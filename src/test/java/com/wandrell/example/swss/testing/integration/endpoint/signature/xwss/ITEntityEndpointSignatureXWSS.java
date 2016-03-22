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

import com.wandrell.example.swss.testing.util.SOAPParsingUtils;
import com.wandrell.example.swss.testing.util.config.context.TestContextPaths;
import com.wandrell.example.swss.testing.util.config.properties.EndpointURLXWSSPropertiesPaths;
import com.wandrell.example.swss.testing.util.config.properties.SOAPPropertiesPaths;
import com.wandrell.example.swss.testing.util.config.properties.TestPropertiesPaths;
import com.wandrell.example.swss.testing.util.test.integration.endpoint.AbstractITEndpoint;

/**
 * Implementation of {@code AbstractITEndpoint} for a signature protected
 * endpoint using XWSS.
 * <p>
 * Pay attention to the fact that it requires the WS to be running.
 *
 * @author Bernardo Martínez Garrido
 */
@ContextConfiguration(
        locations = { TestContextPaths.DEFAULT, TestContextPaths.KEYSTORE })
@TestPropertySource({ TestPropertiesPaths.ENTITY, TestPropertiesPaths.KEYSTORE,
        SOAPPropertiesPaths.SIGNATURE,
        EndpointURLXWSSPropertiesPaths.SIGNATURE })
public final class ITEntityEndpointSignatureXWSS extends AbstractITEndpoint {

    /**
     * Alias for the certificate for signing messages.
     */
    @Value("${keystore.alias}")
    private String   alias;
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
    private String   pathInvalid;

    /**
     * Default constructor.
     */
    public ITEntityEndpointSignatureXWSS() {
        super();
    }

    @Override
    protected final SOAPMessage getInvalidSoapMessage() throws Exception {
        return SOAPParsingUtils.parseMessageFromFile(pathInvalid);
    }

    @Override
    protected final SOAPMessage getValidSoapMessage() throws Exception {
        // return SecurityUtils.getSignedMessage(pathUnsigned,
        // alias, password, alias, keystore);
        return null;
    }

}
