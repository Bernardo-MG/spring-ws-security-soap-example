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

package com.wandrell.example.swss.service.data;

import com.wandrell.example.swss.model.ExampleEntity;

/**
 * Service for accessing {@link ExampleEntity} instances.
 * <p>
 * It just allows finding an entity by the id, which is required for the
 * endpoint.
 *
 * @author Bernardo Martínez Garrido
 */
public interface ExampleEntityService {

    /**
     * Returns a {@code ExampleEntity} with the given id.
     * <p>
     * If no instance exists with that id then the value {@code null} is
     * returned.
     *
     * @param identifier
     *            identifier of the {@code ExampleEntity} to find
     * @return the {@code ExampleEntity} with the given id or {@code null}
     */
    public ExampleEntity findById(final Integer identifier);

}
