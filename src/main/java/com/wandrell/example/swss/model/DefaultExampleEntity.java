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

package com.wandrell.example.swss.model;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.google.common.base.MoreObjects;

/**
 * Implementation of {@link ExampleEntity} for JPA and XML marshalling.
 *
 * @author Bernardo Martínez Garrido
 */
@Entity(name = "ExampleEntity")
@Table(name = "example_entities")
public final class DefaultExampleEntity implements ExampleEntity {

    /**
     * Serialization ID.
     */
    @Transient
    private static final long serialVersionUID = 1328776989450853491L;
    /**
     * Entity's ID.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Integer           entityId;
    /**
     * Name of the entity.
     * <p>
     * This is to have additional data apart from the id, to be used on the
     * tests.
     */
    @Column(name = "name", nullable = false)
    private String            entityName;

    /**
     * Constructs a {@code JPAExampleEntity}.
     */
    public DefaultExampleEntity() {
        super();
    }

    @Override
    public final boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }

        final DefaultExampleEntity other = (DefaultExampleEntity) obj;
        return Objects.equals(entityId, other.entityId);
    }

    @Override
    public final Integer getId() {
        return entityId;
    }

    @Override
    public final String getName() {
        return entityName;
    }

    @Override
    public final int hashCode() {
        return Objects.hash(entityId);
    }

    @Override
    public final void setId(final Integer id) {
        entityId = checkNotNull(id, "Received a null pointer as id");
    }

    @Override
    public final void setName(final String name) {
        entityName = checkNotNull(name, "Received a null pointer as name");
    }

    @Override
    public final String toString() {
        return MoreObjects.toStringHelper(this).add("entityId", entityId)
                .toString();
    }

}
