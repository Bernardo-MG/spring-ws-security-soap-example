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

import java.util.Locale;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.ws.soap.client.SoapFaultClientException;
import org.springframework.ws.test.client.MockWebServiceServer;
import org.springframework.ws.test.client.RequestMatchers;
import org.springframework.ws.test.client.ResponseCreators;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.wandrell.example.swss.client.EntityClient;
import com.wandrell.example.swss.testing.util.config.ClientWSS4JContextConfig;
import com.wandrell.example.ws.generated.entity.Entity;

/**
 * Unit tests for {@link EntityClient}.
 * <p>
 * Checks the following cases:
 * <ol>
 * <li>The client can handle error messages.</li>
 * <li>The client throws SOAP exceptions for received faults.</li>
 * <li>The client throws SOAP exceptions for version mismatch faults.</li>
 * </ol>
 *
 * @author Bernardo Martínez Garrido
 */
@ContextConfiguration(locations = { ClientWSS4JContextConfig.UNSECURE })
public final class TestEntityClientErrors extends
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
     * Path to the file with the valid request payload.
     */
    @Value("${soap.request.payload.path}")
    private String       requestPayloadPath;

    /**
     * Constructs a {@code TestEntityClient}.
     */
    public TestEntityClientErrors() {
        super();
    }

    /**
     * Tests that the client can handle error messages.
     */
    @Test
    public void testClient_Error() {
        final MockWebServiceServer mockServer; // Mocked server
        final Source requestPayload;  // SOAP payload for the request
        final Entity result;          // Queried entity

        mockServer = MockWebServiceServer.createServer(client);

        requestPayload = new StreamSource(
                ClassLoader.class.getResourceAsStream(requestPayloadPath));

        mockServer.expect(RequestMatchers.payload(requestPayload)).andRespond(
                ResponseCreators.withError("Error"));

        result = client.getEntity("http:somewhere.com", entityId);

        Assert.assertEquals((Integer) result.getId(), (Integer) (-1));
        Assert.assertEquals(result.getName(), null);

        mockServer.verify();
    }

    /**
     * Tests that the client throws SOAP exceptions for received faults.
     */
    @Test(expectedExceptions = { SoapFaultClientException.class })
    public void testClient_Fault() {
        final MockWebServiceServer mockServer; // Mocked server
        final Source requestPayload;  // SOAP payload for the request
        final Entity result;          // Queried entity

        mockServer = MockWebServiceServer.createServer(client);

        requestPayload = new StreamSource(
                ClassLoader.class.getResourceAsStream(requestPayloadPath));

        mockServer.expect(RequestMatchers.payload(requestPayload)).andRespond(
                ResponseCreators.withServerOrReceiverFault("FAULT:Server",
                        Locale.ENGLISH));

        result = client.getEntity("http:somewhere.com", entityId);

        Assert.assertEquals((Integer) result.getId(), (Integer) (-1));
        Assert.assertEquals(result.getName(), null);

        mockServer.verify();
    }

    /**
     * Tests that the client throws SOAP exceptions for version mismatch faults.
     */
    @Test(expectedExceptions = { SoapFaultClientException.class })
    public void testClient_VersionMismatch() {
        final MockWebServiceServer mockServer; // Mocked server
        final Source requestPayload;  // SOAP payload for the request
        final Entity result;          // Queried entity

        mockServer = MockWebServiceServer.createServer(client);

        requestPayload = new StreamSource(
                ClassLoader.class.getResourceAsStream(requestPayloadPath));

        mockServer.expect(RequestMatchers.payload(requestPayload)).andRespond(
                ResponseCreators.withVersionMismatchFault(
                        "FAULT:Version mismatch", Locale.ENGLISH));

        result = client.getEntity("http:somewhere.com", entityId);

        Assert.assertEquals((Integer) result.getId(), (Integer) (-1));
        Assert.assertEquals(result.getName(), null);

        mockServer.verify();
    }

}
