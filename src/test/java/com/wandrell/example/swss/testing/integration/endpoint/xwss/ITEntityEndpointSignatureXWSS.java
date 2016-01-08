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

package com.wandrell.example.swss.testing.integration.endpoint.xwss;

import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
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

import org.apache.tools.ant.util.Base64Converter;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.signature.XMLSignature;
import org.apache.xml.security.transforms.Transforms;
import org.apache.xml.security.utils.Constants;
import org.apache.xml.security.utils.XMLUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import com.wandrell.example.swss.testing.util.SOAPParsingUtils;
import com.wandrell.example.swss.testing.util.config.ContextConfig;
import com.wandrell.example.swss.testing.util.test.endpoint.AbstractITEndpoint;
import com.wandrell.example.ws.generated.entity.Entity;

/**
 * Implementation of {@code AbstractITEndpoint} for a signature protected
 * endpoint using XWSS.
 * <p>
 * It adds the following cases:
 * <ol>
 * <li>A message without a signature returns a fault.</li>
 * <li>A message with a valid signature returns the expected value.</li>
 * </ol>
 * <p>
 * Pay attention to the fact that it requires the WS to be running.
 *
 * @author Bernardo Martínez Garrido
 */
@ContextConfiguration(locations = { ContextConfig.ENDPOINT_SIGNATURE_XWSS })
public final class ITEntityEndpointSignatureXWSS extends AbstractITEndpoint {

    /**
     * Alias for the certificate for signing messages.
     */
    @Value("${keystore.alias}")
    private String   alias;
    /**
     * Id of the returned entity.
     */
    @Value("${entity.id}")
    private Integer  entityId;
    /**
     * Name of the returned entity.
     */
    @Value("${entity.name}")
    private String   entityName;
    /**
     * Key store for signing messages.
     */
    @Autowired
    @Qualifier("keyStore")
    private KeyStore keystore;
    /**
     * Password for the certificate for signing messages.
     */
    @Value("${keystore.password}")
    private String   password;
    /**
     * Path to the file containing the invalid SOAP request.
     */
    @Value("${message.invalid.file.path}")
    private String   pathInvalid;

    /**
     * Default constructor.
     */
    public ITEntityEndpointSignatureXWSS() {
        super();
    }

    /**
     * Tests that a message without a signature returns a fault.
     *
     * @throws Exception
     *             never, this is a required declaration
     */
    @Test
    public final void testEndpoint_Unsigned_ReturnsFault() throws Exception {
        final SOAPMessage message; // Response message

        message = callWebService(SOAPParsingUtils
                .parseMessageFromFile(pathInvalid));

        Assert.assertNotNull(message.getSOAPPart().getEnvelope().getBody()
                .getFault());
    }

    /**
     * Tests that a message with a valid signature returns the expected value.
     *
     * @throws Exception
     *             never, this is a required declaration
     */
    @Test
    public final void testEndpoint_ValidSignature_ReturnsEntity()
            throws Exception {
        final SOAPMessage message; // Response message
        final Entity entity;       // Entity from the response

        // message = callWebService(getSignedMessage());

        // TODO: Make this work

        // Assert.assertNull(message.getSOAPPart().getEnvelope().getBody()
        // .getFault());

        // entity = SOAPParsingUtils.parseEntityFromMessage(message);

        // Assert.assertEquals((Integer) entity.getId(), entityId);
        // Assert.assertEquals(entity.getName(), entityName);
    }

    private final SOAPMessage getMessageToSign() throws SOAPException,
            IOException {
        final SOAPMessage soapMessage;
        final SOAPPart soapPart;
        final SOAPEnvelope soapEnvelope;
        final SOAPHeader soapHeader;
        final SOAPHeaderElement secElement;
        final SOAPElement binaryTokenElement;

        soapMessage = SOAPParsingUtils.parseMessageFromFile(pathInvalid);
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

    private final XMLSignature getSignature(final Document doc,
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

    private final SOAPMessage getSignedMessage()
            throws UnrecoverableKeyException, KeyStoreException,
            NoSuchAlgorithmException, SAXException, IOException,
            ParserConfigurationException, XMLSecurityException, SOAPException,
            TransformerConfigurationException, TransformerException,
            CertificateEncodingException {
        String privateKeyAlias = alias;
        String privateKeyPass = password;
        String certificateAlias = alias;
        Element root = null;
        String BaseURI = ClassLoader.class.getResource(pathInvalid).toString();
        SOAPMessage soapMessage;
        Base64Converter base64 = new Base64Converter();
        String token;
        Node binaryToken;
        X509Certificate cert;
        PrivateKey privateKey;
        XMLSignature sig;

        soapMessage = getMessageToSign();

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

    private final Document toDocument(SOAPMessage soapMsg)
            throws TransformerConfigurationException, TransformerException,
            SOAPException, IOException {
        Source src = soapMsg.getSOAPPart().getContent();
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        DOMResult result = new DOMResult();
        transformer.transform(src, result);
        return (Document) result.getNode();
    }

    private final SOAPMessage toMessage(Document jdomDocument)
            throws IOException, SOAPException {
        SOAPMessage message = MessageFactory.newInstance().createMessage();
        SOAPPart sp = message.getSOAPPart();
        Element imported = (Element) sp.importNode(
                jdomDocument.getFirstChild(), true);
        SOAPBody sb = message.getSOAPBody();
        sb.appendChild(imported);

        return message;
    }

}
