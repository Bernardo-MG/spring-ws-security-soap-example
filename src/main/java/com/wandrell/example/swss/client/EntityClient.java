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
import org.springframework.ws.WebServiceMessageFactory;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import com.wandrell.example.swss.endpoint.ExampleEntityEndpoint;
import com.wandrell.example.ws.generated.entity.Entity;
import com.wandrell.example.ws.generated.entity.GetEntityRequest;
import com.wandrell.example.ws.generated.entity.GetEntityResponse;

/**
 * Client for acquiring {@link Entity} entities from the web service.
 * <p>
 * It is a simple client, which only takes a URL and the numeric identifier for
 * an entity, and then queries the web service for it, returning the result.
 * <p>
 * Internally a {@link SoapActionCallback} will be used when calling the web
 * service to avoid unreachable endpoint errors when using some authentication
 * methods, which otherwise would make it impossible to reach the endpoint.
 * <p>
 * If no result is received then the {@code null} value will be returned. And if
 * any exception occurs then it will be thrown as usual.
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
     * Constructs an {@code EntityClient}.
     */
    public EntityClient() {
        super();
    }

    /**
     * Constructs an {@code EntityClient} with the specified
     * {@code WebServiceMessageFactory}.
     *
     * @param factory
     *            the {@code WebServiceMessageFactory} to be used by the client
     */
    public EntityClient(final WebServiceMessageFactory factory) {
        super(factory);
    }

    /**
     * Acquires an {@code Entity} from the web service by the id.
     * <p>
     * If the id is invalid then the resulting response will contain a null
     * entity.
     * <p>
     * The method makes sure the expected SOAP action is used, to avoid
     * unreachable endpoint errors when using some authentication methods.
     *
     * @param url
     *            url to the web service
     * @param entityId
     *            id of the queried {@code Entity}
     * @return the {@code Entity} with the received id
     */
    public final Entity getEntity(final String url, final Integer entityId) {
        final GetEntityRequest request;   // Request for acquiring the entity
        final GetEntityResponse response; // Response with the resulting entity
        final Entity entity;              // Entity for the failed requests

        checkNotNull(url, "Received a null pointer as url");
        checkNotNull(entityId, "Received a null pointer as entity id");

        LOGGER.debug(
                String.format("Querying URL %1$s for id %2$d", url, entityId));

        // Generates request
        request = new GetEntityRequest();
        request.setId(entityId);

        // Sends request and receives response
        response = (GetEntityResponse) getWebServiceTemplate()
                .marshalSendAndReceive(url, request,
                        new SoapActionCallback(ExampleEntityEndpoint.ACTION));

        if ((response == null) || (response.getEntity() == null)) {
            // No response was received
            entity = null;
            LOGGER.debug("No response received");
        } else {
            entity = response.getEntity();
            LOGGER.debug(String.format(
                    "Received response with id %1$d and name %2$s",
                    entity.getId(), entity.getName()));
        }

        return entity;
    }

}
