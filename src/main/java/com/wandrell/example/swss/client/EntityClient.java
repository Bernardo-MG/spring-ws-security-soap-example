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

package com.wandrell.example.swss.client;

import static com.google.common.base.Preconditions.checkNotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.ws.WebServiceMessageFactory;
import org.springframework.ws.client.core.WebServiceMessageCallback;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import com.wandrell.example.swss.endpoint.ExampleEntityEndpointConstants;
import com.wandrell.example.swss.model.DefaultExampleEntity;
import com.wandrell.example.swss.model.ExampleEntity;
import com.wandrell.example.ws.generated.entity.GetEntityRequest;
import com.wandrell.example.ws.generated.entity.GetEntityResponse;

/**
 * Client for querying the web service.
 * <p>
 * The only operation supported is querying an endpoint by sending an entity's
 * id, and receiving back that entity's data.
 * <p>
 * As with the endpoint, this client is unsecured. Any security concern is to be
 * taken care by Spring. This means that by default it will only be able to
 * query unsecure endpoints.
 * <p>
 * Some security protocols, such as encryption, may make endpoints unreachable.
 * To solve this a {@link SoapActionCallback} will be used by the client when
 * querying.
 * <p>
 * If any of the usual SOAP or transmission problems arise, the client will
 * throw an exception. But if for any reason there is no error but no response
 * is received, or an empty one is received, then a {@code null} value will be
 * returned.
 *
 * @author Bernardo Martínez Garrido
 */
public final class EntityClient extends WebServiceGatewaySupport {

    /**
     * The logger used for logging the entity client.
     */
    private static final Logger LOGGER = LoggerFactory
            .getLogger(EntityClient.class);

    /**
     * Default constructor.
     */
    public EntityClient() {
        super();
    }

    /**
     * Constructs a client with the specified message factory.
     *
     * @param factory
     *            the message factory to use
     */
    public EntityClient(final WebServiceMessageFactory factory) {
        super(factory);
    }

    /**
     * Acquires an entity from the web service by sending the id. The default
     * URI will be used, and should be set before calling the method.
     * <p>
     * If the id is invalid then the resulting response will contain an empty
     * entity, with a negative id.
     * <p>
     * The SOAP request will include, in the HTTP header, the SOAP action, to
     * avoid unreachable endpoints when using some authentication methods such
     * as encryption.
     *
     * @param entityId
     *            id of the queried entity
     * @return the entity with the received id
     */
    public final ExampleEntity getEntity(final Integer entityId) {
        return getEntity(getDefaultUri(), entityId);
    }

    /**
     * Acquires an entity from the web service by the id.
     * <p>
     * If the id is invalid then the resulting response will contain an empty
     * entity, with a negative id.
     * <p>
     * The SOAP request will include, in the HTTP header, the SOAP action, to
     * avoid unreachable endpoints when using some authentication methods such
     * as encryption.
     *
     * @param uri
     *            URI to the endpoint
     * @param entityId
     *            id of the queried entity
     * @return the entity with the received id
     */
    public final ExampleEntity getEntity(final String uri,
            final Integer entityId) {
        final GetEntityRequest request;     // Request for acquiring the entity
        final GetEntityResponse response;   // Response with the result
        final ExampleEntity entity;         // Entity with the response data
        final WebServiceMessageCallback cb; // Callback with the SOAP action

        checkNotNull(uri, "Received a null pointer as URI");
        checkNotNull(entityId, "Received a null pointer as entity id");

        LOGGER.debug(
                String.format("Querying URI %1$s for id %2$d", uri, entityId));

        // Generates request
        request = new GetEntityRequest();
        request.setId(entityId);

        // Prepares callback
        cb = new SoapActionCallback(ExampleEntityEndpointConstants.ACTION);

        // Sends request and receives response
        response = (GetEntityResponse) getWebServiceTemplate()
                .marshalSendAndReceive(uri, request, cb);

        if ((response == null) || (response.getEntity() == null)) {
            // No response was received
            entity = null;

            LOGGER.debug("No response received");
        } else {
            entity = new DefaultExampleEntity();
            if (response.getEntity().getName() == null) {
                // The response was empty
                LOGGER.debug("Received an empty response");
            } else {
                // The response was not empty
                BeanUtils.copyProperties(response.getEntity(), entity);

                LOGGER.debug(String.format(
                        "Received response with id %1$d and name %2$s",
                        entity.getId(), entity.getName()));
            }

        }

        return entity;
    }

}
