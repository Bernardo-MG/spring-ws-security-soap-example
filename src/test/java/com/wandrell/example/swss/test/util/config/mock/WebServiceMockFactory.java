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

package com.wandrell.example.swss.test.util.config.mock;

import org.mockito.Matchers;
import org.mockito.Mockito;

import com.wandrell.example.swss.model.ExampleEntity;
import com.wandrell.example.swss.service.domain.ExampleEntityService;

/**
 * Factory which creates mocked dependencies for the web service endpoints.
 *
 * @author Bernardo Martínez Garrido
 */
public final class WebServiceMockFactory {

    /**
     * Default constructor.
     */
    public WebServiceMockFactory() {
        super();
    }

    /**
     * Returns a mocked example entity domain service.
     * 
     * @return a mocked domain service
     */
    public final ExampleEntityService getExampleEntityService() {
        final ExampleEntityService service; // Mocked service
        final ExampleEntity entity;         // Mocked returned entity

        // Mocks the entity
        entity = Mockito.mock(ExampleEntity.class);
        Mockito.when(entity.getId()).thenReturn(1);
        Mockito.when(entity.getName()).thenReturn("name");

        // Mocks the service
        service = Mockito.mock(ExampleEntityService.class);
        Mockito.when(service.findById(Matchers.anyInt())).thenReturn(entity);

        return service;
    }

}
