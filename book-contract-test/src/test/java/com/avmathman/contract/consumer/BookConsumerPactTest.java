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

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "book-service")
public class BookConsumerPactTest {

    @Pact(consumer = "future-ui")
    public V4Pact getBookPact(PactBuilder builder) {
        return builder.usingLegacyDsl()
                .given("book with id 1 exists")
                .uponReceiving("GET book by id")
                .path("/books/1")
                .method("GET")
                .willRespondWith()
                .status(200)
                .body(new PactDslJsonBody()
                        .numberType("id", 1)
                        .stringType("title")
                        .stringType("author"))
                .toPact(V4Pact.class);
    }

    @Test
    void testGetBook(MockServer mockServer) {
        RestTemplate restTemplate = new RestTemplate();
        var response = restTemplate.getForEntity(mockServer.getUrl() + "/books/1", String.class);
        assertEquals(200, response.getStatusCodeValue());
    }
}
