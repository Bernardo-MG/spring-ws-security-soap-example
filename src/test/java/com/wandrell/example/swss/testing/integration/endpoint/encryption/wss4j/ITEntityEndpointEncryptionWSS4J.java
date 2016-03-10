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

package com.wandrell.example.swss.testing.integration.endpoint.encryption.wss4j;

import javax.xml.soap.SOAPMessage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.wandrell.example.swss.testing.util.SOAPParsingUtils;
import com.wandrell.example.swss.testing.util.config.context.TestContextPaths;
import com.wandrell.example.swss.testing.util.config.properties.EndpointURLWSS4JPropertiesPaths;
import com.wandrell.example.swss.testing.util.config.properties.SOAPPropertiesPaths;
import com.wandrell.example.swss.testing.util.config.properties.TestPropertiesPaths;
import com.wandrell.example.swss.testing.util.test.integration.endpoint.AbstractITEndpoint;

/**
 * Implementation of {@code AbstractITEndpoint} for an encryption protected
 * endpoint using WSS4J.
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
@ContextConfiguration(locations = { TestContextPaths.DEFAULT })
@TestPropertySource({ TestPropertiesPaths.ENTITY,
        SOAPPropertiesPaths.ENCRYPTION,
        EndpointURLWSS4JPropertiesPaths.ENCRYPTION })
public final class ITEntityEndpointEncryptionWSS4J extends AbstractITEndpoint {

    /**
     * Path to the file containing the invalid SOAP request.
     */
    @Value("${soap.request.invalid.path}")
    private String pathUnsigned;

    /**
     * Default constructor.
     */
    public ITEntityEndpointEncryptionWSS4J() {
        super();
    }

    /**
     * Tests that a message correctly encrypted returns the expected value.
     *
     * @throws Exception
     *             never, this is a required declaration
     */
    @Test
    public final void testEndpoint_Encrypted_ReturnsFault() throws Exception {
        // TODO: Get this working
    }

    /**
     * Tests that an unencrypted message returns a fault.
     *
     * @throws Exception
     *             never, this is a required declaration
     */
    @Test
    public final void testEndpoint_Unencrypted_ReturnsFault() throws Exception {
        final SOAPMessage message; // Response message

        message = callWebService(
                SOAPParsingUtils.parseMessageFromFile(pathUnsigned));

        Assert.assertNotNull(
                message.getSOAPPart().getEnvelope().getBody().getFault());
    }

}
