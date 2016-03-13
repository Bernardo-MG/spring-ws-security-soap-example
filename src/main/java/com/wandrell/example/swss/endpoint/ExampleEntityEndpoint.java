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
import com.wandrell.example.swss.service.data.ExampleEntityService;
import com.wandrell.example.ws.generated.entity.Entity;
import com.wandrell.example.ws.generated.entity.GetEntityRequest;
import com.wandrell.example.ws.generated.entity.GetEntityResponse;

/**
 * Web service endpoint for {@link ExampleEntity}.
 * <p>
 * It is as simple as it can be, having a single service which just receives a
 * {@link GetEntityRequest} asking for an entity with a specific id, and then
 * returns a {@code GetEntityResponse} which said id.
 * <p>
 * Both classes are JAXB annotated and generated from the same XSD file used for
 * creating the WSDL.
 * <p>
 * All the public constants contained in the endpoint match pieces of the
 * endpoint WSDL.
 * <p>
 * Note that the endpoint offers no security at all, as this concern is to be
 * handled by Spring.
 *
 * @author Bernardo Martínez Garrido
 */
@Endpoint
public class ExampleEntityEndpoint {

    /**
     * The action for acquiring the entities.
     * <p>
     * When sending requests to the web service this action should be used if
     * the authentication systems modifies the message.
     */
    public static final String               ACTION    = "http://wandrell.com/example/ws/entity/getEntity";
    /**
     * Namespace for the example entities.
     */
    public static final String               ENTITY_NS = "http://wandrell.com/example/ws/entity";
    /**
     * Name for the operation used to acquire an entity.
     */
    public static final String               REQUEST   = "getEntityRequest";
    /**
     * The logger used for logging the entity endpoint.
     */
    private static final Logger              LOGGER    = LoggerFactory
            .getLogger(ExampleEntityEndpoint.class);
    /**
     * Service for accessing the {@code ExampleEntity} instances handled by the
     * web service.
     * <p>
     * This is injected by Spring.
     */
    private final ExampleEntityService entityService;

    /**
     * Constructs a {@code ExampleEntityEndpoint}.
     * <p>
     * The constructor is meant to make use of Spring's IOC system.
     *
     * @param service
     *            the service for the {@code ExampleEntity} instances
     */
    @Autowired
    public ExampleEntityEndpoint(final ExampleEntityService service) {
        super();

        entityService = service;
    }

    /**
     * Returns the {@code Entity} with the id received through a SOAP request.
     * Both the request and response are handled through JAXB annotated classes.
     * <p>
     * The entity is transformed from a {@link ExampleEntity} instance returned
     * by the repository to the {@link Entity} used by the generated classes.
     *
     * @param request
     *            JAXB annotated representation of the SOAP request for the
     *            entity
     * @return JAXB annotated representation of the SOAP response with the
     *         entity
     */
    @PayloadRoot(localPart = REQUEST, namespace = ENTITY_NS)
    @SoapAction(ACTION)
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
                            entityResponse.getId(), entityResponse.getName()));
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
