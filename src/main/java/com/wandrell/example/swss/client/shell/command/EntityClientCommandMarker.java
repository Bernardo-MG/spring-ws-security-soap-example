/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2016 the original author or authors.
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

package com.wandrell.example.swss.client.shell.command;

import static com.google.common.base.Preconditions.checkNotNull;

import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.shell.core.annotation.CliOption;
import org.springframework.ws.client.WebServiceIOException;

import com.wandrell.example.swss.client.EntityClient;
import com.wandrell.example.ws.generated.entity.Entity;

/**
 * Commands which make use of a {@link EntityClient}.
 *
 * @author Bernardo Martínez Garrido
 */
public final class EntityClientCommandMarker implements CommandMarker {

    /**
     * URI to the endpoint which the client will contact.
     */
    private final String       endpointUri;
    /**
     * Client which will be used to contact the web service.
     */
    private final EntityClient entClient;

    /**
     * Constructs a {@code EntityClientCommandMarker}.
     */
    public EntityClientCommandMarker(final EntityClient client,
            final String uri) {
        super();

        entClient = checkNotNull(client, "Received a null pointer as client");
        endpointUri = checkNotNull(uri, "Received a null pointer as URI");
    }

    /**
     * Returns the URI which will be used by the client.
     *
     * @return the URI which will be used by the client
     */
    @CliCommand(value = "uri", help = "Prints the endpoint URI")
    public String getQueryUri() {
        return "Client uri: " + getEndpointUri();
    }

    /**
     * Queries the endpoint for an entity, identified by its id, and returns the
     * result.
     *
     * @return the result of querying the endpoint for an entity
     */
    @CliCommand(value = "query", help = "Queries the endpoint for an entity")
    public String getResponse(@CliOption(key = { "id" }, mandatory = true,
            help = "The id of the entity to query") final Integer id) {
        final Entity entity; // Queried entity
        String result;

        try {
            entity = getEntityClient().getEntity(getEndpointUri(), 1);
            result = "Response with id " + entity.getId() + " and name "
                    + entity.getName();
        } catch (final WebServiceIOException e) {
            result = "Error when querying the endpoint: "
                    + e.getMostSpecificCause().getLocalizedMessage();
        }

        return result;
    }

    /**
     * Returns the URI to the endpoint which the client will contact.
     *
     * @return the URI to the endpoint which the client will contact
     */
    private final String getEndpointUri() {
        return endpointUri;
    }

    /**
     * Returns the {@code EntityClient} which the commands make use of.
     *
     * @return the {@code EntityClient} which the commands make use of
     */
    private final EntityClient getEntityClient() {
        return entClient;
    }

}
