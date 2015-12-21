package com.wandrell.demo.ws.soap.spring.testing.integration.client;

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
    }

    private Jaxb2Marshaller getMarshaller() {
        final Jaxb2Marshaller marshaller;

        marshaller = new Jaxb2Marshaller();
        marshaller.setPackagesToScan("com.wandrell.demo.ws.generated.*");

        return marshaller;
    }

}
