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

package com.bernardomg.example.swss.test.util.config.properties;

/**
 * Paths to the WSS4J-based endpoints test properties files.
 * <p>
 * These files contain the URL for an endpoint and its WSDL, each with their own
 * security protocol, and the SOAP action for the tested operation.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 */
public final class TestEndpointWss4jPropertiesPaths {

    /**
     * Encrypted endpoint.
     */
    public static final String ENCRYPTION      = "classpath:config/endpoint/test-endpoint-encryption-wss4j.properties";

    /**
     * Digested password endpoint.
     */
    public static final String PASSWORD_DIGEST = "classpath:config/endpoint/test-endpoint-password-digest-wss4j.properties";

    /**
     * Plain password endpoint.
     */
    public static final String PASSWORD_PLAIN  = "classpath:config/endpoint/test-endpoint-password-plain-wss4j.properties";

    /**
     * Signed endpoint.
     */
    public static final String SIGNATURE       = "classpath:config/endpoint/test-endpoint-signature-wss4j.properties";

    /**
     * Unsecure endpoint.
     */
    public static final String UNSECURE        = "classpath:config/endpoint/test-endpoint-unsecure.properties";

    /**
     * Private constructor to avoid initialization.
     */
    private TestEndpointWss4jPropertiesPaths() {
        super();
    }

}
