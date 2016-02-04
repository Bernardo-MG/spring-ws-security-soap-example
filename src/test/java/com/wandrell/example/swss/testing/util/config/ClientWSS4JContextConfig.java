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
public final class ClientWSS4JContextConfig {

    /**
     * Context with signature-based security for the client and using WSS4J for
     * both the client and the web service.
     */
    public static final String ENCRYPTION               = "classpath:context/client/encryption/wss4j/test-client-encryption-wss4j-to-wss4j.xml";
    /**
     * Context with password-based security for the client, with invalid
     * authentication data and using WSS4J for both the client and the web
     * service.
     */
    public static final String ENCRYPTION_INVALID       = "classpath:context/client/encryption/wss4j/test-client-encryption-wss4j-to-wss4j-invalid.xml";
    /**
     * Context with encryption-based security for the client and using WSS4J for
     * the client but XWSS for the web service.
     */
    public static final String ENCRYPTION_TO_XWSS                = "classpath:context/client/encryption/wss4j/test-client-encryption-wss4j-to-xwss.xml";
    /**
     * Context with invalid signature-based security for the client and using
     * WSS4J for the client but XWSS for the web service.
     */
    public static final String ENCRYPTION_TO_XWSS_INVALID        = "classpath:context/client/encryption/wss4j/test-client-encryption-wss4j-to-xwss-invalid.xml";
    /**
     * Context with password-based security for the client and using WSS4J for
     * both the client and the web service.
     */
    public static final String PASSWORD_DIGEST          = "classpath:context/client/password/digest/wss4j/test-client-password-digest-wss4j-to-wss4j.xml";
    /**
     * Context with password-based security for the client, with invalid
     * authentication data and using WSS4J for both the client and the web
     * service.
     */
    public static final String PASSWORD_DIGEST_INVALID  = "classpath:context/client/password/digest/wss4j/test-client-password-digest-wss4j-to-wss4j-invalid.xml";
    /**
     * Context with password-based security for the client and using WSS4J for
     * the client but XWSS for the web service.
     */
    public static final String PASSWORD_DIGEST_TO_XWSS           = "classpath:context/client/password/digest/wss4j/test-client-password-digest-wss4j-to-xwss.xml";
    /**
     * Context with password-based security for the client, with invalid
     * authentication data and using WSS4J for the client but XWSS for the web
     * service.
     */
    public static final String PASSWORD_DIGEST_TO_XWSS_INVALID   = "classpath:context/client/password/digest/wss4j/test-client-password-digest-wss4j-to-xwss-invalid.xml";
    /**
     * Context with password-based security for the client and using WSS4J for
     * both the client and the web service.
     */
    public static final String PASSWORD_PLAIN           = "classpath:context/client/password/plain/wss4j/test-client-password-plain-wss4j-to-wss4j.xml";
    /**
     * Context with password-based security for the client, with invalid
     * authentication data and using WSS4J for both the client and the web
     * service.
     */
    public static final String PASSWORD_PLAIN_INVALID   = "classpath:context/client/password/plain/wss4j/test-client-password-plain-wss4j-to-wss4j-invalid.xml";
    /**
     * Context with password-based security for the client and using WSS4J for
     * the client but XWSS for the web service.
     */
    public static final String PASSWORD_PLAIN_TO_XWSS            = "classpath:context/client/password/plain/wss4j/test-client-password-plain-wss4j-to-xwss.xml";
    /**
     * Context with password-based security for the client, with invalid
     * authentication data and using WSS4J for the client but XWSS for the web
     * service.
     */
    public static final String PASSWORD_PLAIN_TO_XWSS_INVALID    = "classpath:context/client/password/plain/wss4j/test-client-password-plain-wss4j-to-xwss-invalid.xml";
    /**
     * Context with password-based security for the client, with invalid
     * authentication data and using WSS4J for both the client and the web
     * service.
     */
    public static final String PASSWORD_PLAIN_UNSECURED = "classpath:context/client/password/plain/wss4j/test-client-password-plain-wss4j-to-wss4j-unsecured.xml";
    /**
     * Context with signature-based security for the client and using WSS4J for
     * both the client and the web service.
     */
    public static final String SIGNATURE                = "classpath:context/client/signature/wss4j/test-client-signature-wss4j-to-wss4j.xml";
    /**
     * Context with invalid signature-based security for the client and using
     * WSS4J for both the client and the web service.
     */
    public static final String SIGNATURE_INVALID        = "classpath:context/client/signature/wss4j/test-client-signature-wss4j-to-wss4j-invalid.xml";
    /**
     * Context with signature-based security for the client and using WSS4J for
     * the client but XWSS for the web service.
     */
    public static final String SIGNATURE_TO_XWSS                 = "classpath:context/client/signature/wss4j/test-client-signature-wss4j-to-xwss.xml";
    /**
     * Context with invalid signature-based security for the client and using
     * WSS4J for the client but XWSS for the web service.
     */
    public static final String SIGNATURE_TO_XWSS_INVALID         = "classpath:context/client/signature/wss4j/test-client-signature-wss4j-to-xwss-invalid.xml";
    /**
     * Unsecure context for the client.
     */
    public static final String UNSECURE                                = "classpath:context/client/test-client-unsecure.xml";

    /**
     * Private constructor to avoid initialization.
     */
    private ClientWSS4JContextConfig() {
        super();
    }

}
