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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wandrell.example.swss.model.ExampleEntity;
import com.wandrell.example.swss.repository.ExampleEntityRepository;

/**
 * Default implementation of {@link ExampleEntityService}, making use of
 * Spring for dependency injection.
 * <p>
 * It uses an {@link ExampleEntityRepository} for acquiring the entities.
 *
 * @author Bernardo Martínez Garrido
 */
@Service
public class DefaultExampleEntityService
        implements ExampleEntityService {

    /**
     * Repository for the {@code ExampleEntity} instances handled by the
     * service.
     * <p>
     * This is injected by Spring.
     */
    private final ExampleEntityRepository entityRepository;

    /**
     * Constructs a {@code DefaultExampleEntityService}.
     * <p>
     * The constructor is meant to make use of Spring's IOC system.
     *
     * @param repository
     *            the repository for the {@code ExampleEntity} instances
     */
    @Autowired
    public DefaultExampleEntityService(
            final ExampleEntityRepository repository) {
        super();

        this.entityRepository = repository;
    }

    @Override
    public final ExampleEntity findById(final Integer identifier) {
        return getExampleEntityRepository().findOne(identifier);
    }

    /**
     * Returns the repository used to acquire the {@code ExampleEntity}
     * instances.
     *
     * @return the repository used to acquire the {@code ExampleEntity}
     *         instances
     */
    private final ExampleEntityRepository getExampleEntityRepository() {
        return entityRepository;
    }

}
