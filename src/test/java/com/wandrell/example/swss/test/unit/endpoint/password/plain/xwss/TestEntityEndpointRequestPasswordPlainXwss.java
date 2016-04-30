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

package com.wandrell.example.swss.test.unit.endpoint.password.plain.xwss;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

import com.wandrell.example.swss.test.util.config.context.ServletContextPaths;
import com.wandrell.example.swss.test.util.config.context.ServletXwssContextPaths;
import com.wandrell.example.swss.test.util.config.properties.EndpointPropertiesPaths;
import com.wandrell.example.swss.test.util.config.properties.EndpointXwssPropertiesPaths;
import com.wandrell.example.swss.test.util.config.properties.InterceptorXwssPropertiesPaths;
import com.wandrell.example.swss.test.util.config.properties.SoapPropertiesPaths;
import com.wandrell.example.swss.test.util.config.properties.TestEndpointXwssPropertiesPaths;
import com.wandrell.example.swss.test.util.config.properties.TestPropertiesPaths;
import com.wandrell.example.swss.test.util.factory.SecureSoapMessages;
import com.wandrell.example.swss.test.util.test.unit.endpoint.AbstractTestEntityEndpointRequest;

/**
 * Unit test for a XWSS plain password protected endpoint.
 *
 * @author Bernardo Martínez Garrido
 */
@ContextConfiguration(locations = { ServletContextPaths.APPLICATION_MOCKED,
        ServletXwssContextPaths.PASSWORD_PLAIN_MOCKED })
@TestPropertySource({ TestPropertiesPaths.WSDL,
        SoapPropertiesPaths.PASSWORD_PLAIN,
        InterceptorXwssPropertiesPaths.PASSWORD_PLAIN,
        EndpointXwssPropertiesPaths.PASSWORD_PLAIN,
        EndpointPropertiesPaths.COMMON, TestPropertiesPaths.USER,
        TestEndpointXwssPropertiesPaths.PASSWORD_PLAIN })
public final class TestEntityEndpointRequestPasswordPlainXwss
        extends AbstractTestEntityEndpointRequest {

    /**
     * Password for the passworded message.
     */
    @Value("${security.credentials.password}")
    private String password;

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
     * Constructs a {@code TestEntityEndpointPasswordPlainXWSS}.
     */
    public TestEntityEndpointRequestPasswordPlainXwss() {
        super();
    }

    @Override
    protected final Source getRequestEnvelope() {
        try {
            return new StreamSource(SecureSoapMessages
                    .getPlainPasswordStream(pathValid, username, password));
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

}
