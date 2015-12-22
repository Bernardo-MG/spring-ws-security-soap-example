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

package com.wandrell.example.ws.soap.spring.model;

import java.io.Serializable;

/**
 * Interface representing an entity to be used on the example
 * <p>
 * It just implements {@link PersistenceEntity}, adding a name field as
 * additional data for the tests.
 *
 * @author Bernardo Martínez Garrido
 */
public interface ExampleEntity extends Serializable {

    /**
     * Returns the ID assigned to this entity.
     * <p>
     * If no ID has been assigned yet, then the value will be {@code null} or
     * lower than zero.
     *
     * @return the entity's ID
     */
    public Integer getId();

    /**
     * Returns the name of the entity.
     *
     * @return the entity's name
     */
    public String getName();

    /**
     * Sets the ID assigned to this entity.
     *
     * @param identifier
     *            the ID for the entity
     */
    public void setId(final Integer identifier);

    /**
     * Changes the name of the entity.
     *
     * @param name
     *            the name to set on the entity
     */
    public void setName(final String name);

}
