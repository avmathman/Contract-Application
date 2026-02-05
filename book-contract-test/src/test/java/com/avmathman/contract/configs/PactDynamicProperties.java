package com.avmathman.contract.configs;

import org.springframework.test.context.DynamicPropertyRegistry;

public final class PactDynamicProperties {

    private PactDynamicProperties() {}

    public static void registerProviderBaseUrl(DynamicPropertyRegistry registry) {
        registry.add("provider.base-url", () -> "http://localhost:" + System.getProperty("pact.mockServer.port"));
    }
}
