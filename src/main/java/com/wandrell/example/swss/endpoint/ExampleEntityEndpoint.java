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

package com.wandrell.example.swss.endpoint;

import static com.google.common.base.Preconditions.checkNotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import org.springframework.ws.soap.server.endpoint.annotation.SoapAction;

import com.wandrell.example.swss.model.ExampleEntity;
import com.wandrell.example.swss.service.domain.ExampleEntityService;
import com.wandrell.example.ws.generated.entity.Entity;
import com.wandrell.example.ws.generated.entity.GetEntityRequest;
import com.wandrell.example.ws.generated.entity.GetEntityResponse;

/**
 * Endpoint for the example entities.
 * <p>
 * It just receives a {@link GetEntityRequest} with the id for an entity and
 * then returns a {@link GetEntityResponse} containing the entity for said id.
 * <p>
 * Both the request and response classes are the JAXB annotated classes
 * generated from the XSD file, which is the same one used for creating the WSDL
 * for the endpoints.
 * <p>
 * Note that the endpoint offers no security at all, as this concern is to be
 * handled by Spring.
 * <p>
 * As the annotations found in the class imply, this endpoint is meant to be
 * located and wired automatically by Spring.
 *
 * @author Bernardo Martínez Garrido
 * @see GetEntityResponse
 * @see GetEntityRequest
 * @see Entity
 */
@Endpoint
public class ExampleEntityEndpoint {

    /**
     * The logger used for logging the entity endpoint.
     */
    private static final Logger        LOGGER = LoggerFactory
            .getLogger(ExampleEntityEndpoint.class);

    /**
     * Service for accessing the {@code ExampleEntity} instances handled by the
     * web service.
     * <p>
     * This is injected by Spring.
     */
    private final ExampleEntityService entityService;

    /**
     * Constructs an endpoint for the example entities.
     *
     * @param service
     *            the service for the {@code ExampleEntity} instances
     */
    @Autowired
    public ExampleEntityEndpoint(final ExampleEntityService service) {
        super();

        entityService = checkNotNull(service,
                "Received a null pointer as service");
    }

    /**
     * Returns a {@code GetEntityResponse} containing an {@code Entity} for the
     * id received on the {@code GetEntityRequest}.
     * <p>
     * These are all JAXB annotated classes, representing the contents of SOAP
     * requests and responses.
     * <p>
     * The actual data will be acquired from the persistence layer on a
     * {@link ExampleEntity} bean, which will be transformed to the expected
     * result.
     *
     * @param request
     *            payload of the SOAP request for the entity
     * @return payload for the SOAP response with the entity
     */
    @PayloadRoot(localPart = ExampleEntityEndpointConstants.REQUEST,
            namespace = ExampleEntityEndpointConstants.ENTITY_NS)
    @SoapAction(ExampleEntityEndpointConstants.ACTION)
    @ResponsePayload
    public final GetEntityResponse
            getEntity(@RequestPayload final GetEntityRequest request) {
        final GetEntityResponse response; // SOAP response with the result
        final ExampleEntity entity;       // Found entity
        final Entity entityResponse;      // Entity to return

        checkNotNull(request, "Received a null pointer as request");

        LOGGER.debug(
                String.format("Received request for id %d", request.getId()));

        // Acquires the entity
        entity = getExampleEntityService().findById(request.getId());

        response = new GetEntityResponse();
        if (entity == null) {
            LOGGER.debug("Entity not found");
        } else {
            // The entity is transformed from the persistence model to the SOAP
            // one
            entityResponse = new Entity();
            entityResponse.setId(entity.getId());
            entityResponse.setName(entity.getName());
            response.setEntity(entityResponse);

            LOGGER.debug(
                    String.format("Found entity with id %1$d and name %2$s",
                            entity.getId(), entity.getName()));
        }

        return response;
    }

    /**
     * Returns the service used to handle the {@code ExampleEntity} instances.
     *
     * @return the service used to handle the {@code ExampleEntity} instances
     */
    private final ExampleEntityService getExampleEntityService() {
        return entityService;
    }

}
