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

package com.wandrell.example.swss.testing.unit.endpoint.password.plain.xwss;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.ws.test.server.MockWebServiceClient;
import org.springframework.ws.test.server.RequestCreators;
import org.springframework.ws.test.server.ResponseMatchers;
import org.testng.annotations.Test;

import com.wandrell.example.swss.testing.util.config.WSContextConfig;

/**
 * Unit tests for the unsecured endpoint.
 * <p>
 * Checks the following cases:
 * <ol>
 * <li>The endpoint parses correctly formed SOAP messages.</li>
 * <li>The endpoint can handle incorrectly formed SOAP messages.</li>
 * </ol>
 *
 * @author Bernardo Martínez Garrido
 */
@ContextConfiguration(locations = { WSContextConfig.PASSWORD_PLAIN_XWSS })
public final class TestEntityEndpointUnsecure extends
        AbstractTestNGSpringContextTests {

    /**
     * Application context to be used for creating the client mock.
     */
    @Autowired
    private ApplicationContext applicationContext;
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
     * Path to XSD file which validates the SOAP messages.
     */
    @Value("${xsd.entity.path}")
    private String             entityXsdPath;

    /**
     * Constructs a {@code TestEntityEndpointUnsecure}.
     */
    public TestEntityEndpointUnsecure() {
        super();
    }

    /**
     * Tests that the endpoint can handle incorrectly formed SOAP messages.
     */
    @Test
    public final void testEndpoint_Invalid() throws Exception {
        final MockWebServiceClient mockClient; // Mocked client
        final Source requestPayload;  // SOAP payload for the request

        mockClient = MockWebServiceClient.createClient(applicationContext);

        requestPayload = new StreamSource(
                ClassLoader.class
                        .getResourceAsStream(requestPayloadInvalidPath));

        mockClient.sendRequest(RequestCreators.withPayload(requestPayload))
                .andExpect(ResponseMatchers.clientOrSenderFault());
    }

    /**
     * Tests that the endpoint parses correctly formed SOAP messages.
     */
    @Test
    public final void testEndpoint_Valid() throws Exception {
        final MockWebServiceClient mockClient; // Mocked client
        final Source requestPayload;  // SOAP payload for the request

        mockClient = MockWebServiceClient.createClient(applicationContext);

        requestPayload = new StreamSource(
                ClassLoader.class.getResourceAsStream(requestPayloadPath));

        // TODO: Fix this
        // mockClient.sendRequest(RequestCreators.withPayload(requestPayload))
        // .andExpect(
        // ResponseMatchers.validPayload(new ClassPathResource(
        // entityXsdPath)));
    }

}
