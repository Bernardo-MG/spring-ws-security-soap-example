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

package com.wandrell.example.swss.testing.unit.endpoint.signature.wss4j;

import java.security.KeyStore;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

import com.wandrell.example.swss.testing.util.config.context.ServletWss4jContextPaths;
import com.wandrell.example.swss.testing.util.config.context.TestContextPaths;
import com.wandrell.example.swss.testing.util.config.properties.EndpointWss4jPropertiesPaths;
import com.wandrell.example.swss.testing.util.config.properties.InterceptorWss4jPropertiesPaths;
import com.wandrell.example.swss.testing.util.config.properties.SoapPropertiesPaths;
import com.wandrell.example.swss.testing.util.config.properties.TestEndpointWss4jPropertiesPaths;
import com.wandrell.example.swss.testing.util.config.properties.TestPropertiesPaths;
import com.wandrell.example.swss.testing.util.factory.SecureSoapMessages;

/**
 * Implementation of {@code AbstractTestEntityEndpointRequest} for a WSS4J
 * signed endpoint.
 *
 * @author Bernardo Martínez Garrido
 */
@ContextConfiguration(locations = { ServletWss4jContextPaths.APPLICATION_COMMON,
        ServletWss4jContextPaths.SIGNATURE, TestContextPaths.KEYSTORE,
        TestContextPaths.KEYSTORE_WSS4J })
@TestPropertySource({ TestPropertiesPaths.WSDL, SoapPropertiesPaths.UNSECURE,
        SoapPropertiesPaths.SIGNATURE,
        InterceptorWss4jPropertiesPaths.SIGNATURE,
        EndpointWss4jPropertiesPaths.SIGNATURE,
        EndpointWss4jPropertiesPaths.COMMON, TestPropertiesPaths.USER,
        TestPropertiesPaths.KEYSTORE, TestPropertiesPaths.KEYSTORE_WSS4J,
        TestEndpointWss4jPropertiesPaths.SIGNATURE })
public final class TestEntityEndpointSignatureWSS4J {

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
     * Password for the passworded message.
     */
    @Value("${security.credentials.password}")
    private String   password;
    /**
     * Password for the certificate for signing messages.
     */
    @Value("${keystore.password}")
    private String   passwordKey;
    /**
     * Path to the file containing the unsecured SOAP request.
     */
    @Value("${soap.request.path}")
    private String   pathUnsecure;
    /**
     * Username for the passworded message.
     */
    @Value("${security.credentials.user}")
    private String   username;

    /**
     * Constructs a {@code TestEntityEndpointSignatureWSS4J}.
     */
    public TestEntityEndpointSignatureWSS4J() {
        super();
        // TODO: Make this work
    }

    protected final Source getRequestEnvelope() {
        try {
            return new StreamSource(SecureSoapMessages.getSignedStream(
                    pathUnsecure, alias, passwordKey, alias, keystore));
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

}
