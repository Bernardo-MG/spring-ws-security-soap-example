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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.wandrell.example.swss.config.EndpointConfig;
import com.wandrell.example.swss.model.ExampleEntity;
import com.wandrell.example.swss.service.ExampleEntityService;
import com.wandrell.example.ws.generated.entity.Entity;
import com.wandrell.example.ws.generated.entity.GetEntityRequest;
import com.wandrell.example.ws.generated.entity.GetEntityResponse;

/**
 * Web service endpoint for {@link ExampleEntity}.
 */
@Endpoint
public class EntityEndpoint {

    /**
     * Service for the {@code ExampleEntity} instances handled by the web
     * service.
     * <p>
     * This is injected by Spring.
     */
    private final ExampleEntityService entityService;

    /**
     * Constructs a {@code ExampleEntityEndpoint}.
     *
     * @param service
     *            the service for the {@code ExampleEntity} instances
     */
    @Autowired
    public EntityEndpoint(final ExampleEntityService service) {
        super();

        entityService = service;
    }

    /**
     * Acquires an {@code Entity} through a SOAP request.
     * <p>
     * The entity should be transformed from the {@link Entity} instance
     * returned by the repository to the {@link Entity} used by the SOAP
     * classes.
     *
     * @param request
     *            JAXB2 representation of a SOAP request for the entity
     * @return JAXB2 representation of a SOAP response with the entity
     */
    @PayloadRoot(localPart = EndpointConfig.GET_ENTITY_REQUEST, namespace = EndpointConfig.ENTITY_NS)
    @ResponsePayload
    public final GetEntityResponse getEntity(
            @RequestPayload final GetEntityRequest request) {
        final GetEntityResponse response; // SOAP response with the result
        final ExampleEntity entity;       // Found entity
        final Entity entityResponse;      // Entity to return

        response = new GetEntityResponse();

        entity = getExampleEntityService().findById(request.getId());

        if (entity != null) {
            entityResponse = new Entity();
            entityResponse.setId(entity.getId());
            entityResponse.setName(entity.getName());
            response.setEntity(entityResponse);
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
