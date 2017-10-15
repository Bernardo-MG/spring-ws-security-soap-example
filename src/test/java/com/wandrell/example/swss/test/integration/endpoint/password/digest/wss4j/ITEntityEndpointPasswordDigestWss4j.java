/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2015-2017 the original author or authors.
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

package com.wandrell.example.swss.test.integration.endpoint.password.digest.wss4j;

import javax.xml.soap.SOAPMessage;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.TestPropertySource;

import com.wandrell.example.swss.test.util.SoapMessageUtils;
import com.wandrell.example.swss.test.util.config.properties.SoapPropertiesPaths;
import com.wandrell.example.swss.test.util.config.properties.TestEndpointWss4jPropertiesPaths;
import com.wandrell.example.swss.test.util.config.properties.TestPropertiesPaths;
import com.wandrell.example.swss.test.util.factory.SecureSoapMessages;
import com.wandrell.example.swss.test.util.test.integration.endpoint.AbstractITEndpoint;

/**
 * Integration test for a password protected endpoint using WSS4J.
 * <p>
 * It adds the following cases:
 * <ol>
 * <li>A message with a wrong password returns a fault.</li>
 * <li>A message with a wrong user returns a fault.</li>
 * </ol>
 * <p>
 * Pay attention to the fact that it requires the WS to be running.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 */
@TestPropertySource({ TestPropertiesPaths.USER,
        SoapPropertiesPaths.PASSWORD_DIGEST,
        TestEndpointWss4jPropertiesPaths.PASSWORD_DIGEST })
public final class ITEntityEndpointPasswordDigestWss4j
        extends AbstractITEndpoint {

    /**
     * Password for the passworded message.
     */
    @Value("${security.credentials.password}")
    private String password;

    /**
     * Path to the file containing the invalid SOAP request.
     */
    @Value("${soap.request.invalid.path}")
    private String pathInvalid;

    /**
     * Path to the file containing the valid SOAP request.
     */
    @Value("${soap.request.template.path}")
    private String pathValid;

    /**
     * Username for the passworded message.
     */
    @Value("${security.credentials.user}")
    private String username;

    /**
     * Default constructor.
     */
    public ITEntityEndpointPasswordDigestWss4j() {
        super();
    }

    /**
     * Tests that a message with a wrong password returns a fault.
     *
     * @throws Exception
     *             never, this is a required declaration
     */
    @Test
    public final void testEndpoint_InvalidPassword_ReturnsFault()
            throws Exception {
        final SOAPMessage message; // Response message

        message = callWebService(SecureSoapMessages.getDigestedPasswordMessage(
                pathValid, username, password + "abc123"));

        Assert.assertNotNull(
                message.getSOAPPart().getEnvelope().getBody().getFault());
    }

    /**
     * Tests that a message with a wrong user returns a fault.
     *
     * @throws Exception
     *             never, this is a required declaration
     */
    @Test
    public final void testEndpoint_InvalidUser_ReturnsFault() throws Exception {
        final SOAPMessage message; // Response message

        message = callWebService(SecureSoapMessages.getDigestedPasswordMessage(
                pathValid, username + "abc123", password));

        Assert.assertNotNull(
                message.getSOAPPart().getEnvelope().getBody().getFault());
    }

    @Override
    protected final SOAPMessage getInvalidSoapMessage() throws Exception {
        return SoapMessageUtils.getMessage(pathInvalid);
    }

    @Override
    protected final SOAPMessage getValidSoapMessage() throws Exception {
        return SecureSoapMessages.getDigestedPasswordMessage(pathValid,
                username, password);
    }

}
