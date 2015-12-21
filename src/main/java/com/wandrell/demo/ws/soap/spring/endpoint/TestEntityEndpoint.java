package com.wandrell.demo.ws.soap.spring.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.wandrell.demo.ws.generated.entity.Entity;
import com.wandrell.demo.ws.generated.entity.GetEntityRequest;
import com.wandrell.demo.ws.generated.entity.GetEntityResponse;
import com.wandrell.demo.ws.soap.spring.model.TestEntity;
import com.wandrell.demo.ws.soap.spring.repository.TestEntityRepository;

/**
 * The Class EntityEndpoint.
 */
@Endpoint
public class TestEntityEndpoint {

    /** The Constant NAMESPACE_URI. */
    private static final String NAMESPACE_URI = "http://wandrell.com/demo/ws/entity";

    /** The sample repository. */
    private TestEntityRepository entityRepository;

    /**
     * Instantiates a new sample endpoint.
     *
     * @param entityRepository
     *            the sample repository
     */
    @Autowired
    public TestEntityEndpoint(final TestEntityRepository entityRepository) {
        this.entityRepository = entityRepository;
    }

    /**
     * Gets the sample.
     *
     * @param request
     *            the request
     * @return the sample
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getEntityRequest")
    @ResponsePayload
    public GetEntityResponse getEntity(GetEntityRequest request) {
        final GetEntityResponse response;
        final TestEntity entity;
        final Entity entityResponse;

        response = new GetEntityResponse();

        entity = entityRepository.findOne(request.getId());

        if (entity != null) {
            entityResponse = new Entity();
            entityResponse.setId(entity.getId());
            entityResponse.setName(entity.getName());
            response.setEntity(entityResponse);
        }

        return response;
    }
}