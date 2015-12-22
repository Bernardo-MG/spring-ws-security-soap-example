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

package com.wandrell.example.ws.soap.spring.testing.integration.ws;

import java.io.IOException;

import javax.xml.bind.JAXBException;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.wandrell.example.ws.generated.entity.Entity;
import com.wandrell.example.ws.soap.spring.testing.config.WSPathConfig;
import com.wandrell.example.ws.soap.spring.testing.util.SOAPParsingUtils;

/**
 * Integration tests for
 * {@link com.wandrell.example.ws.soap.spring.endpoint.ExampleEntityEndpoint
 * ExampleEntityEndpoint} testing that it handles unsecured messages correctly.
 * <p>
 * Checks the following cases:
 * <ol>
 * <li>A valid message returns the expected value.</li>
 * <li>A message with invalid content returns a fault.</li>
 * </ol>
 * <p>
 * Pay attention to the fact that it requires the WS to be running.
 *
 * @author Bernardo Martínez Garrido
 */
public final class ITExampleEntityEndpointNotSecured {

    /**
     * Id of the returned entity.
     */
    private final Integer entityId = 1;
    /**
     * Name of the returned entity.
     */
    private final String entityName = "entity_1";
    /**
     * Path to the file containing the invalid SOAP request.
     */
    private final String pathInvalid = "/soap/entity-invalid.xml";
    /**
     * Path to the file containing the valid SOAP request.
     */
    private final String pathValid = "/soap/entity-not-secured.xml";
    /**
     * URL to the web service being tested.
     */
    private final String wsUrl = WSPathConfig.ENDPOINT_ENTITIES;

    /**
     * Default constructor.
     */
    public ITExampleEntityEndpointNotSecured() {
        super();
    }

    /**
     * Tests that a message with invalid content returns a fault.
     *
     * @throws UnsupportedOperationException
     *             never, this is a required declaration
     * @throws SOAPException
     *             never, this is a required declaration
     * @throws IOException
     *             never, this is a required declaration
     * @throws JAXBException
     *             never, this is a required declaration
     */
    @Test
    public final void testEndpoint_Invalid_ReturnsFault()
            throws UnsupportedOperationException, SOAPException, IOException,
            JAXBException {
        final SOAPMessage message; // Response message

        message = callWebService(SOAPParsingUtils
                .parseMessageFromFile(pathInvalid));

        Assert.assertNotNull(message.getSOAPPart().getEnvelope().getBody()
                .getFault());
    }

    /**
     * Tests that a valid message returns the expected value.
     *
     * @throws UnsupportedOperationException
     *             never, this is a required declaration
     * @throws SOAPException
     *             never, this is a required declaration
     * @throws IOException
     *             never, this is a required declaration
     * @throws JAXBException
     *             never, this is a required declaration
     */
    @Test
    public final void testEndpoint_Valid_ReturnsEntity()
            throws UnsupportedOperationException, SOAPException, IOException,
            JAXBException {
        final SOAPMessage message; // Response message
        final Entity entity;       // Entity from the response

        message = callWebService(SOAPParsingUtils
                .parseMessageFromFile(pathValid));

        Assert.assertNull(message.getSOAPPart().getEnvelope().getBody()
                .getFault());

        entity = SOAPParsingUtils.parseEntityFromMessage(message);

        Assert.assertEquals((Integer) entity.getId(), entityId);
        Assert.assertEquals(entity.getName(), entityName);
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
    private final SOAPMessage callWebService(final SOAPMessage request)
            throws SOAPException {
        final SOAPConnectionFactory soapConnectionFactory; // Connection factory

        soapConnectionFactory = SOAPConnectionFactory.newInstance();

        return soapConnectionFactory.createConnection().call(request, wsUrl);
    }

}
