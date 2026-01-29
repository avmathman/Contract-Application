# Book Service Contract Tests

This repository contains a provider and a contract-test module that verify Pact contracts for the Book Service.

Summary
- `book-service` — the provider implementation (Spring Boot application).
- `book-contract-test` — Pact provider/consumer tests that verify pacts in `target/pacts`.

Prerequisites
- Java 17+ (or the project JDK configured in your environment)
- Maven

If the test cannot find the provider application class `com.avmathman.contract.BookServiceApplication`, either:

1) Add the provider module as a test-scoped dependency in `book-contract-test/pom.xml`:

```xml
<dependency>
  <groupId>com.avmathman</groupId>
  <artifactId>book-service</artifactId>
  <version>${project.version}</version>
  <scope>test</scope>
</dependency>
```

2) Or add a minimal Spring Boot application in the test sources and point the provider test to it, for example `book-contract-test/src/test/java/com/avmathman/contract/TestBookServiceApplication.java`:

```java
package com.avmathman.contract;

import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TestBookServiceApplication {}
```

and update the provider test to use `TestBookServiceApplication.class` in the `@SpringBootTest(classes = ...)` annotation.

Run provider contract tests

From the project root run:

```
mvn clean -am -pl book-contract-test test
```

This builds required modules (-am) and runs the tests in the `book-contract-test` module only.

Pact files

Place or generate pacts under `book-contract-test/target/pacts` or adjust the `@PactFolder` annotation in the provider test.

Troubleshooting
- "cannot find symbol: BookServiceApplication": follow the two options above.
- 4xx responses in tests: ensure provider `@State` setup methods configure the provider state to match the pact (DB inserts, mocked downstream services, etc.).

Contact
- If you want, I can also add the `TestBookServiceApplication` test class into `book-contract-test/src/test/java` for you and update `BookProviderPactTest` to use it (non-invasive option).
