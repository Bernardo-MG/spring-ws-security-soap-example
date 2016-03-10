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
 * Configuration class for the test SOAP messages properties files paths.
 * <p>
 * Each file contains the information for finding or generating test SOAP
 * messages.
 *
 * @author Bernardo Martínez Garrido
 */
public final class SOAPPropertiesPaths {

    /**
     * Properties file with the encrypted SOAP test data.
     */
    public static final String ENCRYPTION      = "classpath:config/soap/test-soap-encryption.properties";
    /**
     * Properties file with the digested password SOAP test data.
     */
    public static final String PASSWORD_DIGEST = "classpath:config/soap/test-soap-password-digest.properties";
    /**
     * Properties file with the plain password SOAP test data.
     */
    public static final String PASSWORD_PLAIN  = "classpath:config/soap/test-soap-password-plain.properties";
    /**
     * Properties file with the signature SOAP test data.
     */
    public static final String SIGNATURE       = "classpath:config/soap/test-soap-signature.properties";
    /**
     * Properties file with the unsecure SOAP test data.
     */
    public static final String UNSECURE        = "classpath:config/soap/test-soap-unsecure.properties";

    /**
     * Private constructor to avoid initialization.
     */
    private SOAPPropertiesPaths() {
        super();
    }

}
