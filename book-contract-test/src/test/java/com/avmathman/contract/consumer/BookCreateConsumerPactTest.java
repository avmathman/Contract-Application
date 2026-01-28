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
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "book-service")
public class BookCreateConsumerPactTest {

    @Pact(consumer = "future-ui")
    public V4Pact createBookPact(PactBuilder builder) {
        return builder.usingLegacyDsl()
                .given("book can be created")
                .uponReceiving("POST create book")
                .path("/books")
                .method("POST")
                .headers("Content-Type", "application/json")
                .body(new PactDslJsonBody()
                        .stringType("title", "Clean Code")
                        .stringType("author", "Robert Martin"))
                .willRespondWith()
                .status(201)
                .body(new PactDslJsonBody()
                        .numberType("id", 10)
                        .stringType("title")
                        .stringType("author"))
                .toPact(V4Pact.class);
    }

    @Test
    void testCreateBook(MockServer mockServer) {
        RestTemplate restTemplate = new RestTemplate();
        var response = restTemplate.postForEntity(
                mockServer.getUrl() + "/books",
                Map.of("title", "Clean Code", "author", "Robert Martin"),
                String.class
        );
        assertEquals(201, response.getStatusCodeValue());
    }
}
