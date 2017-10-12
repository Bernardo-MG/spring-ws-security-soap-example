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

package com.wandrell.example.swss.test.unit.endpoint.password.plain.wss4j;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

import com.wandrell.example.swss.test.util.config.context.ServletWss4jContextPaths;
import com.wandrell.example.swss.test.util.config.properties.EndpointWss4jPropertiesPaths;
import com.wandrell.example.swss.test.util.config.properties.InterceptorWss4jPropertiesPaths;
import com.wandrell.example.swss.test.util.config.properties.SoapPropertiesPaths;
import com.wandrell.example.swss.test.util.config.properties.TestEndpointWss4jPropertiesPaths;
import com.wandrell.example.swss.test.util.factory.SecureSoapMessages;
import com.wandrell.example.swss.test.util.test.unit.endpoint.AbstractTestEntityEndpointRequest;

/**
 * Unit test for a WSS4J plain password protected endpoint.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 */
@ContextConfiguration(locations = { ServletWss4jContextPaths.PASSWORD_PLAIN_MOCKED })
@TestPropertySource({ SoapPropertiesPaths.PASSWORD_PLAIN, InterceptorWss4jPropertiesPaths.PASSWORD_PLAIN,
		EndpointWss4jPropertiesPaths.PASSWORD_PLAIN, TestEndpointWss4jPropertiesPaths.PASSWORD_PLAIN })
public final class TestEntityEndpointRequestPasswordPlainWss4j extends AbstractTestEntityEndpointRequest {

	/**
	 * Password for the passworded message.
	 */
	@Value("${security.credentials.password}")
	private String password;

	/**
	 * Path to the file containing the valid SOAP request.
	 */
	@Value("${soap.request.template.path}")
	private String pathValid;

	/**
	 * Username for the passworded message.
	 */
	@Value("${security.credentials.user}")
	private String username;

	/**
	 * Constructs a {@code TestEntityEndpointPasswordPlainWSS4J}.
	 */
	public TestEntityEndpointRequestPasswordPlainWss4j() {
		super();
	}

	@Override
	protected final Source getRequestEnvelope() {
		try {
			return new StreamSource(SecureSoapMessages.getPlainPasswordStream(pathValid, username, password));
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}

}
