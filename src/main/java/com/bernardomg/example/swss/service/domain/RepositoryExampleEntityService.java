/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2015-2017 the original author or authors.
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

package com.bernardomg.example.swss.service.domain;

import static com.google.common.base.Preconditions.checkNotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bernardomg.example.swss.model.DefaultExampleEntity;
import com.bernardomg.example.swss.model.ExampleEntity;
import com.bernardomg.example.swss.repository.ExampleEntityRepository;

/**
 * Example entity domain service implementation, using an
 * {@link ExampleEntityRepository} for acquiring the entities.
 * <p>
 * This service just wraps and hides an instance of the
 * {@link com.bernardomg.example.swss.repository.ExampleEntityRepository
 * ExampleEntityRepository}.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 */
@Service("exampleEntityService")
public class RepositoryExampleEntityService implements ExampleEntityService {

    /**
     * The logger used for logging the user details service usage.
     */
    private static final Logger           LOGGER = LoggerFactory
            .getLogger(RepositoryExampleEntityService.class);

    /**
     * Repository for the domain entities handled by the service.
     */
    private final ExampleEntityRepository entityRepository;

    /**
     * Constructs an entities service with the specified repository.
     *
     * @param repository
     *            the repository for the entity instances
     */
    @Autowired
    public RepositoryExampleEntityService(
            final ExampleEntityRepository repository) {
        super();

        this.entityRepository = checkNotNull(repository,
                "Received a null pointer as repository");
    }

    /**
     * Returns an entity with the given id.
     * <p>
     * If no instance exists with that id then an entity with a negative id is
     * returned.
     *
     * @param identifier
     *            identifier of the entity to find
     * @return the entity for the given id
     */
    @Override
    public final ExampleEntity findById(final Integer identifier) {
        ExampleEntity entity; // Entity to return

        checkNotNull(identifier, "Received a null pointer as identifier");

        entity = getExampleEntityRepository().findOne(identifier);

        if (entity == null) {
            // No entity found for the id
            LOGGER.debug("No entity found for id {}", identifier);
            entity = new DefaultExampleEntity();
        }

        return entity;
    }

    /**
     * Returns the repository used to acquire the domain entities.
     *
     * @return the repository used to acquire the domain entities
     */
    private final ExampleEntityRepository getExampleEntityRepository() {
        return entityRepository;
    }

}
