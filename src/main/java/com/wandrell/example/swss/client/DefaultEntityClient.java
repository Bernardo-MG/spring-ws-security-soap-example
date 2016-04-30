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
import org.springframework.ws.client.core.WebServiceMessageCallback;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import com.wandrell.example.swss.endpoint.ExampleEntityEndpointConstants;
import com.wandrell.example.swss.generated.entity.GetEntityRequest;
import com.wandrell.example.swss.generated.entity.GetEntityResponse;
import com.wandrell.example.swss.model.DefaultExampleEntity;
import com.wandrell.example.swss.model.ExampleEntity;

/**
 * Spring-based client for querying the web service endpoints.
 * <p>
 * It supports the only operation which the
 * {@link com.wandrell.example.swss.endpoint.ExampleEntityEndpoint
 * ExampleEntityEndpoint} has: querying an entity by its id.
 * <p>
 * As with that same endpoint, this client by default is unsecured. Any such
 * concern is to be taken care by Spring. This means that by default it will
 * only be able to query unsecure endpoints.
 * <p>
 * There is a problem with some security protocols, such as encryption, which
 * may make the endpoints unreachable. To solve this a
 * {@link SoapActionCallback} will be used by the client when querying, this
 * will add a SOAP action header to the request, which won't disappear even if
 * the message is modified.
 * <p>
 * Any other problem is taken care in a simple way. No exception is caught by
 * the client, meaning that any SOAP or transmission exception will leak out to
 * the main application, but if for some reason there is no exception and the
 * response is empty then a {@code null} value will be returned.
 *
 * @author Bernardo Martínez Garrido
 * @see ExampleEntity
 * @see com.wandrell.example.swss.endpoint.ExampleEntityEndpoint
 *      ExampleEntityEndpoint
 */
public final class DefaultEntityClient extends WebServiceGatewaySupport
        implements EntityClient {

    /**
     * The logger used for logging the entity client.
     */
    private static final Logger LOGGER = LoggerFactory
            .getLogger(DefaultEntityClient.class);

    /**
     * Default constructor.
     */
    public DefaultEntityClient() {
        super();
    }

    /**
     * Sends an id to the endpoint and receives back the data for the entity
     * with that same id. This method makes use of the default URI, which should
     * be set before calling it.
     * <p>
     * If for some reason, which may be caused by the id being invalid, an empty
     * response, or not response at all, is received then an entity with a
     * negative id will be returned.
     * <p>
     * The SOAP request will include, in the HTTP header, the SOAP action. This
     * way the unreachable endpoint error caused by some authentication methods,
     * such as encryption, can be avoided.
     *
     * @param identifier
     *            id of the queried entity
     * @return the entity with the received id
     */
    @Override
    public final ExampleEntity getEntity(final Integer identifier) {
        return getEntity(getDefaultUri(), identifier);
    }

    /**
     * Sends an id to the endpoint and receives back the data for the entity
     * with that same id.
     * <p>
     * If for some reason, which may be caused by the id being invalid, an empty
     * response, or not response at all, is received then an entity with a
     * negative id will be returned.
     * <p>
     * The SOAP request will include, in the HTTP header, the SOAP action. This
     * way the unreachable endpoint error caused by some authentication methods,
     * such as encryption, can be avoided.
     *
     * @param uri
     *            URI to the endpoint
     * @param identifier
     *            id of the queried entity
     * @return the entity with the received id
     */
    @Override
    public final ExampleEntity getEntity(final String uri,
            final Integer identifier) {
        final GetEntityRequest request;     // Request for acquiring the entity
        final GetEntityResponse response;   // Response with the result
        final ExampleEntity entity;         // Entity with the response data
        final WebServiceMessageCallback cb; // Callback with the SOAP action

        checkNotNull(uri, "Received a null pointer as URI");
        checkNotNull(identifier, "Received a null pointer as entity id");

        LOGGER.debug(String.format("Querying URI %1$s for id %2$d", uri,
                identifier));

        // Generates request
        request = new GetEntityRequest();
        request.setId(identifier);

        // Prepares callback
        cb = new SoapActionCallback(ExampleEntityEndpointConstants.ACTION);

        // Sends request and receives response
        response = (GetEntityResponse) getWebServiceTemplate()
                .marshalSendAndReceive(uri, request, cb);

        if ((response == null) || (response.getEntity() == null)) {
            // No response was received
            entity = new DefaultExampleEntity();
            entity.setName("");
            entity.setId(-1);

            LOGGER.debug("No response received");
        } else {
            entity = new DefaultExampleEntity();
            if (response.getEntity().getName() == null) {
                // The response was empty
                entity.setName("");
                entity.setId(-1);

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
