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

package com.wandrell.example.swss.testing.unit.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.ws.test.server.MockWebServiceClient;
import org.springframework.ws.test.server.RequestCreator;
import org.springframework.ws.test.server.RequestCreators;
import org.springframework.ws.test.server.ResponseMatcher;
import org.springframework.ws.test.server.ResponseMatchers;
import org.testng.annotations.Test;

import com.wandrell.example.swss.testing.util.config.context.ServletWSS4JContextPaths;
import com.wandrell.example.swss.testing.util.config.properties.EndpointXWSSPropertiesPaths;
import com.wandrell.example.swss.testing.util.config.properties.SOAPPropertiesPaths;
import com.wandrell.example.swss.testing.util.config.properties.TestPropertiesPaths;

/**
 * Unit tests for an unsecured endpoint testing that it handles payload-based
 * SOAP messages correctly.
 * <p>
 * Checks the following cases:
 * <ol>
 * <li>The endpoint parses SOAP requests with a valid payload.</li>
 * <li>The endpoint can handle SOAP requests with an invalid payload.</li>
 * </ol>
 *
 * @author Bernardo Martínez Garrido
 */
@ContextConfiguration(locations = { ServletWSS4JContextPaths.BASE,
        ServletWSS4JContextPaths.UNSECURE })
@TestPropertySource({ TestPropertiesPaths.WSDL, SOAPPropertiesPaths.UNSECURE,
        EndpointXWSSPropertiesPaths.UNSECURE,
        EndpointXWSSPropertiesPaths.BASE })
public final class TestEntityEndpointUnsecure
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
    @Value("${soap.request.payload.invalid.path}")
    private String             requestPayloadInvalidPath;
    /**
     * Path to the file with the valid request payload.
     */
    @Value("${soap.request.payload.path}")
    private String             requestPayloadPath;

    /**
     * Constructs a {@code TestEntityEndpointUnsecure}.
     */
    public TestEntityEndpointUnsecure() {
        super();
    }

    /**
     * Tests that the endpoint can handle SOAP requests with a valid payload.
     */
    @Test
    public final void testEndpoint_Payload_Invalid() throws Exception {
        final MockWebServiceClient mockClient; // Mocked client
        final RequestCreator requestCreator;   // Creator for the request
        final ResponseMatcher responseMatcher; // Matcher for the response

        // Creates the request
        requestCreator = RequestCreators
                .withPayload(new ClassPathResource(requestPayloadInvalidPath));

        // Creates the response matcher
        responseMatcher = ResponseMatchers.clientOrSenderFault();

        // Creates the client mock
        mockClient = MockWebServiceClient.createClient(applicationContext);

        // Calls the endpoint
        mockClient.sendRequest(requestCreator).andExpect(responseMatcher);
    }

    /**
     * Tests that the endpoint parses SOAP requests with a valid payload.
     */
    @Test
    public final void testEndpoint_Payload_Valid() throws Exception {
        final MockWebServiceClient mockClient; // Mocked client
        final RequestCreator requestCreator;   // Creator for the request
        final ResponseMatcher responseMatcher; // Matcher for the response

        // Creates the request
        requestCreator = RequestCreators
                .withPayload(new ClassPathResource(requestPayloadPath));

        // Creates the response matcher
        responseMatcher = ResponseMatchers
                .validPayload(new ClassPathResource(entityXsdPath));

        // Creates the client mock
        mockClient = MockWebServiceClient.createClient(applicationContext);

        // Calls the endpoint
        mockClient.sendRequest(requestCreator).andExpect(responseMatcher);
    }

}
