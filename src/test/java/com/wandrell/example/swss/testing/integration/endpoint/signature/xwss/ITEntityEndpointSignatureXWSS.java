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

package com.wandrell.example.swss.testing.integration.endpoint.signature.xwss;

import java.security.KeyStore;

import javax.xml.soap.SOAPMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.wandrell.example.swss.testing.util.SOAPParsingUtils;
import com.wandrell.example.swss.testing.util.config.context.TestContextPaths;
import com.wandrell.example.swss.testing.util.config.properties.EndpointURLXWSSPropertiesPaths;
import com.wandrell.example.swss.testing.util.config.properties.SOAPPropertiesPaths;
import com.wandrell.example.swss.testing.util.config.properties.TestPropertiesPaths;
import com.wandrell.example.swss.testing.util.test.integration.endpoint.AbstractITEndpoint;
import com.wandrell.example.ws.generated.entity.Entity;

/**
 * Implementation of {@code AbstractITEndpoint} for a signature protected
 * endpoint using XWSS.
 * <p>
 * It adds the following cases:
 * <ol>
 * <li>A message without a signature returns a fault.</li>
 * <li>A message with a valid signature returns the expected value.</li>
 * </ol>
 * <p>
 * Pay attention to the fact that it requires the WS to be running.
 *
 * @author Bernardo Martínez Garrido
 */
@ContextConfiguration(
        locations = { TestContextPaths.DEFAULT, TestContextPaths.KEYSTORE })
@TestPropertySource({ TestPropertiesPaths.ENTITY,
        TestPropertiesPaths.KEYSTORE, SOAPPropertiesPaths.SIGNATURE,
        EndpointURLXWSSPropertiesPaths.SIGNATURE })
public final class ITEntityEndpointSignatureXWSS extends AbstractITEndpoint {

    /**
     * Alias for the certificate for signing messages.
     */
    @Value("${keystore.alias}")
    private String   alias;
    /**
     * Id of the returned entity.
     */
    @Value("${entity.id}")
    private Integer  entityId;
    /**
     * Name of the returned entity.
     */
    @Value("${entity.name}")
    private String   entityName;
    /**
     * Key store for signing messages.
     */
    @Autowired
    @Qualifier("keyStore")
    private KeyStore keystore;
    /**
     * Password for the certificate for signing messages.
     */
    @Value("${keystore.password}")
    private String   password;
    /**
     * Path to the file containing the invalid SOAP request.
     */
    @Value("${soap.request.invalid.path}")
    private String   pathUnsigned;

    /**
     * Default constructor.
     */
    public ITEntityEndpointSignatureXWSS() {
        super();
    }

    /**
     * Tests that a message without a signature returns a fault.
     *
     * @throws Exception
     *             never, this is a required declaration
     */
    @Test
    public final void testEndpoint_Unsigned_ReturnsFault() throws Exception {
        final SOAPMessage message; // Response message

        message = callWebService(
                SOAPParsingUtils.parseMessageFromFile(pathUnsigned));

        Assert.assertNotNull(
                message.getSOAPPart().getEnvelope().getBody().getFault());
    }

    /**
     * Tests that a message with a valid signature returns the expected value.
     *
     * @throws Exception
     *             never, this is a required declaration
     */
    @Test
    public final void testEndpoint_ValidSignature_ReturnsEntity()
            throws Exception {
        final SOAPMessage message; // Response message
        final Entity entity;       // Entity from the response

        // message =
        // callWebService(securityUtils.getSignedMessage(alias,password,alias,pathInvalid,keystore));

        // TODO: Get this working

        // Assert.assertNull(message.getSOAPPart().getEnvelope().getBody()
        // .getFault());

        // entity = SOAPParsingUtils.parseEntityFromMessage(message);

        // Assert.assertEquals((Integer) entity.getId(), entityId);
        // Assert.assertEquals(entity.getName(), entityName);
    }

}
