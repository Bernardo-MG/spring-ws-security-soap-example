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

package com.wandrell.example.swss.testing.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TimeZone;

import javax.xml.parsers.ParserConfigurationException;
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
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * Utilities class for security procedures.
 *
 * @author Bernardo Martínez Garrido
 */
public final class SecurityUtils {

    /**
     * Creates a SOAP message with a digested password, username and nonce.
     * <p>
     * A freemarker template will be used for this. It will receive the
     * generated values, and give a basic structure to the message.
     *
     * @param path
     *            path to the freemarker template
     * @param password
     *            password to use
     * @return the text content for the passworded message
     * @return a SOAP message with a digested password, username and nonce
     * @throws Exception
     *             when any error occurs during the message creation
     */
    public static final SOAPMessage getDigestedPasswordMessage(
            final String path, final String user, final String password)
            throws Exception {
        final MessageFactory factory;
        final SOAPMessage message;
        final ByteArrayOutputStream out;

        factory = MessageFactory.newInstance();

        message = factory.createMessage(new MimeHeaders(),
                getDigestedPasswordStream(path, user, password));

        out = new ByteArrayOutputStream();
        message.writeTo(out);

        return message;
    }

    public static final InputStream getDigestedPasswordStream(
            final String path, final String user, final String password)
            throws Exception {

        return new ByteArrayInputStream(getDigestedPasswordMessageContent(path,
                user, password).getBytes("UTF-8"));
    }

    public static final SOAPMessage getPlainPasswordMessage(final String path,
            final String user, final String password) throws Exception {
        final MessageFactory factory;

        factory = MessageFactory.newInstance();

        return factory.createMessage(new MimeHeaders(),
                getPlainPasswordStream(path, user, password));
    }

    public static final InputStream getPlainPasswordStream(final String path,
            final String user, final String password) throws Exception {
        return new ByteArrayInputStream(getPlainPasswordMessageContent(path,
                user, password).getBytes("UTF-8"));
    }

    public static final SOAPMessage getSignedMessage(final String pathBase,
            final String privateKeyAlias, final String privateKeyPass,
            final String certificateAlias, final KeyStore keystore)
            throws UnrecoverableKeyException, KeyStoreException,
            NoSuchAlgorithmException, SAXException, IOException,
            ParserConfigurationException, XMLSecurityException, SOAPException,
            TransformerConfigurationException, TransformerException,
            CertificateEncodingException {
        Element root = null;
        String BaseURI = ClassLoader.class.getResource(pathBase).toString();
        SOAPMessage soapMessage;
        Base64Converter base64 = new Base64Converter();
        String token;
        Node binaryToken;
        X509Certificate cert;
        PrivateKey privateKey;
        XMLSignature sig;

        soapMessage = getMessageToSign(pathBase);

        // get the private key used to sign, from the keystore
        privateKey = (PrivateKey) keystore.getKey(privateKeyAlias,
                privateKeyPass.toCharArray());
        cert = (X509Certificate) keystore.getCertificate(certificateAlias);

        // create basic structure of signature
        Document doc = toDocument(soapMessage);

        org.apache.xml.security.Init.init();

        sig = getSignature(doc, BaseURI, cert, privateKey);

        // optional, but better
        root = doc.getDocumentElement();
        root.normalize();
        root.getElementsByTagName("wsse:Security").item(0)
                .appendChild(sig.getElement());

        token = base64.encode(cert.getEncoded());

        binaryToken = root.getElementsByTagName("wsse:BinarySecurityToken")
                .item(0);
        binaryToken.setTextContent(token);

        // write signature to file
        XMLUtils.outputDOMc14nWithComments(doc, System.out);

        return toMessage(doc);
    }

    public static final InputStream getSignedMessageStream(
            final String pathBase, final String privateKeyAlias,
            final String privateKeyPass, final String certificateAlias,
            final KeyStore keystore) throws UnsupportedEncodingException,
            Exception {
        SOAPMessage msg = SecurityUtils.getSignedMessage(pathBase,
                privateKeyAlias, privateKeyPass, certificateAlias, keystore);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        msg.writeTo(out);
        String strMsg = new String(out.toByteArray());
        return new ByteArrayInputStream(strMsg.getBytes());
    }

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
    private static final String generateDigest(final String password,
            final String date, final String nonce)
            throws UnsupportedEncodingException {
        final ByteBuffer buf;
        byte[] toHash;
        byte[] hash;

        buf = ByteBuffer.allocate(1000);
        buf.put(Base64.decodeBase64(nonce));
        buf.put(date.getBytes("UTF-8"));
        buf.put(password.getBytes("UTF-8"));
        toHash = new byte[buf.position()];
        buf.rewind();
        buf.get(toHash);
        hash = DigestUtils.sha1(toHash);

        return Base64.encodeBase64String(hash);
    }

    /**
     * Generates the date value for the SOAP secure header.
     *
     * @return the date value for the header
     */
    private static final String getDate() {
        final DateFormat zuluTime;
        final String date;

        zuluTime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        zuluTime.setTimeZone(TimeZone.getTimeZone("UTC"));

        date = zuluTime.format(new Date());

        return date;
    }

    /**
     * Generates the text content for the passworded message.
     * <p>
     * This will be created from a freemarker template.
     *
     * @param path
     *            path to the freemarker template
     * @param user
     *            username to use
     * @param password
     *            password to use
     * @return the text content for the passworded message
     * @throws Exception
     *             if any error occurs during the message creation
     */
    private static final String getDigestedPasswordMessageContent(
            final String path, final String user, final String password)
            throws Exception {
        final String nonce;
        final String date;
        final String digest;
        final Configuration cfg;
        final Template template;
        final Map<String, Object> data;
        final ByteArrayOutputStream out;

        cfg = new Configuration(Configuration.VERSION_2_3_0);
        template = cfg.getTemplate(path);

        nonce = getNonce();
        date = getDate();
        digest = generateDigest(password, date, nonce);

        data = new LinkedHashMap<String, Object>();

        data.put("user", user);
        data.put("password", password);
        data.put("nonce", nonce);
        data.put("date", date);
        data.put("digest", digest);

        out = new ByteArrayOutputStream();
        template.process(data, new OutputStreamWriter(out));

        return new String(out.toByteArray());
    }

    private static final SOAPMessage getMessageToSign(final String pathBase)
            throws SOAPException, IOException {
        final SOAPMessage soapMessage;
        final SOAPPart soapPart;
        final SOAPEnvelope soapEnvelope;
        final SOAPHeader soapHeader;
        final SOAPHeaderElement secElement;
        final SOAPElement binaryTokenElement;

        soapMessage = SOAPParsingUtils.parseMessageFromFile(pathBase);
        soapPart = soapMessage.getSOAPPart();
        soapEnvelope = soapPart.getEnvelope();
        soapHeader = soapEnvelope.getHeader();

        secElement = soapHeader
                .addHeaderElement(soapEnvelope
                        .createName(
                                "Security",
                                "wsse",
                                "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd"));
        binaryTokenElement = secElement
                .addChildElement(soapEnvelope
                        .createName(
                                "BinarySecurityToken",
                                "wsse",
                                "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd"));
        binaryTokenElement
                .setAttribute(
                        "EncodingType",
                        "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#Base64Binary");
        binaryTokenElement
                .setAttribute(
                        "ValueType",
                        "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509v3");

        return soapMessage;
    }

    /**
     * Generates the nonce value for the SOAP secure header.
     *
     * @return the nonce value for the header
     * @throws Exception
     *             if any error occurs while generating the nonce
     */
    private static final String getNonce() throws Exception {
        final SecureRandom random;
        final byte[] nonceBytes;

        random = SecureRandom.getInstance("SHA1PRNG");
        random.setSeed(System.currentTimeMillis());
        nonceBytes = new byte[16];
        random.nextBytes(nonceBytes);

        return new String(Base64.encodeBase64(nonceBytes), "UTF-8");
    }

    private static final String getPlainPasswordMessageContent(
            final String path, final String user, final String password)
            throws Exception {
        final Configuration cfg;
        final Template template;
        final Map<String, Object> data;
        final ByteArrayOutputStream out;

        cfg = new Configuration(Configuration.VERSION_2_3_0);
        template = cfg.getTemplate(path);

        data = new LinkedHashMap<String, Object>();

        data.put("user", user);
        data.put("password", password);

        out = new ByteArrayOutputStream();
        template.process(data, new OutputStreamWriter(out));

        return new String(out.toByteArray());
    }

    private static final XMLSignature getSignature(final Document doc,
            final String BaseURI, final X509Certificate cert,
            final PrivateKey privateKey) throws XMLSecurityException {
        final XMLSignature sig;

        sig = new XMLSignature(doc, BaseURI,
                XMLSignature.ALGO_ID_SIGNATURE_RSA_SHA1);

        Transforms transforms = new Transforms(doc);
        transforms.addTransform(Transforms.TRANSFORM_C14N_OMIT_COMMENTS);
        // Sign the content of SOAP Envelope
        sig.addDocument("", transforms, Constants.ALGO_ID_DIGEST_SHA1);

        sig.addKeyInfo(cert);
        sig.addKeyInfo(cert.getPublicKey());
        sig.sign(privateKey);

        return sig;
    }

    private static final Document toDocument(SOAPMessage soapMsg)
            throws TransformerConfigurationException, TransformerException,
            SOAPException, IOException {
        Source src = soapMsg.getSOAPPart().getContent();
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        DOMResult result = new DOMResult();
        transformer.transform(src, result);
        return (Document) result.getNode();
    }

    private static final SOAPMessage toMessage(Document jdomDocument)
            throws IOException, SOAPException {
        SOAPMessage message = MessageFactory.newInstance().createMessage();
        SOAPPart sp = message.getSOAPPart();
        sp.setContent(new DOMSource(jdomDocument.getFirstChild()));

        return message;
    }

    /**
     * Private constructor to avoid initialization.
     */
    private SecurityUtils() {
        super();
    }

}
