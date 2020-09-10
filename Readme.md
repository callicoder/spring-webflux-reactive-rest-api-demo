# Testing Reactive Rest APIs with Spring WebFlux and Reactive MongoDB

This repo is forked from https://github.com/callicoder/spring-webflux-reactive-rest-api-demo
This repo demonstrates different types of testing for microservices like Component, System Integration,
Contract & Performance tests. 

## Requirements

1. Java - 1.8.x

2. Maven - 3.x.x

3. MongoDB - 3.x.x

## Steps to Setup

**1. Clone the application**

```bash
git clone https://github.com/Dev2AtWork/spring-webflux-reactive-rest-api-demo.git
```

**2. Build and run the app using maven**

```bash
cd spring-webflux-reactive-rest-api-demo
mvn package
java -jar target/webflux-demo-0.0.1-SNAPSHOT.jar
```

Alternatively, you can run the app without packaging it using -

```bash
mvn spring-boot:run
```

The server will start at <http://localhost:8080>.

## Exploring the Rest APIs

The application defines following REST APIs

```
1. GET /tweets - Get All Tweets

2. POST /tweets - Create a new Tweet

3. GET /tweets/{id} - Retrieve a Tweet by Id

3. PUT /tweets/{id} - Update a Tweet

4. DELETE /tweets/{id} - Delete a Tweet

4. GET /stream/tweets - Stream tweets to a browser as Server-Sent Events
```

#### Running Component Tests (Pre-deployment tests)
```$xslt
mvn -Dtest=com.example.webfluxdemo.component.** test
```

####Running Integration tests from deployed environment
```$xslt
mvn -Dtest=com.example.webfluxdemo.integration.** test
```

####Generating & Running Contract Tests
```$xslt
mvn org.springframework.cloud:spring-cloud-contract-maven-plugin:generateTests
mvn -Dtest=ContractVerifierTest test
```
####Creating & Running Consumer stubs from Contract
```$xslt
mvn org.springframework.cloud:spring-cloud-contract-maven-plugin:convert
mvn org.springframework.cloud:spring-cloud-contract-maven-plugin:run
```
Default port for wiremock stub is 8080 to override - 
```$xslt
-Dspring.cloud.contract.verifier.http.port=<port_number>
```

Learn more about Spring Cloud Contract - https://spring.io/projects/spring-cloud-contract 

####Performance Tests
Gatling test scripts to run load tests. To execute
```
mvn gatling:test -Dgatling.simulationClass=com.example.webfluxdemo.performance.Journey
```