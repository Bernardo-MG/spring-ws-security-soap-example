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

package com.wandrell.example.swss.testing.integration.ws;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.wandrell.example.swss.testing.util.SOAPParsingUtils;
import com.wandrell.example.swss.testing.util.SecurityUtils;
import com.wandrell.example.swss.testing.util.config.ContextConfig;
import com.wandrell.example.ws.generated.entity.Entity;

/**
 * Integration tests for
 * {@link com.wandrell.example.swss.endpoint.EntityEndpoint
 * ExampleEntityEndpoint} testing it with password-based security.
 * <p>
 * Checks the following cases:
 * <ol>
 * <li>A valid message returns the expected value.</li>
 * <li>A message without a password returns a fault.</li>
 * <li>A message with a wrong password returns a fault.</li>
 * <li>A message with a wrong user returns a fault.</li>
 * <li>The WSDL is being generated.</li>
 * </ol>
 * <p>
 * Pay attention to the fact that it requires the WS to be running.
 *
 * @author Bernardo Martínez Garrido
 */
@ContextConfiguration(locations = { ContextConfig.ENDPOINT_PASSWORD })
public final class ITEntityEndpointPassword extends
        AbstractTestNGSpringContextTests {

    /**
     * Id of the returned entity.
     */
    @Value("${entity.id}")
    private Integer entityId;
    /**
     * Name of the returned entity.
     */
    @Value("${entity.name}")
    private String entityName;
    /**
     * Password for the passworded message.
     */
    @Value("${security.credentials.password}")
    private String password;
    /**
     * Path to the file containing the invalid SOAP request.
     */
    @Value("${message.invalid.file.path}")
    private String pathInvalid;
    /**
     * Path to the file containing the valid SOAP request.
     */
    @Value("${message.valid.file.path}")
    private String pathValid;
    /**
     * Username for the passworded message.
     */
    @Value("${security.credentials.user}")
    private String username;
    /**
     * URL to the WSDL for the web service being tested.
     */
    @Value("${ws.wsdl.url}")
    private String wsdlURL;
    /**
     * URL to the web service being tested.
     */
    @Value("${ws.url}")
    private String wsURL;

    /**
     * Default constructor.
     */
    public ITEntityEndpointPassword() {
        super();
    }

    /**
     * Tests that a message with invalid content returns a fault.
     *
     * @throws Exception
     *             never, this is a required declaration
     */
    @Test
    public final void testEndpoint_Invalid_ReturnsFault() throws Exception {
        final SOAPMessage message; // Response message

        message = callWebService(SOAPParsingUtils
                .parseMessageFromFile(pathInvalid));

        Assert.assertNotNull(message.getSOAPPart().getEnvelope().getBody()
                .getFault());
    }

    /**
     * Tests that a message with a wrong password returns a fault.
     *
     * @throws Exception
     *             never, this is a required declaration
     */
    @Test
    public final void testEndpoint_InvalidPassword_ReturnsEntity()
            throws Exception {
        final SOAPMessage message; // Response message

        message = callWebService(SecurityUtils.getPasswordedMessage(pathValid,
                username, "abc123"));

        Assert.assertNotNull(message.getSOAPPart().getEnvelope().getBody()
                .getFault());
    }

    /**
     * Tests that a message with a wrong user returns a fault.
     *
     * @throws Exception
     *             never, this is a required declaration
     */
    @Test
    public final void testEndpoint_InvalidUser_ReturnsEntity() throws Exception {
        final SOAPMessage message; // Response message

        message = callWebService(SecurityUtils.getPasswordedMessage(pathValid,
                "abc123", password));

        Assert.assertNotNull(message.getSOAPPart().getEnvelope().getBody()
                .getFault());
    }

    /**
     * Tests that a valid message returns the expected value.
     *
     * @throws Exception
     *             never, this is a required declaration
     */
    @Test
    public final void testEndpoint_ValidUserAndPassword_ReturnsEntity()
            throws Exception {
        final SOAPMessage message; // Response message
        final Entity entity;       // Entity from the response

        message = callWebService(SecurityUtils.getPasswordedMessage(pathValid,
                username, password));

        Assert.assertNull(message.getSOAPPart().getEnvelope().getBody()
                .getFault());

        entity = SOAPParsingUtils.parseEntityFromMessage(message);

        Assert.assertEquals((Integer) entity.getId(), entityId);
        Assert.assertEquals(entity.getName(), entityName);
    }

    /**
     * Tests that the WSDL is being generated.
     *
     * @throws IOException
     *             never, this is a required declaration
     */
    @Test
    public final void testEndpoint_WSDL() throws IOException {
        final URL url;           // URL for the WSDL
        final BufferedReader in; // Reader for the WSDL
        final String line;       // First line of the WSDL

        url = new URL(wsdlURL);
        in = new BufferedReader(new InputStreamReader(url.openStream()));
        line = in.readLine();

        Assert.assertTrue(line.contains("wsdl:definitions"));
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

        return soapConnectionFactory.createConnection().call(request, wsURL);
    }

}
