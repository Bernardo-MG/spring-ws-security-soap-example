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

package com.wandrell.example.swss.testing.unit.client;

import java.io.IOException;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.ws.client.WebServiceTransportException;
import org.springframework.ws.soap.client.SoapFaultClientException;
import org.springframework.ws.test.client.MockWebServiceServer;
import org.springframework.ws.test.client.RequestMatcher;
import org.springframework.ws.test.client.RequestMatchers;
import org.springframework.ws.test.client.ResponseCreator;
import org.springframework.ws.test.client.ResponseCreators;
import org.testng.annotations.Test;

import com.wandrell.example.swss.client.EntityClient;
import com.wandrell.example.swss.testing.util.config.context.ClientWSS4JContextPaths;
import com.wandrell.example.swss.testing.util.config.properties.TestPropertiesPaths;

/**
 * Unit tests for {@link EntityClient} checking that the client throws
 * exceptions for SOAP errors.
 * <p>
 * Checks the following cases:
 * <ol>
 * <li>Tests that the client throws an exception when receiving an error
 * message.</li>
 * <li>Tests that the client throws an exception when receiving an error
 * message.</li>
 * <li>Tests that the client throws an exception when receiving a SOAP version
 * mismatch fault.</li>
 * </ol>
 *
 * @author Bernardo Martínez Garrido
 */
@ContextConfiguration(locations = { ClientWSS4JContextPaths.UNSECURE })
@TestPropertySource({ TestPropertiesPaths.ENTITY, TestPropertiesPaths.WSDL })
public final class TestEntityClientExceptionSOAP
        extends AbstractTestNGSpringContextTests {

    /**
     * The client being tested.
     */
    @Autowired
    private EntityClient client;
    /**
     * Valid entity id.
     */
    @Value("${entity.id}")
    private Integer      entityId;
    /**
     * Path to XSD file which validates the SOAP messages.
     */
    @Value("${xsd.entity.path}")
    private String       entityXsdPath;

    /**
     * Constructs a {@code TestEntityClientExceptionSOAP}.
     */
    public TestEntityClientExceptionSOAP() {
        super();
    }

    /**
     * Tests that the client throws an exception when receiving an error
     * message.
     *
     * @throws IOException
     *             if there is any problem loading the entity schema file
     */
    @Test(expectedExceptions = WebServiceTransportException.class)
    public final void testClient_Error_Exception() throws IOException {
        final MockWebServiceServer mockServer; // Mocked server
        final RequestMatcher requestMatcher;   // Matcher for the request
        final ResponseCreator responseCreator; // Creator for the response

        // Creates the request matcher
        requestMatcher = RequestMatchers
                .validPayload(new ClassPathResource(entityXsdPath));

        // Creates the response
        responseCreator = ResponseCreators.withError("Error");

        // Creates the server mock
        mockServer = MockWebServiceServer.createServer(client);
        mockServer.expect(requestMatcher).andRespond(responseCreator);

        // Calls the server mock
        client.getEntity("http:somewhere.com", entityId);
    }

    /**
     * Tests that the client throws an exception when receiving a SOAP fault.
     *
     * @throws IOException
     *             if there is any problem loading the entity schema file
     */
    @Test(expectedExceptions = { SoapFaultClientException.class })
    public final void testClient_Fault_Exception() throws IOException {
        final MockWebServiceServer mockServer; // Mocked server
        final RequestMatcher requestMatcher;   // Matcher for the request
        final ResponseCreator responseCreator; // Creator for the response

        // Creates the request matcher
        requestMatcher = RequestMatchers
                .validPayload(new ClassPathResource(entityXsdPath));

        // Creates the response
        responseCreator = ResponseCreators
                .withServerOrReceiverFault("FAULT:Server", Locale.ENGLISH);

        // Creates the server mock
        mockServer = MockWebServiceServer.createServer(client);
        mockServer.expect(requestMatcher).andRespond(responseCreator);

        // Calls the server mock
        client.getEntity("http:somewhere.com", entityId);
    }

    /**
     * Tests that the client throws an exception when receiving a SOAP version
     * mismatch fault.
     *
     * @throws IOException
     *             if there is any problem loading the entity schema file
     */
    @Test(expectedExceptions = { SoapFaultClientException.class })
    public final void testClient_VersionMismatch_Exception()
            throws IOException {
        final MockWebServiceServer mockServer; // Mocked server
        final RequestMatcher requestMatcher;   // Matcher for the request
        final ResponseCreator responseCreator; // Creator for the response

        // Creates the request matcher
        requestMatcher = RequestMatchers
                .validPayload(new ClassPathResource(entityXsdPath));

        // Creates the response
        responseCreator = ResponseCreators.withVersionMismatchFault(
                "FAULT:Version mismatch", Locale.ENGLISH);

        // Creates the server mock
        mockServer = MockWebServiceServer.createServer(client);
        mockServer.expect(requestMatcher).andRespond(responseCreator);

        // Calls the server mock
        client.getEntity("http:somewhere.com", entityId);
    }

}
