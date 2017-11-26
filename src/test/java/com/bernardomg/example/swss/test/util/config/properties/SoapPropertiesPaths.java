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
 * Paths to the test SOAP messages properties files.
 * <p>
 * Each file contains the information for finding or generating SOAP messages
 * for the tests.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 */
public final class SoapPropertiesPaths {

    /**
     * Encrypted SOAP messages for the WSS4J interceptors.
     */
    public static final String ENCRYPTION_WSS4J = "classpath:config/soap/test-soap-encryption-wss4j.properties";

    /**
     * Encrypted SOAP messages for the XWSS interceptors.
     */
    public static final String ENCRYPTION_XWSS  = "classpath:config/soap/test-soap-encryption-xwss.properties";

    /**
     * Digested password SOAP messages.
     */
    public static final String PASSWORD_DIGEST  = "classpath:config/soap/test-soap-password-digest.properties";

    /**
     * Plain password SOAP messages.
     */
    public static final String PASSWORD_PLAIN   = "classpath:config/soap/test-soap-password-plain.properties";

    /**
     * Signed SOAP messages.
     */
    public static final String SIGNATURE        = "classpath:config/soap/test-soap-signature.properties";

    /**
     * Unsecure SOAP messages.
     */
    public static final String UNSECURE         = "classpath:config/soap/test-soap-unsecure.properties";

    /**
     * Private constructor to avoid initialization.
     */
    private SoapPropertiesPaths() {
        super();
    }

}
