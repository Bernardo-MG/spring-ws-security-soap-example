package com.wandrell.demo.ws.soap.spring.testing.integration.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.wandrell.demo.ws.generated.entity.Entity;
import com.wandrell.demo.ws.soap.spring.client.EntityClient;
import com.wandrell.demo.ws.soap.spring.testing.config.WSPathConfig;

/**
 * Basis for an integration test.
 *
 * Requires the WS to be running.
 *
 * @author Bernardo Martínez Garrido
 */
public final class ITSampleClient {

    /**
     * Logger for the tests.
     */
    private static final Logger LOGGER = LoggerFactory
            .getLogger(ITSampleClient.class);

    public ITSampleClient() {
        super();
    }

    @Test
    public final void testEndpoint() {
        final EntityClient client;
        final Entity entity;

        client = new EntityClient();

        client.setDefaultUri(WSPathConfig.ENDPOINT_ENTITIES);
        client.setMarshaller(getMarshaller());
        client.setUnmarshaller(getMarshaller());

        entity = client.getEntity(1).getEntity();

        Assert.assertEquals(entity.getId(), 1);
        Assert.assertEquals(entity.getName(), "entity_1");

        LOGGER.debug(String.format("Received entity with id %d and name %s",
                entity.getId(), entity.getName()));
    }

    private Jaxb2Marshaller getMarshaller() {
        final Jaxb2Marshaller marshaller;

        marshaller = new Jaxb2Marshaller();
        marshaller.setPackagesToScan("com.wandrell.demo.ws.generated.*");

        return marshaller;
    }

}
