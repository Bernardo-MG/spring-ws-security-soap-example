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

package com.wandrell.example.swss.testing.util.config.properties;

/**
 * Configuration class for the WSS4J-based test endpoints URLs properties files
 * paths.
 * <p>
 * These files contain the URL for an endpoint and its WSDL. These endpoints use
 * the WSS4J implementation of the WSS standard.
 *
 * @author Bernardo Martínez Garrido
 */
public final class EndpointURLWSS4JPropertiesPaths {

    /**
     * Properties file with the encrypted endpoint URLs.
     */
    public static final String ENCRYPTION      = "classpath:config/endpoint/test-endpoint-encryption-wss4j.properties";
    /**
     * Properties file with the digested password endpoint URLs.
     */
    public static final String PASSWORD_DIGEST = "classpath:config/endpoint/test-endpoint-password-digest-wss4j.properties";
    /**
     * Properties file with the plain password endpoint URLs.
     */
    public static final String PASSWORD_PLAIN  = "classpath:config/endpoint/test-endpoint-password-plain-wss4j.properties";
    /**
     * Properties file with the signed endpoint URLs.
     */
    public static final String SIGNATURE       = "classpath:config/endpoint/test-endpoint-signature-wss4j.properties";
    /**
     * Properties file with the unsecure endpoint URLs.
     */
    public static final String UNSECURE        = "classpath:config/endpoint/test-endpoint-unsecure.properties";

    /**
     * Private constructor to avoid initialization.
     */
    private EndpointURLWSS4JPropertiesPaths() {
        super();
    }

}
