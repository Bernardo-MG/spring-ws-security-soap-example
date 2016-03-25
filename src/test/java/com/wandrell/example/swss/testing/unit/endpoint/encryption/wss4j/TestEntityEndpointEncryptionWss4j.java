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

package com.wandrell.example.swss.testing.unit.endpoint.encryption.wss4j;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

import com.wandrell.example.swss.testing.util.config.context.ServletWss4jContextPaths;
import com.wandrell.example.swss.testing.util.config.context.TestContextPaths;
import com.wandrell.example.swss.testing.util.config.properties.EndpointWss4jPropertiesPaths;
import com.wandrell.example.swss.testing.util.config.properties.InterceptorWss4jPropertiesPaths;
import com.wandrell.example.swss.testing.util.config.properties.SoapPropertiesPaths;
import com.wandrell.example.swss.testing.util.config.properties.TestEndpointWss4jPropertiesPaths;
import com.wandrell.example.swss.testing.util.config.properties.TestPropertiesPaths;
import com.wandrell.example.swss.testing.util.test.unit.endpoint.AbstractTestEntityEndpointRequest;

/**
 * Implementation of {@code AbstractTestEntityEndpointRequest} for a XWSS
 * encrypted endpoint.
 *
 * @author Bernardo Martínez Garrido
 */
@ContextConfiguration(locations = { ServletWss4jContextPaths.APPLICATION_COMMON,
        ServletWss4jContextPaths.ENCRYPTION, TestContextPaths.KEYSTORE,
        TestContextPaths.KEYSTORE_WSS4J })
@TestPropertySource({ TestPropertiesPaths.WSDL, SoapPropertiesPaths.UNSECURE,
        SoapPropertiesPaths.ENCRYPTION_WSS4J,
        InterceptorWss4jPropertiesPaths.ENCRYPTION,
        EndpointWss4jPropertiesPaths.ENCRYPTION,
        EndpointWss4jPropertiesPaths.COMMON, TestPropertiesPaths.USER,
        TestPropertiesPaths.KEYSTORE, TestPropertiesPaths.KEYSTORE_WSS4J,
        TestEndpointWss4jPropertiesPaths.ENCRYPTION })
public final class TestEntityEndpointEncryptionWss4j
        extends AbstractTestEntityEndpointRequest {

    /**
     * Path to the file containing the valid SOAP request.
     */
    @Value("${soap.request.path}")
    private String pathValid;

    /**
     * Constructs a {@code TestEntityEndpointEncryptionWSS4J}.
     */
    public TestEntityEndpointEncryptionWss4j() {
        super();
    }

    @Override
    protected final Source getRequestEnvelope() {
        try {
            return new StreamSource(
                    new ClassPathResource(pathValid).getInputStream());
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

}
