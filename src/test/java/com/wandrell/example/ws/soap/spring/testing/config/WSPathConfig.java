package com.wandrell.example.ws.soap.spring.testing.config;

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
    public static final String ENDPOINT_ENTITIES = "http://localhost:8080/sws/entities";

    /**
     * Private constructor to avoid initialization.
     */
    private WSPathConfig() {
        super();
    }

}
