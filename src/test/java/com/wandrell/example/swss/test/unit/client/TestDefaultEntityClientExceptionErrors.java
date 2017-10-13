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

package com.wandrell.example.swss.test.unit.client;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.wandrell.example.swss.client.DefaultEntityClient;
import com.wandrell.example.swss.test.util.config.context.ClientWss4jContextPaths;

/**
 * Unit tests for {@link DefaultEntityClient} checking that the client throws
 * exceptions for common non SOAP errors.
 * <p>
 * Checks the following cases:
 * <ol>
 * <li>The client throws an exception when connecting to an invalid URL.</li>
 * </ol>
 *
 * @author Bernardo Mart&iacute;nez Garrido
 */
@ContextConfiguration(locations = { ClientWss4jContextPaths.UNSECURE })
public final class TestDefaultEntityClientExceptionErrors
        extends AbstractJUnit4SpringContextTests {

    /**
     * The client being tested.
     */
    @Autowired
    private DefaultEntityClient client;

    /**
     * Constructs a {@code TestEntityClientExceptionErrors}.
     */
    public TestDefaultEntityClientExceptionErrors() {
        super();
    }

    /**
     * The client throws an exception when connecting to an invalid URL.
     */
    @Test(expected = Exception.class)
    public final void testClient_InvalidURL() {
        client.getEntity("http://www.somewhere.com", 0);
    }

}
