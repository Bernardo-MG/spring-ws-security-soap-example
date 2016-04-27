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

package com.wandrell.example.swss.test.unit.endpoint.signature.xwss;

import java.security.KeyStore;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

import com.wandrell.example.swss.test.util.config.context.ServletContextPaths;
import com.wandrell.example.swss.test.util.config.context.ServletXwssContextPaths;
import com.wandrell.example.swss.test.util.config.context.TestContextPaths;
import com.wandrell.example.swss.test.util.config.properties.EndpointPropertiesPaths;
import com.wandrell.example.swss.test.util.config.properties.EndpointXwssPropertiesPaths;
import com.wandrell.example.swss.test.util.config.properties.InterceptorXwssPropertiesPaths;
import com.wandrell.example.swss.test.util.config.properties.SoapPropertiesPaths;
import com.wandrell.example.swss.test.util.config.properties.TestEndpointXwssPropertiesPaths;
import com.wandrell.example.swss.test.util.config.properties.TestPropertiesPaths;
import com.wandrell.example.swss.test.util.factory.SecureSoapMessages;

/**
 * Unit test for a XWSS signed endpoint.
 *
 * @author Bernardo Martínez Garrido
 */
@ContextConfiguration(locations = { ServletContextPaths.APPLICATION_MOCKED,
        ServletXwssContextPaths.SIGNATURE, TestContextPaths.KEYSTORE })
@TestPropertySource({ TestPropertiesPaths.WSDL, SoapPropertiesPaths.UNSECURE,
        SoapPropertiesPaths.SIGNATURE, InterceptorXwssPropertiesPaths.SIGNATURE,
        EndpointXwssPropertiesPaths.SIGNATURE, EndpointPropertiesPaths.COMMON,
        TestPropertiesPaths.USER, TestEndpointXwssPropertiesPaths.SIGNATURE })
public final class TestEntityEndpointSignatureXwss {

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
     * Constructs a {@code TestEntityEndpointSignatureXWSS}.
     */
    public TestEntityEndpointSignatureXwss() {
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
