/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2015 the original author or authors.
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.wandrell.example.swss.test.util.factory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPHeaderElement;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.tools.ant.util.Base64Converter;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.signature.XMLSignature;
import org.apache.xml.security.transforms.Transforms;
import org.apache.xml.security.utils.Constants;
import org.apache.xml.security.utils.XMLUtils;
import org.springframework.core.io.ClassPathResource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.wandrell.example.swss.test.util.SoapMessageUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * Factory method for secured SOAP messages.
 * <p>
 * These can be contained in a {@code SOAPMessage}, or be the input of an
 * {@code InputStream}. In the second case the {@code InputStream} will point to
 * a string containing the full message.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 */
public final class SecureSoapMessages {

	/**
	 * Generates the digest value for the SOAP secure header.
	 * <p>
	 * This is a codified password, with the help of the date and nonce values.
	 * Both of these values should be found on the SOAP secure header.
	 *
	 * @param password
	 *            password to digest
	 * @param date
	 *            date used on the SOAP header
	 * @param nonce
	 *            nonce used on the SOAP header
	 * @return the digested password
	 * @throws UnsupportedEncodingException
	 *             if the UTF-8 encoding is not supported
	 */
	private static final String generateDigest(final String password, final String date, final String nonce)
			throws UnsupportedEncodingException {
		final ByteBuffer buf; // Buffers storing the data to digest
		byte[] toHash; // Bytes to generate the hash

		// Fills buffer with data to digest
		buf = ByteBuffer.allocate(1000);
		buf.put(Base64.decodeBase64(nonce));
		buf.put(date.getBytes("UTF-8"));
		buf.put(password.getBytes("UTF-8"));

		// Initializes hash bytes to the correct size
		toHash = new byte[buf.position()];
		buf.rewind();

		// Copies bytes from the buffer to the hash bytes
		buf.get(toHash);

		return Base64.encodeBase64String(DigestUtils.sha1(toHash));
	}

	/**
	 * Generates the current date in the format expected by the SOAP message.
	 *
	 * @return the current date
	 */
	private static final String getCurrentDate() {
		final DateFormat format; // Format to apply

		// Zulu time format
		format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		format.setTimeZone(TimeZone.getTimeZone("UTC"));

		return format.format(new Date());
	}

	/**
	 * Creates a SOAP message with a digested password, username and nonce.
	 * <p>
	 * The nonce will be generated during the securing process.
	 * <p>
	 * A freemarker template should be provided, it will be used to generate the
	 * final message from the received parameters.
	 *
	 * @param path
	 *            path to the freemarker template
	 * @param user
	 *            user to include in the message
	 * @param password
	 *            password to include in the message
	 * @return a digested password {@code SOAPMessage}
	 * @return a SOAP message with a digested password, username and nonce
	 * @throws Exception
	 *             if any error occurs during the message creation
	 */
	public static final SOAPMessage getDigestedPasswordMessage(final String path, final String user,
			final String password) throws Exception {
		return MessageFactory.newInstance().createMessage(new MimeHeaders(),
				getDigestedPasswordStream(path, user, password));
	}

	/**
	 * Generates the text content for the digested password SOAP message.
	 * <p>
	 * This will be created from a freemarker template.
	 *
	 * @param path
	 *            path to the freemarker template
	 * @param user
	 *            username to use
	 * @param password
	 *            password to use
	 * @return the text content for the digested password message
	 * @throws Exception
	 *             if any error occurs during the message creation
	 */
	private static final String getDigestedPasswordMessageContent(final String path, final String user,
			final String password) throws Exception {
		final String nonce; // Nonce for the message
		final String date; // Current date
		final String digest; // Digested password
		final Template template; // Freemarker template
		final Map<String, Object> data; // Data for the template
		final ByteArrayOutputStream out; // Steam with the message

		// Generates security data
		nonce = getNonce();
		date = getCurrentDate();
		digest = generateDigest(password, date, nonce);

		// Prepares the data for the template
		data = new HashMap<String, Object>();
		data.put("user", user);
		data.put("password", password);
		data.put("nonce", nonce);
		data.put("date", date);
		data.put("digest", digest);

		// Processes the template to the output
		out = new ByteArrayOutputStream();
		template = new Configuration(Configuration.VERSION_2_3_0).getTemplate(path);
		template.process(data, new OutputStreamWriter(out));

		return new String(out.toByteArray());
	}

	/**
	 * Creates a SOAP message with a digested password, username and nonce.
	 * <p>
	 * The nonce will be generated during the securing process.
	 * <p>
	 * A freemarker template should be provided, it will be used to generate the
	 * final message from the received parameters.
	 * 
	 * @param path
	 *            path to the freemarker template
	 * @param user
	 *            user to include in the message
	 * @param password
	 *            password to include in the message
	 * @return a SOAP message with a digested password, username and nonce
	 * @throws Exception
	 *             if any error occurs during the message creation
	 */
	public static final InputStream getDigestedPasswordStream(final String path, final String user,
			final String password) throws Exception {
		return new ByteArrayInputStream(getDigestedPasswordMessageContent(path, user, password).getBytes("UTF-8"));
	}

	private static final SOAPMessage getMessageToSign(final String pathBase) throws SOAPException, IOException {
		final SOAPMessage soapMessage;
		final SOAPPart soapPart;
		final SOAPEnvelope soapEnvelope;
		final SOAPHeader soapHeader;
		final SOAPHeaderElement secElement;
		final SOAPElement binaryTokenElement;

		soapMessage = SoapMessageUtils.getMessage(pathBase);
		soapPart = soapMessage.getSOAPPart();
		soapEnvelope = soapPart.getEnvelope();
		soapHeader = soapEnvelope.getHeader();

		secElement = soapHeader.addHeaderElement(soapEnvelope.createName("Security", "wsse",
				"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd"));
		binaryTokenElement = secElement.addChildElement(soapEnvelope.createName("BinarySecurityToken", "wsse",
				"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd"));
		binaryTokenElement.setAttribute("EncodingType",
				"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#Base64Binary");
		binaryTokenElement.setAttribute("ValueType",
				"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509v3");

		return soapMessage;
	}

	/**
	 * Generates a nonce value for the SOAP secure header.
	 *
	 * @return the nonce value
	 * @throws Exception
	 *             if any error occurs while generating the nonce
	 */
	private static final String getNonce() throws Exception {
		final SecureRandom random; // Random value generator
		final byte[] nonceBytes; // Bytes to generate the nonce

		random = SecureRandom.getInstance("SHA1PRNG");
		random.setSeed(System.currentTimeMillis());
		nonceBytes = new byte[16];
		random.nextBytes(nonceBytes);

		return new String(Base64.encodeBase64(nonceBytes), "UTF-8");
	}

	/**
	 * Creates a SOAP message with a plain password and username.
	 * <p>
	 * A freemarker template should be provided, it will be used to generate the
	 * final message from the received parameters.
	 * 
	 * @param path
	 *            path to the freemarker template
	 * @param user
	 *            user to include in the message
	 * @param password
	 *            password to include in the message
	 * @return a SOAP message with a plain password and username
	 * @throws Exception
	 *             if any error occurs during the message creation
	 */
	public static final SOAPMessage getPlainPasswordMessage(final String path, final String user, final String password)
			throws Exception {
		return MessageFactory.newInstance().createMessage(new MimeHeaders(),
				getPlainPasswordStream(path, user, password));
	}

	/**
	 * Generates the text content for the plain password SOAP message.
	 * <p>
	 * This will be created from a freemarker template.
	 *
	 * @param path
	 *            path to the freemarker template
	 * @param user
	 *            username to use
	 * @param password
	 *            password to use
	 * @return the text content for the plain passworde message
	 * @throws Exception
	 *             if any error occurs during the message creation
	 */
	private static final String getPlainPasswordMessageContent(final String path, final String user,
			final String password) throws Exception {
		final Template template; // Freemarker template
		final Map<String, Object> data; // Data for the template
		final ByteArrayOutputStream out; // Steam with the message

		// Prepares the data for the template
		data = new HashMap<String, Object>();
		data.put("user", user);
		data.put("password", password);

		// Processes the template to the output
		out = new ByteArrayOutputStream();
		template = new Configuration(Configuration.VERSION_2_3_0).getTemplate(path);
		template.process(data, new OutputStreamWriter(out));

		return new String(out.toByteArray());
	}

	/**
	 * Creates a SOAP message with a plain password and username.
	 * <p>
	 * A freemarker template should be provided, it will be used to generate the
	 * final message from the received parameters.
	 * 
	 * @param path
	 *            path to the freemarker template
	 * @param user
	 *            user to include in the message
	 * @param password
	 *            password to include in the message
	 * @return a SOAP message with a plain password and username
	 * @throws Exception
	 *             if any error occurs during the message creation
	 */
	public static final InputStream getPlainPasswordStream(final String path, final String user, final String password)
			throws Exception {
		return new ByteArrayInputStream(getPlainPasswordMessageContent(path, user, password).getBytes("UTF-8"));
	}

	private static final XMLSignature getSignature(final Document doc, final String BaseURI, final X509Certificate cert,
			final PrivateKey privateKey) throws XMLSecurityException {
		final XMLSignature sig;

		sig = new XMLSignature(doc, BaseURI, XMLSignature.ALGO_ID_SIGNATURE_RSA_SHA1);

		final Transforms transforms = new Transforms(doc);
		transforms.addTransform(Transforms.TRANSFORM_C14N_OMIT_COMMENTS);
		// Sign the content of SOAP Envelope
		sig.addDocument("", transforms, Constants.ALGO_ID_DIGEST_SHA1);

		sig.addKeyInfo(cert);
		sig.addKeyInfo(cert.getPublicKey());
		sig.sign(privateKey);

		return sig;
	}

	/**
	 * Creates a SOAP message with a signature.
	 * <p>
	 * A valid SOAP message is required, this will be the message to be signed.
	 * 
	 * @param pathBase
	 *            path to the SOAP message to sign
	 * @param privateKeyAlias
	 *            alias for the private key
	 * @param privateKeyPass
	 *            password for the private key
	 * @param certificateAlias
	 *            alias for the certificate
	 * @param keystore
	 *            key store for the signing
	 * @return a singed SOAP message
	 * @throws Exception
	 *             if any error occurs during the message creation
	 */
	public static final SOAPMessage getSignedMessage(final String pathBase, final String privateKeyAlias,
			final String privateKeyPass, final String certificateAlias, final KeyStore keystore) throws Exception {
		Element root = null;
		final String BaseURI = new ClassPathResource(pathBase).getURI().toString();
		SOAPMessage soapMessage;
		final Base64Converter base64 = new Base64Converter();
		String token;
		Node binaryToken;
		X509Certificate cert;
		PrivateKey privateKey;
		XMLSignature sig;

		soapMessage = getMessageToSign(pathBase);

		// get the private key used to sign, from the keystore
		privateKey = (PrivateKey) keystore.getKey(privateKeyAlias, privateKeyPass.toCharArray());
		cert = (X509Certificate) keystore.getCertificate(certificateAlias);

		// create basic structure of signature
		final Document doc = toDocument(soapMessage);

		org.apache.xml.security.Init.init();

		sig = getSignature(doc, BaseURI, cert, privateKey);

		// optional, but better
		root = doc.getDocumentElement();
		root.normalize();
		root.getElementsByTagName("wsse:Security").item(0).appendChild(sig.getElement());

		token = base64.encode(cert.getEncoded());

		binaryToken = root.getElementsByTagName("wsse:BinarySecurityToken").item(0);
		binaryToken.setTextContent(token);

		// write signature to file
		XMLUtils.outputDOMc14nWithComments(doc, System.out);

		return toMessage(doc);
	}

	/**
	 * Creates a SOAP message with a signature.
	 * <p>
	 * A valid SOAP message is required, this will be the message to be signed.
	 * 
	 * @param pathBase
	 *            path to the SOAP message to sign
	 * @param privateKeyAlias
	 *            alias for the private key
	 * @param privateKeyPass
	 *            password for the private key
	 * @param certificateAlias
	 *            alias for the certificate
	 * @param keystore
	 *            key store for the signing
	 * @return a singed SOAP message
	 * @throws Exception
	 *             if any error occurs during the message creation
	 */
	public static final InputStream getSignedStream(final String pathBase, final String privateKeyAlias,
			final String privateKeyPass, final String certificateAlias, final KeyStore keystore) throws Exception {
		final SOAPMessage msg = SecureSoapMessages.getSignedMessage(pathBase, privateKeyAlias, privateKeyPass,
				certificateAlias, keystore);
		final ByteArrayOutputStream out = new ByteArrayOutputStream();
		msg.writeTo(out);
		final String strMsg = new String(out.toByteArray());
		return new ByteArrayInputStream(strMsg.getBytes());
	}

	private static final Document toDocument(final SOAPMessage soapMsg)
			throws TransformerConfigurationException, TransformerException, SOAPException, IOException {
		final Source src = soapMsg.getSOAPPart().getContent();
		final TransformerFactory tf = TransformerFactory.newInstance();
		final Transformer transformer = tf.newTransformer();
		final DOMResult result = new DOMResult();
		transformer.transform(src, result);
		return (Document) result.getNode();
	}

	private static final SOAPMessage toMessage(final Document jdomDocument) throws IOException, SOAPException {
		final SOAPMessage message = MessageFactory.newInstance().createMessage();
		final SOAPPart sp = message.getSOAPPart();
		sp.setContent(new DOMSource(jdomDocument.getFirstChild()));

		return message;
	}

	/**
	 * Private constructor to avoid initialization.
	 */
	private SecureSoapMessages() {
		super();
	}

}
