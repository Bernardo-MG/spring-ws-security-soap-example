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

package com.wandrell.example.swss.testing.util.test.unit.client;

import java.io.IOException;

import javax.xml.namespace.QName;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.ws.test.client.MockWebServiceServer;
import org.springframework.ws.test.client.RequestMatcher;
import org.springframework.ws.test.client.RequestMatchers;
import org.springframework.ws.test.client.ResponseCreator;
import org.springframework.ws.test.client.ResponseCreators;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.wandrell.example.swss.client.EntityClient;
import com.wandrell.example.ws.generated.entity.Entity;

/**
 * Abstract unit tests for an endpoint testing that it handles header-based SOAP
 * messages correctly.
 * <p>
 * Checks the following cases:
 * <ol>
 * <li>The client parses correctly formed SOAP messages.</li>
 * <li>The client can handle incorrectly formed SOAP messages.</li>
 * </ol>
 * <p>
 * This base test is meant for those clients securing the SOAP message.
 *
 * @author Bernardo Martínez Garrido
 */
public abstract class AbstractTestEntityClientHeader
        extends AbstractTestNGSpringContextTests {

    /**
     * The client being tested.
     */
    @Autowired
    private EntityClient client;
    /**
     * Expected id for the returned entity.
     */
    @Value("${entity.id}")
    private Integer      entityId;
    /**
     * Expected name for the returned entity.
     */
    @Value("${entity.name}")
    private String       entityName;
    /**
     * Path to the file with the invalid response payload.
     */
    @Value("${soap.response.payload.invalid.path}")
    private String       responsePayloadInvalidPath;
    /**
     * Path to the file with the valid response payload.
     */
    @Value("${soap.response.payload.path}")
    private String       responsePayloadPath;
    /**
     * Security header token name.
     */
    @Value("${soap.header.security.name}")
    private String       secHeaderLocalPart;
    /**
     * Security header token URI.
     */
    @Value("${soap.header.security.uri}")
    private String       secHeaderUri;

    /**
     * Constructs a {@code AbstractTestEntityClientHeader}.
     */
    public AbstractTestEntityClientHeader() {
        super();
    }

    /**
     * Tests that the client can handle incorrectly formed SOAP messages.
     *
     * @throws IOException
     *             if there is any problem loading the entity schema file
     */
    @Test
    public final void testClient_Header_Invalid() throws IOException {
        final MockWebServiceServer mockServer; // Mocked server
        final RequestMatcher requestMatcher;   // Matcher for the request
        final ResponseCreator responseCreator; // Creator for the response
        final Entity result;                   // Queried entity

        // Creates the request matcher
        requestMatcher = RequestMatchers
                .soapHeader(new QName(secHeaderUri, secHeaderLocalPart));

        // Creates the response
        responseCreator = ResponseCreators
                .withPayload(new ClassPathResource(responsePayloadInvalidPath));

        // Creates the server mock
        mockServer = MockWebServiceServer.createServer(client);
        mockServer.expect(requestMatcher).andRespond(responseCreator);

        // Calls the server mock
        result = client.getEntity("http:somewhere.com", entityId);

        Assert.assertEquals(result.getId(), 0);
        Assert.assertEquals(result.getName(), null);

        mockServer.verify();
    }

    /**
     * Tests that the client parses correctly formed SOAP messages.
     *
     * @throws IOException
     *             if there is any problem loading the entity schema file
     */
    @Test
    public final void testClient_Header_Valid() throws IOException {
        final MockWebServiceServer mockServer; // Mocked server
        final RequestMatcher requestMatcher;   // Matcher for the request
        final ResponseCreator responseCreator; // Creator for the response
        final Entity result;                   // Queried entity

        // Creates the request matcher
        requestMatcher = RequestMatchers
                .soapHeader(new QName(secHeaderUri, secHeaderLocalPart));

        // Creates the response
        responseCreator = ResponseCreators
                .withPayload(new ClassPathResource(responsePayloadPath));

        // Creates the server mock
        mockServer = MockWebServiceServer.createServer(client);
        mockServer.expect(requestMatcher).andRespond(responseCreator);

        // Calls the server mock
        result = client.getEntity("http:somewhere.com", entityId);

        Assert.assertEquals((Integer) result.getId(), entityId);
        Assert.assertEquals(result.getName(), entityName);

        mockServer.verify();
    }

}
