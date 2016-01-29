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

package com.wandrell.example.swss.testing.unit.client.password.plain.xwss;

import java.io.IOException;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.ws.test.client.MockWebServiceServer;
import org.springframework.ws.test.client.RequestMatchers;
import org.springframework.ws.test.client.ResponseCreators;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.wandrell.example.swss.client.EntityClient;
import com.wandrell.example.swss.testing.util.config.ClientXWSSContextConfig;
import com.wandrell.example.ws.generated.entity.Entity;

/**
 * Unit tests for {@link EntityClient}.
 * <p>
 * Checks the following cases:
 * <ol>
 * <li>The client parses correctly formed SOAP messages.</li>
 * <li>The client can handle incorrectly formed SOAP messages.</li>
 * </ol>
 *
 * @author Bernardo Martínez Garrido
 */
@ContextConfiguration(locations = { ClientXWSSContextConfig.PASSWORD_PLAIN })
public final class TestEntityClientPasswordPlainXWSS extends
        AbstractTestNGSpringContextTests {

    /**
     * The client being tested.
     */
    @Autowired
    private EntityClient client;
    /**
     * Id of the returned entity.
     */
    @Value("${entity.id}")
    private Integer      entityId;
    /**
     * Name of the returned entity.
     */
    @Value("${entity.name}")
    private String       entityName;
    /**
     * Path to XSD file which validates the SOAP messages.
     */
    @Value("${xsd.entity.path}")
    private String       entityXsdPath;
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
     * Constructs a {@code TestEntityClient}.
     */
    public TestEntityClientPasswordPlainXWSS() {
        super();
    }

    /**
     * Tests that the client can handle incorrectly formed SOAP messages.
     * 
     * @throws IOException
     */
    @Test
    public void testClient_Invalid() throws IOException {
        final MockWebServiceServer mockServer; // Mocked server
        final Source responsePayload; // SOAP payload for the response
        final Entity result;          // Queried entity

        mockServer = MockWebServiceServer.createServer(client);

        responsePayload = new StreamSource(
                ClassLoader.class
                        .getResourceAsStream(responsePayloadInvalidPath));
        mockServer.expect(
                RequestMatchers.validPayload(new ClassPathResource(
                        entityXsdPath))).andRespond(
                ResponseCreators.withPayload(responsePayload));

        result = client.getEntity("http:somewhere.com", entityId);

        Assert.assertEquals(result.getId(), 0);
        Assert.assertEquals(result.getName(), null);

        mockServer.verify();
    }

    /**
     * Tests that the client parses correctly formed SOAP messages.
     * 
     * @throws IOException
     */
    @Test
    public void testClient_Valid() throws IOException {
        final MockWebServiceServer mockServer; // Mocked server
        final Source responsePayload; // SOAP payload for the response
        final Entity result;          // Queried entity

        mockServer = MockWebServiceServer.createServer(client);

        responsePayload = new StreamSource(
                ClassLoader.class.getResourceAsStream(responsePayloadPath));

        mockServer.expect(
                RequestMatchers.validPayload(new ClassPathResource(
                        entityXsdPath))).andRespond(
                ResponseCreators.withPayload(responsePayload));

        result = client.getEntity("http:somewhere.com", entityId);

        Assert.assertEquals((Integer) result.getId(), entityId);
        Assert.assertEquals(result.getName(), entityName);

        mockServer.verify();
    }

}
