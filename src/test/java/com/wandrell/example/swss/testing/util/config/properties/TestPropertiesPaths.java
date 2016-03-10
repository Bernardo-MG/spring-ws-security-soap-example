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
 * Configuration class for the test properties files paths.
 * <p>
 * These contain generic data required in several tests.
 *
 * @author Bernardo Martínez Garrido
 */
public final class TestPropertiesPaths {

    /**
     * Properties file with the test entity configuration.
     */
    public static final String ENTITY                           = "classpath:config/test-entity.properties";
    /**
     * Properties file with the keystore configuration.
     */
    public static final String KEYSTORE                         = "classpath:context/keystore/keystore.properties";
    /**
     * Properties file with the WSS4J keystore configuration.
     */
    public static final String KEYSTORE_WSS4J                   = "classpath:context/keystore/keystore-wss4j.properties";
    /**
     * Properties file with the invalid keystore configuration.
     */
    public static final String KEYSTORE_INVALID                 = "classpath:context/keystore/keystore2.properties";
    /**
     * Properties file with the test invalid digested user and password
     * configuration.
     */
    public static final String SECURITY_PASSWORD_DIGEST_INVALID = "classpath:config/security/test-security-password-digest-invalid.properties";
    /**
     * Properties file with the test invalid plain user and password
     * configuration.
     */
    public static final String SECURITY_PASSWORD_PLAIN_INVALID  = "classpath:config/security/test-security-password-plain-invalid.properties";
    /**
     * Properties file with the test user configuration.
     */
    public static final String USER                             = "classpath:config/user/test-user.properties";
    /**
     * Properties file with the invalid test user configuration.
     */
    public static final String USER_INVALID                     = "classpath:config/user/test-user-invalid.properties";
    /**
     * Properties file with the test WSDL configuration.
     */
    public static final String WSDL                             = "classpath:config/endpoint/test-wsdl.properties";

    /**
     * Private constructor to avoid initialization.
     */
    private TestPropertiesPaths() {
        super();
    }

}
