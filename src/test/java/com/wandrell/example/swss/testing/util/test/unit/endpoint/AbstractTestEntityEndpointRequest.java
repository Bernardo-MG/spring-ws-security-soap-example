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

package com.wandrell.example.swss.testing.util.test.unit.endpoint;

import javax.xml.transform.Source;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.ws.test.server.MockWebServiceClient;
import org.springframework.ws.test.server.RequestCreator;
import org.springframework.ws.test.server.RequestCreators;
import org.springframework.ws.test.server.ResponseMatcher;
import org.springframework.ws.test.server.ResponseMatchers;
import org.testng.annotations.Test;

/**
 * Abstract unit tests for an endpoint testing that it handles envelope-based
 * SOAP messages correctly.
 * <p>
 * Checks the following cases:
 * <ol>
 * <li>The endpoint parses SOAP requests with a valid envelope.</li>
 * <li>The endpoint can handle SOAP requests with an invalid envelope.</li>
 * </ol>
 * <p>
 * This base test is meant for those endpoints where the full envelope is more
 * important than the payload, which is the case of the secured endpoints.
 *
 * @author Bernardo Martínez Garrido
 */
public abstract class AbstractTestEntityEndpointRequest
        extends AbstractTestNGSpringContextTests {

    /**
     * Application context to be used for creating the client mock.
     */
    @Autowired
    private ApplicationContext applicationContext;
    /**
     * Path to XSD file which validates the SOAP messages.
     */
    @Value("${xsd.entity.path}")
    private String             entityXsdPath;
    /**
     * Path to the file with the invalid request payload.
     */
    @Value("${soap.request.invalid.path}")
    private String             requestEnvelopeInvalidPath;

    /**
     * Constructs an {@code AbstractTestEntityEndpointRequest}.
     */
    public AbstractTestEntityEndpointRequest() {
        super();
    }

    /**
     * Tests that the endpoint parses SOAP requests with a valid envelope.
     */
    @Test
    public final void testEndpoint_Envelope_Valid() throws Exception {
        final MockWebServiceClient mockClient; // Mocked client
        final RequestCreator requestCreator;   // Creator for the request
        final ResponseMatcher responseMatcher; // Matcher for the response
        final Source requestEnvelope;          // SOAP envelope for the request

        // Creates the request
        requestEnvelope = getRequestEnvelope();
        requestCreator = RequestCreators.withSoapEnvelope(requestEnvelope);

        // Creates the response matcher
        responseMatcher = ResponseMatchers
                .validPayload(new ClassPathResource(entityXsdPath));

        // Creates the client mock
        mockClient = MockWebServiceClient.createClient(applicationContext);

        // Calls the endpoint
        mockClient.sendRequest(requestCreator).andExpect(responseMatcher);
    }

    /**
     * Tests that the endpoint can handle invalid SOAP messages.
     */
    @Test
    public final void testEndpoint_Invalid() throws Exception {
        final MockWebServiceClient mockClient; // Mocked client
        final RequestCreator requestCreator;   // Creator for the request
        final ResponseMatcher responseMatcher; // Matcher for the response

        // Creates the request
        requestCreator = RequestCreators.withSoapEnvelope(
                new ClassPathResource(requestEnvelopeInvalidPath));

        // Creates the response matcher
        responseMatcher = ResponseMatchers.clientOrSenderFault();

        // Creates the client mock
        mockClient = MockWebServiceClient.createClient(applicationContext);

        // Calls the endpoint
        mockClient.sendRequest(requestCreator).andExpect(responseMatcher);
    }

    /**
     * Returns a valid SOAP request envelope in a {@code Source} class.
     *
     * @return a valid SOAP request envelope
     */
    protected abstract Source getRequestEnvelope();

}
