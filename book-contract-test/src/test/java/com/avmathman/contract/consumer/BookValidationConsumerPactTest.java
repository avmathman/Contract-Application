package com.avmathman.contract.consumer;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactBuilder;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.V4Pact;
import au.com.dius.pact.core.model.annotations.Pact;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "book-service", port = "0")
public class BookValidationConsumerPactTest extends BookBasePactTest {

    @Pact(consumer = "future-ui")
    public V4Pact validationErrorPact(PactBuilder builder) {
        return builder.usingLegacyDsl()
                .given("invalid book payload")
                .uponReceiving("POST book missing title")
                .path("/books")
                .method("POST")
                .headers("Content-Type", "application/json")
                .body(new PactDslJsonBody()
                        .stringType("author", "Robert Martin"))
                .willRespondWith()
                .status(400)
                .body(new PactDslJsonBody()
                        .stringValue("error", "VALIDATION_ERROR")
                        .stringType("message"))
                .toPact(V4Pact.class);
    }

    @Test
    void testValidationError(MockServer mockServer) {
        RestTemplate restTemplate = new RestTemplate();
        try {
            restTemplate.postForEntity(
                    mockServer.getUrl() + "/books",
                    Map.of("author", "Robert Martin"),
                    String.class
            );
            fail("Expected HttpClientErrorException for 400");
        } catch (HttpClientErrorException e) {
            assertEquals(400, e.getRawStatusCode());
            String body = e.getResponseBodyAsString();
            assertTrue(body.contains("\"error\":\"VALIDATION_ERROR\""));
        }
    }
}
