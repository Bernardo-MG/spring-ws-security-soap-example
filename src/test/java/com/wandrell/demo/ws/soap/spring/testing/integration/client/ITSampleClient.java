package com.wandrell.demo.ws.soap.spring.testing.integration.client;

import java.math.BigDecimal;

import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.testng.annotations.Test;

import com.wandrell.demo.ws.soap.spring.client.SampleClient;
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
        final SampleClient client;

        client = new SampleClient();

        client.setDefaultUri(WSPathConfig.ENDPOINT_SAMPLES);
        client.setMarshaller(getMarshaller());
        client.setUnmarshaller(getMarshaller());

        client.getSample(new BigDecimal(0.1), new BigDecimal(2));
    }

    private Jaxb2Marshaller getMarshaller() {
        final Jaxb2Marshaller marshaller;

        marshaller = new Jaxb2Marshaller();
        marshaller.setPackagesToScan("com.wandrell.demo.ws.generated.*");

        return marshaller;
    }

}
