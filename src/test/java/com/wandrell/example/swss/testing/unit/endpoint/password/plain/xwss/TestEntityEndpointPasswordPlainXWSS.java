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

import com.wandrell.example.swss.testing.util.config.SOAPPropertiesConfig;
import com.wandrell.example.swss.testing.util.config.TestPropertiesConfig;
import com.wandrell.example.swss.testing.util.config.context.WebServiceContextConfig;

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
@ContextConfiguration(locations = { WebServiceContextConfig.BASE,
        WebServiceContextConfig.PASSWORD_PLAIN_XWSS })
@TestPropertySource({ TestPropertiesConfig.WSDL, SOAPPropertiesConfig.UNSECURE,
        SOAPPropertiesConfig.PASSWORD_PLAIN,
        "classpath:context/interceptor/password/plain/xwss/interceptor-password-plain-xwss.properties",
        "classpath:context/endpoint/password/plain/xwss/endpoint-password-plain-xwss.properties",
        "classpath:context/endpoint/endpoint.properties" })
public final class TestEntityEndpointPasswordPlainXWSS
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
     * Path to the file with the valid request envelope.
     */
    @Value("${soap.request.path}")
    private String             requestEnvelopePath;
    /**
     * Path to the file with the invalid request payload.
     */
    @Value("${soap.request.payload.invalid.path}")
    private String             requestPayloadInvalidPath;

    /**
     * Constructs a {@code TestEntityEndpointUnsecure}.
     */
    public TestEntityEndpointPasswordPlainXWSS() {
        super();
    }

    /**
     * Tests that the endpoint can handle incorrectly formed SOAP messages.
     */
    @Test
    public final void testEndpoint_Invalid() throws Exception {
        final MockWebServiceClient mockClient; // Mocked client
        final RequestCreator requestCreator;   // Creator for the request
        final ResponseMatcher responseMatcher; // Matcher for the response
        final Source requestPayload;           // SOAP payload for the request

        // Creates the request
        requestPayload = new StreamSource(ClassLoader.class
                .getResourceAsStream(requestPayloadInvalidPath));
        requestCreator = RequestCreators.withPayload(requestPayload);

        // Creates the response matcher
        responseMatcher = ResponseMatchers.clientOrSenderFault();

        // Creates the client mock
        mockClient = MockWebServiceClient.createClient(applicationContext);

        // Calls the endpoint
        mockClient.sendRequest(requestCreator).andExpect(responseMatcher);
    }

    /**
     * Tests that the endpoint parses correctly formed SOAP messages.
     */
    @Test
    public final void testEndpoint_Valid() throws Exception {
        final MockWebServiceClient mockClient; // Mocked client
        final RequestCreator requestCreator;   // Creator for the request
        final ResponseMatcher responseMatcher; // Matcher for the response
        final Source requestEnvelope;          // SOAP envelope for the request

        // Creates the client mock
        mockClient = MockWebServiceClient.createClient(applicationContext);

        // Creates the request
        requestEnvelope = new StreamSource(
                ClassLoader.class.getResourceAsStream(requestEnvelopePath));
        requestCreator = RequestCreators.withSoapEnvelope(requestEnvelope);

        // Creates the response matcher
        responseMatcher = ResponseMatchers
                .validPayload(new ClassPathResource(entityXsdPath));

        // Calls the endpoint
        mockClient.sendRequest(requestCreator).andExpect(responseMatcher);
    }

}
