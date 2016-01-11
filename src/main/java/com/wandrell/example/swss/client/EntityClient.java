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

import org.springframework.ws.WebServiceMessageFactory;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import com.wandrell.example.swss.config.EndpointConfig;
import com.wandrell.example.ws.generated.entity.Entity;
import com.wandrell.example.ws.generated.entity.GetEntityRequest;
import com.wandrell.example.ws.generated.entity.GetEntityResponse;

/**
 * Client for acquiring {@link Entity} entities from the web service.
 */
public final class EntityClient extends WebServiceGatewaySupport {

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
     *
     * @param id
     *            id of the {@code Entity}
     * @return the {@code Entity} with the received id
     */
    public final Entity getEntity(final Integer id) {
        final GetEntityRequest request;   // Request for acquiring the entity
        final GetEntityResponse response; // Response with the resulting entity

        request = new GetEntityRequest();
        request.setId(id);

        response = (GetEntityResponse) getWebServiceTemplate()
                .marshalSendAndReceive(request,
                        new SoapActionCallback(EndpointConfig.ENTITY_NS));

        return response.getEntity();
    }

}
