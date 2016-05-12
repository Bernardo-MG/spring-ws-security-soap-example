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

package com.wandrell.example.swss.test.integration.client.encryption.wss4j;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

import com.wandrell.example.swss.test.util.config.context.ClientWss4jContextPaths;
import com.wandrell.example.swss.test.util.config.properties.TestEndpointWss4jPropertiesPaths;
import com.wandrell.example.swss.test.util.config.properties.TestPropertiesPaths;
import com.wandrell.example.swss.test.util.test.integration.client.AbstractITEntityClient;

/**
 * Integration test for an encrypted web service using WSS4J for both the client
 * and the web service.
 *
 * @author Bernardo Martínez Garrido
 */
@ContextConfiguration(locations = { ClientWss4jContextPaths.ENCRYPTION })
@TestPropertySource({ TestPropertiesPaths.ENTITY, TestPropertiesPaths.KEYSTORE,
        TestEndpointWss4jPropertiesPaths.ENCRYPTION })
public final class ITEntityClientEncryptionWss4jToWss4j
        extends AbstractITEntityClient {

    /**
     * Default constructor.
     */
    public ITEntityClientEncryptionWss4jToWss4j() {
        super();
    }

}