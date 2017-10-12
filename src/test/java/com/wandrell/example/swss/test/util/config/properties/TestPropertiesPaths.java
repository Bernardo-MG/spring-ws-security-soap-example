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

package com.wandrell.example.swss.test.util.config.properties;

/**
 * Paths to various test properties files.
 * <p>
 * These contain generic data required in several tests.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 */
public final class TestPropertiesPaths {

	/**
	 * Test entity information.
	 */
	public static final String ENTITY = "classpath:config/test-entity.properties";

	/**
	 * Key stores access and configuration.
	 */
	public static final String KEYSTORE = "classpath:config/keystore/keystore.properties";

	/**
	 * Invalid key stores access and configuration.
	 */
	public static final String KEYSTORE_INVALID = "classpath:config/keystore/keystore2.properties";

	/**
	 * WSS4J key stores access and configuration.
	 */
	public static final String KEYSTORE_WSS4J = "classpath:config/keystore/keystore-wss4j.properties";

	/**
	 * Invalid digested password configuration.
	 */
	public static final String SECURITY_PASSWORD_DIGEST_INVALID = "classpath:config/security/test-security-password-digest-invalid.properties";

	/**
	 * Invalid plain password configuration.
	 */
	public static final String SECURITY_PASSWORD_PLAIN_INVALID = "classpath:config/security/test-security-password-plain-invalid.properties";

	/**
	 * Test user authentication data.
	 */
	public static final String USER = "classpath:config/user/test-user.properties";

	/**
	 * Invalid test user authentication data.
	 */
	public static final String USER_INVALID = "classpath:config/user/test-user-invalid.properties";

	/**
	 * WSDL data.
	 */
	public static final String WSDL = "classpath:config/endpoint/test-wsdl.properties";

	/**
	 * Private constructor to avoid initialization.
	 */
	private TestPropertiesPaths() {
		super();
	}

}
