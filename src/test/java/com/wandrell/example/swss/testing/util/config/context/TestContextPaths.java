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

package com.wandrell.example.swss.testing.util.config.context;

/**
 * Configuration class for the test context files paths.
 * <p>
 * These are generic small context configurations required in some tests.
 *
 * @author Bernardo Martínez Garrido
 */
public final class TestContextPaths {

    /**
     * Default context file.
     * <p>
     * For those test which don't need any context configuration but require
     * loading Spring properties.
     */
    public static final String DEFAULT        = "classpath:context/test-default.xml";
    /**
     * Key stores context file.
     * <p>
     * Contains the configuration for the basic Java key stores.
     */
    public static final String KEYSTORE       = "classpath:context/keystore/keystore.xml";
    /**
     * WSS4J key stores context file.
     * <p>
     * Contains the configuration for the WSS4J Java key stores.
     */
    public static final String KEYSTORE_WSS4J = "classpath:context/keystore/keystore-wss4j.xml";

    /**
     * Private constructor to avoid initialization.
     */
    private TestContextPaths() {
        super();
    }

}
