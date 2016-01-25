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

import static org.springframework.ws.test.client.RequestMatchers.payload;
import static org.springframework.ws.test.client.ResponseCreators.withPayload;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.ws.test.client.MockWebServiceServer;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.wandrell.example.swss.client.EntityClient;
import com.wandrell.example.swss.testing.util.config.ClientContextConfig;
import com.wandrell.example.ws.generated.entity.Entity;

/**
 * Unit tests for {@link EntityClient}.
 * <p>
 * Checks the following cases:
 * <ol>
 * <li>The client parses correctly formed SOAP messages.</li>
 * </ol>
 *
 * @author Bernardo Martínez Garrido
 */
@ContextConfiguration(locations = { ClientContextConfig.CLIENT_UNSECURE })
public final class TestEntityClientUnsecure extends AbstractTestNGSpringContextTests {

    /**
     * The client being tested.
     */
    @Autowired
    private EntityClient client;

    /**
     * Constructs a {@code TestEntityClient}.
     */
    public TestEntityClientUnsecure() {
        super();
    }

    /**
     * Tests that the client parses correctly formed SOAP messages.
     */
    @Test
    public void customerClient() {
        final MockWebServiceServer mockServer; // Mocked server
        final Source requestPayload;  // SOAP payload for the request
        final Source responsePayload; // SOAP payload for the response
        final Entity result;          // Queried entity

        mockServer = MockWebServiceServer.createServer(client);

        requestPayload = new StreamSource(
                ClassLoader.class
                        .getResourceAsStream("/soap/entity-request-not-secured.xml"));
        responsePayload = new StreamSource(
                ClassLoader.class
                        .getResourceAsStream("/soap/entity-response-not-secured.xml"));

        mockServer.expect(payload(requestPayload)).andRespond(
                withPayload(responsePayload));

        result = client.getEntity("http:somewhere.com", 1);

        Assert.assertEquals(result.getId(), 1);
        Assert.assertEquals(result.getName(), "entity_1");

        mockServer.verify();
    }

}
