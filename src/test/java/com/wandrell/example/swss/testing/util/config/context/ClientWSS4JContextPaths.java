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

package com.wandrell.example.swss.testing.util.config.context;

/**
 * Configuration class for the WSS4J-based test client context files paths.
 * <p>
 * Each of these files serves to create a Spring context for a single client
 * which uses the WSS4J implementation of the WSS standard.
 *
 * @author Bernardo Martínez Garrido
 */
public final class ClientWSS4JContextPaths {

    /**
     * Context file for the encrypted client.
     */
    public static final String ENCRYPTION      = "classpath:context/client/encryption/wss4j/client-encryption-wss4j.xml";
    /**
     * Context file for the digested password client.
     */
    public static final String PASSWORD_DIGEST = "classpath:context/client/password/digest/wss4j/client-password-digest-wss4j.xml";
    /**
     * Context file for the plain password client.
     */
    public static final String PASSWORD_PLAIN  = "classpath:context/client/password/plain/wss4j/client-password-plain-wss4j.xml";
    /**
     * Context file for the signed client.
     */
    public static final String SIGNATURE       = "classpath:context/client/signature/wss4j/client-signature-wss4j.xml";
    /**
     * Context file for the unsecure client.
     */
    public static final String UNSECURE        = "classpath:context/client/client-unsecure.xml";

    /**
     * Private constructor to avoid initialization.
     */
    private ClientWSS4JContextPaths() {
        super();
    }

}
