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
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.SecureRandom;
import java.util.Collections;

import javax.xml.crypto.MarshalException;
import javax.xml.crypto.dsig.Reference;
import javax.xml.crypto.dsig.SignedInfo;
import javax.xml.crypto.dsig.XMLSignature;
import javax.xml.crypto.dsig.XMLSignatureException;
import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.dom.DOMSignContext;
import javax.xml.crypto.dsig.dom.DOMValidateContext;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;
import javax.xml.crypto.dsig.keyinfo.KeyValue;
import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.sun.xml.ws.security.opt.crypto.dsig.CanonicalizationMethod;
import com.sun.xml.ws.security.opt.crypto.dsig.DigestMethod;
import com.sun.xml.ws.security.opt.crypto.dsig.SignatureMethod;
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
     * Path to the file containing the invalid SOAP request.
     */
    @Value("${message.invalid.file.path}")
    private String   pathInvalid;
    /**
     * Key store for signing messages.
     */
    @Autowired
    @Qualifier("keyStore")
    private KeyStore keystore;
    /**
     * Alias for the certificate for signing messages.
     */
    @Value("keystore.alias")
    private String   alias;
    /**
     * Password for the certificate for signing messages.
     */
    @Value("keystore.password")
    private String   password;
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

        // Check
        // http://stackoverflow.com/questions/25161813/soap-handlers-using-java-keystore

        message = callWebService(getSignedMessage());

        Assert.assertNull(message.getSOAPPart().getEnvelope().getBody()
                .getFault());

        entity = SOAPParsingUtils.parseEntityFromMessage(message);

        Assert.assertEquals((Integer) entity.getId(), entityId);
        Assert.assertEquals(entity.getName(), entityName);
    }

    private final SOAPMessage getSignedMessage() throws MarshalException,
            XMLSignatureException, SOAPException, SAXException, IOException,
            ParserConfigurationException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InstantiationException,
            IllegalAccessException, ClassNotFoundException, KeyException {
        SOAPMessage soapMessage = SOAPParsingUtils
                .parseMessageFromFile(pathInvalid);
        SOAPPart soapPart = soapMessage.getSOAPPart();
        SOAPEnvelope soapEnvelope = soapPart.getEnvelope();

        SOAPHeader soapHeader = soapEnvelope.getHeader();
        soapHeader
                .addHeaderElement(soapEnvelope.createName("Signature",
                        "SOAP-SEC",
                        "http://schemas.xmlsoap.org/soap/security/2000-12"));

        SOAPBody soapBody = soapEnvelope.getBody();
        soapBody.addAttribute(soapEnvelope.createName("id", "SOAP-SEC",
                "http://schemas.xmlsoap.org/soap/security/2000-12"), "Body");

        // Generate a DOM representation of the SOAP message

        System.out.println("Generating the DOM tree...");
        // Get input source
        Source source = soapPart.getContent();
        org.w3c.dom.Node root = null;

        if (source instanceof DOMSource) {
            root = ((DOMSource) source).getNode();

        } else if (source instanceof SAXSource) {
            InputSource inSource = ((SAXSource) source).getInputSource();
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setNamespaceAware(true);
            DocumentBuilder db = null;

            synchronized (dbf) {
                db = dbf.newDocumentBuilder();
            }
            Document doc = db.parse(inSource);
            root = (org.w3c.dom.Node) doc.getDocumentElement();

        } else {
            System.err.println("error: cannot convert SOAP message ("
                    + source.getClass().getName() + ") into a W3C DOM tree");
            System.exit(-1);
        }

        // Generate a DSA key pair

        System.out.println("Generating the DSA keypair...");
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("DSA");
        kpg.initialize(1024, new SecureRandom("not so random".getBytes()));
        KeyPair keypair = kpg.generateKeyPair();

        // Assemble the signature parts

        System.out.println("Preparing the signature...");
        String providerName = System.getProperty("jsr105Provider",
                "org.jcp.xml.dsig.internal.dom.XMLDSigRI");
        XMLSignatureFactory sigFactory = XMLSignatureFactory.getInstance("DOM",
                (Provider) Class.forName(providerName).newInstance());
        Reference ref = sigFactory.newReference("#Body",
                sigFactory.newDigestMethod(DigestMethod.SHA1, null));
        SignedInfo signedInfo = sigFactory.newSignedInfo(sigFactory
                .newCanonicalizationMethod(
                        CanonicalizationMethod.INCLUSIVE_WITH_COMMENTS,
                        (C14NMethodParameterSpec) null), sigFactory
                .newSignatureMethod(SignatureMethod.DSA_SHA1, null),
                Collections.singletonList(ref));
        KeyInfoFactory kif = sigFactory.getKeyInfoFactory();
        KeyValue kv = kif.newKeyValue(keypair.getPublic());
        KeyInfo keyInfo = kif.newKeyInfo(Collections.singletonList(kv));

        XMLSignature sig = sigFactory.newXMLSignature(signedInfo, keyInfo);

        // Insert XML signature into DOM tree and sign

        System.out.println("Signing the SOAP message...");
        // Find where to insert signature
        Element envelope = getFirstChildElement(root);
        Element header = getFirstChildElement(envelope);
        DOMSignContext sigContext = new DOMSignContext(keypair.getPrivate(),
                header);
        // Need to distinguish the Signature element in DSIG (from that in SOAP)
        sigContext.putNamespacePrefix(XMLSignature.XMLNS, "ds");
        // register Body ID attribute
        sigContext.setIdAttributeNS(getNextSiblingElement(header),
                "http://schemas.xmlsoap.org/soap/security/2000-12", "id");
        sig.sign(sigContext);

        // Validate the XML signature

        // Locate the signature element
        Element sigElement = getFirstChildElement(header);
        // Validate the signature using the public key generated above
        DOMValidateContext valContext = new DOMValidateContext(
                keypair.getPublic(), sigElement);
        // register Body ID attribute
        valContext.setIdAttributeNS(getNextSiblingElement(header),
                "http://schemas.xmlsoap.org/soap/security/2000-12", "id");
        boolean isValid = sig.validate(valContext);
        System.out.println("Validating the signature... "
                + (isValid ? "valid" : "invalid"));

        return soapMessage;
    }

    /**
     * Returns the first child element of the specified node, or null if there
     * is no such element.
     *
     * @param node
     *            the node
     * @return the first child element of the specified node, or null if there
     *         is no such element
     * @throws NullPointerException
     *             if <code>node == null</code>
     */
    private static Element getFirstChildElement(org.w3c.dom.Node node) {
        org.w3c.dom.Node child = node.getFirstChild();
        while (child != null
                && child.getNodeType() != org.w3c.dom.Node.ELEMENT_NODE) {
            child = child.getNextSibling();
        }
        return (Element) child;
    }

    /**
     * Returns the next sibling element of the specified node, or null if there
     * is no such element.
     *
     * @param node
     *            the node
     * @return the next sibling element of the specified node, or null if there
     *         is no such element
     * @throws NullPointerException
     *             if <code>node == null</code>
     */
    public static Element getNextSiblingElement(org.w3c.dom.Node node) {
        org.w3c.dom.Node sibling = node.getNextSibling();
        while (sibling != null
                && sibling.getNodeType() != org.w3c.dom.Node.ELEMENT_NODE) {
            sibling = sibling.getNextSibling();
        }
        return (Element) sibling;
    }
}
