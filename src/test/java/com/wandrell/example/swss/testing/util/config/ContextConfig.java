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
public final class ContextConfig {

    /**
     * Context with encryption-based security for the client and using XWSS for
     * the client but WSS4J for the web service.
     */
    public static final String CLIENT_ENCRYPTION_XWSS_TO_WSS4J          = "classpath:context/client/xwss/test-client-encryption-xwss-to-wss4j.xml";
    /**
     * Context with encryption-based security for the client, with invalid
     * authentication data and using XWSS for the client but WSS4J for the web
     * service.
     */
    public static final String CLIENT_ENCRYPTION_XWSS_TO_WSS4J_INVALID  = "classpath:context/client/xwss/test-client-encryption-xwss-to-wss4j-invalid.xml";
    /**
     * Context with encryption-based security for the client and using XWSS for
     * both the client and the web service.
     */
    public static final String CLIENT_ENCRYPTION_XWSS_TO_XWSS           = "classpath:context/client/xwss/test-client-encryption-xwss-to-xwss.xml";
    /**
     * Context with encryption-based security for the client, with invalid
     * authentication data and using XWSS for both the client and the web
     * service.
     */
    public static final String CLIENT_ENCRYPTION_XWSS_TO_XWSS_INVALID   = "classpath:context/client/xwss/test-client-encryption-xwss-to-xwss-invalid.xml";
    /**
     * Context with password-based security for the client and using WSS4J for
     * both the client and the web service.
     */
    public static final String CLIENT_PASSWORD_WSS4J_TO_WSS4J           = "classpath:context/client/wss4j/test-client-password-wss4j-to-wss4j.xml";
    /**
     * Context with password-based security for the client, with invalid
     * authentication data and using WSS4J for both the client and the web
     * service.
     */
    public static final String CLIENT_PASSWORD_WSS4J_TO_WSS4J_INVALID   = "classpath:context/client/wss4j/test-client-password-wss4j-to-wss4j-invalid.xml";
    /**
     * Context with password-based security for the client, with invalid
     * authentication data and using WSS4J for both the client and the web
     * service.
     */
    public static final String CLIENT_ENCRYPTION_WSS4J_TO_WSS4J_INVALID = "classpath:context/client/wss4j/test-client-encryption-wss4j-to-wss4j-invalid.xml";
    /**
     * Context with password-based security for the client and using WSS4J for
     * the client but XWSS for the web service.
     */
    public static final String CLIENT_PASSWORD_WSS4J_TO_XWSS            = "classpath:context/client/wss4j/test-client-password-wss4j-to-xwss.xml";
    /**
     * Context with password-based security for the client, with invalid
     * authentication data and using WSS4J for the client but XWSS for the web
     * service.
     */
    public static final String CLIENT_PASSWORD_WSS4J_TO_XWSS_INVALID    = "classpath:context/client/wss4j/test-client-password-wss4j-to-xwss-invalid.xml";
    /**
     * Context with password-based security for the client and using XWSS for
     * the client but WSS4J for the web service.
     */
    public static final String CLIENT_PASSWORD_XWSS_TO_WSS4J            = "classpath:context/client/xwss/test-client-password-xwss-to-wss4j.xml";
    /**
     * Context with password-based security for the client, with invalid
     * authentication data and using XWSS for the client but WSS4J for the web
     * service.
     */
    public static final String CLIENT_PASSWORD_XWSS_TO_WSS4J_INVALID    = "classpath:context/client/xwss/test-client-password-xwss-to-wss4j-invalid.xml";
    /**
     * Context with password-based security for the client and using XWSS for
     * both the client and the web service.
     */
    public static final String CLIENT_PASSWORD_XWSS_TO_XWSS             = "classpath:context/client/xwss/test-client-password-xwss-to-xwss.xml";
    /**
     * Context with password-based security for the client, with invalid
     * authentication data and using XWSS for both the client and the web
     * service.
     */
    public static final String CLIENT_PASSWORD_XWSS_TO_XWSS_INVALID     = "classpath:context/client/xwss/test-client-password-xwss-to-xwss-invalid.xml";
    /**
     * Context with signature-based security for the client and using WSS4J for
     * both the client and the web service.
     */
    public static final String CLIENT_SIGNATURE_WSS4J_TO_WSS4J          = "classpath:context/client/wss4j/test-client-signature-wss4j-to-wss4j.xml";
    /**
     * Context with signature-based security for the client and using WSS4J for
     * both the client and the web service.
     */
    public static final String CLIENT_ENCRYPTION_WSS4J_TO_WSS4J         = "classpath:context/client/wss4j/test-client-encryption-wss4j-to-wss4j.xml";
    /**
     * Context with invalid signature-based security for the client and using
     * WSS4J for both the client and the web service.
     */
    public static final String CLIENT_SIGNATURE_WSS4J_TO_WSS4J_INVALID  = "classpath:context/client/wss4j/test-client-signature-wss4j-to-wss4j-invalid.xml";
    /**
     * Context with signature-based security for the client and using WSS4J for
     * the client but XWSS for the web service.
     */
    public static final String CLIENT_SIGNATURE_WSS4J_TO_XWSS           = "classpath:context/client/wss4j/test-client-signature-wss4j-to-xwss.xml";
    /**
     * Context with encryption-based security for the client and using WSS4J for
     * the client but XWSS for the web service.
     */
    public static final String CLIENT_ENCRYPTION_WSS4J_TO_XWSS          = "classpath:context/client/wss4j/test-client-encryption-wss4j-to-xwss.xml";
    /**
     * Context with invalid signature-based security for the client and using
     * WSS4J for the client but XWSS for the web service.
     */
    public static final String CLIENT_SIGNATURE_WSS4J_TO_XWSS_INVALID   = "classpath:context/client/wss4j/test-client-signature-wss4j-to-xwss-invalid.xml";
    /**
     * Context with invalid signature-based security for the client and using
     * WSS4J for the client but XWSS for the web service.
     */
    public static final String CLIENT_ENCRYPTION_WSS4J_TO_XWSS_INVALID  = "classpath:context/client/wss4j/test-client-encryption-wss4j-to-xwss-invalid.xml";
    /**
     * Context with signature-based security for the client and using XWSS for
     * the client but WSS4J for the web service.
     */
    public static final String CLIENT_SIGNATURE_XWSS_TO_WSS4J           = "classpath:context/client/xwss/test-client-signature-xwss-to-wss4j.xml";
    /**
     * Context with signature-based security for the client, with invalid
     * authentication data and using XWSS for the client but WSS4J for the web
     * service.
     */
    public static final String CLIENT_SIGNATURE_XWSS_TO_WSS4J_INVALID   = "classpath:context/client/xwss/test-client-signature-xwss-to-wss4j-invalid.xml";
    /**
     * Context with signature-based security for the client and using XWSS for
     * both the client and the web service.
     */
    public static final String CLIENT_SIGNATURE_XWSS_TO_XWSS            = "classpath:context/client/xwss/test-client-signature-xwss-to-xwss.xml";
    /**
     * Context with signature-based security for the client, with invalid
     * authentication data and using XWSS for both the client and the web
     * service.
     */
    public static final String CLIENT_SIGNATURE_XWSS_TO_XWSS_INVALID    = "classpath:context/client/xwss/test-client-signature-xwss-to-xwss-invalid.xml";
    /**
     * Unsecure context for the client.
     */
    public static final String CLIENT_UNSECURE                          = "classpath:context/client/test-client-unsecure.xml";
    /**
     * WS context with encryption-based security for the endpoint using WSS4J.
     */
    public static final String ENDPOINT_ENCRYPTION_WSS4J                = "classpath:context/endpoint/test-endpoint-encryption-wss4j.xml";
    /**
     * WS context with encryption-based security for the endpoint using XWSS.
     */
    public static final String ENDPOINT_ENCRYPTION_XWSS                 = "classpath:context/endpoint/test-endpoint-encryption-xwss.xml";
    /**
     * WS context with password-based security for the endpoint using WSS4J.
     */
    public static final String ENDPOINT_PASSWORD_WSS4J                  = "classpath:context/endpoint/test-endpoint-password-wss4j.xml";
    /**
     * WS context with password-based security for the endpoint using XWSS.
     */
    public static final String ENDPOINT_PASSWORD_XWSS                   = "classpath:context/endpoint/test-endpoint-password-xwss.xml";
    /**
     * WS context with signature-based security for the endpoint using WSS4J.
     */
    public static final String ENDPOINT_SIGNATURE_WSS4J                 = "classpath:context/endpoint/test-endpoint-signature-wss4j.xml";
    /**
     * WS context with signature-based security for the endpoint using XWSS.
     */
    public static final String ENDPOINT_SIGNATURE_XWSS                  = "classpath:context/endpoint/test-endpoint-signature-xwss.xml";
    /**
     * Unsecure WS context for the endpoint.
     */
    public static final String ENDPOINT_UNSECURE                        = "classpath:context/endpoint/test-endpoint-unsecure.xml";

    /**
     * Private constructor to avoid initialization.
     */
    private ContextConfig() {
        super();
    }

}
