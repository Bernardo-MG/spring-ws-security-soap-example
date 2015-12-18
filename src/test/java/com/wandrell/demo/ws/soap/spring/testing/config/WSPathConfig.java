package com.wandrell.demo.ws.soap.spring.testing.config;

/**
 * Configuration class for the WS paths.
 *
 * This is meant for doing integration tests with a live instance of the WS, and
 * these paths should be pointing to the endpoints to test.
 *
 * @author Bernardo Martínez Garrido
 */
public final class WSPathConfig {

    /**
     * Path for the samples endpoint.
     */
    public static final String ENDPOINT_SAMPLES = "http://localhost:9966/spring-soap-ws-security-demo/unsecure/samples";

    /**
     * Private constructor to avoid initialization.
     */
    private WSPathConfig() {
        super();
    }

}
