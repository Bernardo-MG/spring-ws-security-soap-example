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

package com.wandrell.example.swss.testing.util.test.endpoint;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.xpath.XPathExpressionException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/**
 * Abstract integration tests for an endpoint testing that it handles messages
 * correctly.
 * <p>
 * Checks the following cases:
 * <ol>
 * <li>The WSDL is being generated.</li>
 * <li>The WSDL contains the correct SOAP address.</li>
 * </ol>
 * <p>
 * Pay attention to the fact that it requires the WS to be running.
 *
 * @author Bernardo Martínez Garrido
 */
public abstract class AbstractITEndpoint
        extends AbstractTestNGSpringContextTests {

    /**
     * URL to the WSDL of the web service being tested.
     */
    @Value("${ws.wsdl.url}")
    private String wsdlURL;
    /**
     * URL to the web service being tested.
     */
    @Value("${ws.url}")
    private String wsURL;

    /**
     * Default constructor.
     */
    public AbstractITEndpoint() {
        super();
    }

    /**
     * Tests that the WSDL is being generated.
     *
     * @throws IOException
     *             never, this is a required declaration
     */
    @Test
    public final void testEndpoint_WSDL_Exists() throws IOException {
        final URL url;           // URL for the WSDL
        final BufferedReader in; // Reader for the WSDL
        final String line;       // First line of the WSDL

        url = new URL(wsdlURL);
        in = new BufferedReader(new InputStreamReader(url.openStream()));
        line = in.readLine();

        Assert.assertTrue(line.contains("wsdl:definitions"));
    }

    /**
     * Tests that the WSDL contains the correct SOAP address.
     * 
     * @throws ParserConfigurationException
     *             never, this is a required declaration
     * @throws SAXException
     *             never, this is a required declaration
     * @throws IOException
     *             never, this is a required declaration
     * @throws XPathExpressionException
     *             never, this is a required declaration
     */
    @Test
    public final void testEndpoint_WSDL_ValidSOAPAddress()
            throws ParserConfigurationException, SAXException, IOException,
            XPathExpressionException {
        final DocumentBuilderFactory factory;   // Factory for the
                                                // DocumentBuilder
        final DocumentBuilder builder;          // Document builder
        final Document doc;                     // Document for the WSDL
        final Node serviceNode;                 // WSDL service node
        final Node portNode;                    // WSDL port node
        final Node addressNode;                 // WSDL address node

        // Creates the document
        factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        builder = factory.newDocumentBuilder();
        doc = builder.parse(new URL(wsdlURL).openStream());

        serviceNode = getChild((Element) doc.getFirstChild(), "wsdl:service");
        portNode = getChild((Element) serviceNode, "wsdl:port");
        addressNode = getChild((Element) portNode, "soap:address");

        Assert.assertEquals(((Element) addressNode).getAttribute("location"),
                wsURL);
    }

    /**
     * Acquires a child element based on the name.
     * 
     * @param parent
     *            the element where the search will be made
     * @param name
     *            the name of the child to return
     * @return the child with the specified name
     */
    private final Element getChild(final Element parent, final String name) {
        Element result = null;      // The wanted child

        for (Node child = parent.getFirstChild(); child != null; child = child
                .getNextSibling()) {
            if ((child instanceof Element)
                    && (name.equals(child.getNodeName()))) {
                result = (Element) child;
            }
        }

        return result;
    }

    /**
     * Calls the web service being tested and returns the response.
     *
     * @param request
     *            request to the web service
     * @return the web service response
     * @throws SOAPException
     *             never, this is a required declaration
     */
    protected final SOAPMessage callWebService(final SOAPMessage request)
            throws SOAPException {
        final SOAPConnectionFactory soapConnectionFactory; // Connection factory

        soapConnectionFactory = SOAPConnectionFactory.newInstance();

        return soapConnectionFactory.createConnection().call(request, wsURL);
    }

}
