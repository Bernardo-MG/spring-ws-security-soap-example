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

package com.wandrell.example.swss.test.util.config.context;

/**
 * Paths to the WSS4J-based servlet context files.
 * <p>
 * Each of these files can be used to create a Spring context for a single
 * servlet, using WSS4J interceptors to handle security.
 * <p>
 * These are the same context configurations as the ones used for the
 * application servlets.
 *
 * @author Bernardo Martínez Garrido
 */
public final class ServletWss4jContextPaths {

    /**
     * Encrypted servlet.
     */
    public static final String ENCRYPTION      = "classpath:context/servlet/encryption/wss4j/servlet-encryption-wss4j.xml";

    /**
     * Digested password servlet.
     */
    public static final String PASSWORD_DIGEST = "classpath:context/servlet/password/digest/wss4j/servlet-password-digest-wss4j.xml";

    /**
     * Plain password servlet.
     */
    public static final String PASSWORD_PLAIN  = "classpath:context/servlet/password/plain/wss4j/servlet-password-plain-wss4j.xml";

    /**
     * Signed servlet.
     */
    public static final String SIGNATURE       = "classpath:context/servlet/signature/wss4j/servlet-signature-wss4j.xml";

    /**
     * Unsecure servlet.
     */
    public static final String UNSECURE        = "classpath:context/servlet/servlet-unsecure.xml";

    /**
     * Private constructor to avoid initialization.
     */
    private ServletWss4jContextPaths() {
        super();
    }

}
