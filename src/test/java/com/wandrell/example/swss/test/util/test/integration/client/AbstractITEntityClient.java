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

package com.wandrell.example.swss.test.util.test.integration.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.wandrell.example.swss.client.EntityClient;
import com.wandrell.example.swss.model.ExampleEntity;
import com.wandrell.example.swss.test.util.config.properties.TestPropertiesPaths;

/**
 * Abstract integration tests for an {@link EntityClient} testing that it
 * handles messages correctly.
 * <p>
 * Checks the following cases:
 * <ol>
 * <li>A valid id returns the expected value.</li>
 * <li>An invalid id returns an empty entity.</li>
 * </ol>
 * <p>
 * Pay attention to the fact that it requires the WS to be running, and a Spring
 * context to populate the test data.
 *
 * @author Bernardo Martínez Garrido
 */
@TestPropertySource({ TestPropertiesPaths.ENTITY })
public abstract class AbstractITEntityClient
        extends AbstractTestNGSpringContextTests {

    /**
     * Client being tested.
     */
    @Autowired
    private EntityClient client;

    /**
     * Id of the returned entity.
     */
    @Value("${entity.id}")
    private Integer      entityId;

    /**
     * Name of the returned entity.
     */
    @Value("${entity.name}")
    private String       entityName;

    /**
     * URL for the WS.
     */
    @Value("${endpoint.url}")
    private String       wsUrl;

    /**
     * Default constructor.
     */
    public AbstractITEntityClient() {
        super();
    }

    /**
     * Tests that an invalid id returns an empty entity.
     */
    @Test
    public final void testEndpoint_InvalidId_ReturnsEmpty() {
        final ExampleEntity entity; // Returned entity

        entity = client.getEntity(wsUrl, -123);

        Assert.assertEquals(entity.getId(), new Integer(-1));
    }

    /**
     * Tests that a valid id returns the expected value.
     */
    @Test
    public final void testEndpoint_ValidId_ReturnsValid() {
        final ExampleEntity entity; // Returned entity

        entity = client.getEntity(wsUrl, 1);

        Assert.assertEquals(entity.getId(), entityId);
        Assert.assertEquals(entity.getName(), entityName);
    }

}
