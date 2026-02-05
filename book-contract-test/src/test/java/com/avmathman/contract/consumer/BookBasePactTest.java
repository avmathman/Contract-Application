package com.avmathman.contract.consumer;

import com.avmathman.contract.configs.PactDynamicProperties;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

public abstract class BookBasePactTest {
    @DynamicPropertySource
    static void overrideProps(DynamicPropertyRegistry registry) {
        PactDynamicProperties.registerProviderBaseUrl(registry);
    }
}
