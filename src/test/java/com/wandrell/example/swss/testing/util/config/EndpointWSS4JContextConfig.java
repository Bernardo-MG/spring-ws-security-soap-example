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

package com.wandrell.example.swss.testing.util.config;

/**
 * Configuration class for the test context files.
 *
 * @author Bernardo Martínez Garrido
 */
public final class EndpointWSS4JContextConfig {

    /**
     * WS context with encryption-based security for the endpoint using WSS4J.
     */
    public static final String ENCRYPTION      = "classpath:context/endpoint/encryption/wss4j/test-endpoint-encryption-wss4j.xml";
    /**
     * WS context with password-based security for the endpoint using WSS4J.
     */
    public static final String PASSWORD_DIGEST = "classpath:context/endpoint/password/digest/wss4j/test-endpoint-password-digest-wss4j.xml";
    /**
     * WS context with password-based security for the endpoint using WSS4J.
     */
    public static final String PASSWORD_PLAIN  = "classpath:context/endpoint/password/plain/wss4j/test-endpoint-password-plain-wss4j.xml";
    /**
     * WS context with signature-based security for the endpoint using WSS4J.
     */
    public static final String SIGNATURE       = "classpath:context/endpoint/signature/wss4j/test-endpoint-signature-wss4j.xml";
    /**
     * Unsecure WS context for the endpoint.
     */
    public static final String UNSECURE        = "classpath:context/endpoint/test-endpoint-unsecure.xml";

    /**
     * Private constructor to avoid initialization.
     */
    private EndpointWSS4JContextConfig() {
        super();
    }

}
