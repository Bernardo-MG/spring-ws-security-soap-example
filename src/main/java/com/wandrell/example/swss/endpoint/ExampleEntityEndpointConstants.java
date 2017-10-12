/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2016the original author or authors.
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

package com.wandrell.example.swss.endpoint;

/**
 * Constants for the example web service endpoints.
 * <p>
 * These define such things as the namespaces or the SOAP action used, and match
 * the data found in the generated WSDL.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 */
public final class ExampleEntityEndpointConstants {

	/**
	 * The action for acquiring the entities.
	 * <p>
	 * When sending requests to the web service this action should be used if
	 * the authentication systems modifies the message.
	 */
	public static final String ACTION = "http://wandrell.com/example/ws/entity/getEntity";

	/**
	 * Namespace for the example entities.
	 */
	public static final String ENTITY_NS = "http://wandrell.com/example/ws/entity";

	/**
	 * Name for the operation used to acquire an entity.
	 */
	public static final String REQUEST = "getEntityRequest";

	/**
	 * Private constructor to avoid initialization.
	 */
	private ExampleEntityEndpointConstants() {
		super();
	}

}
