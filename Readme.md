# Build Reactive Rest APIs with Spring WebFlux and Reactive MongoDB

Read the tutorial : https://www.callicoder.com/reactive-rest-apis-spring-webflux-reactive-mongo/

## Requirements

1. Java - 1.8.x

2. Maven - 3.x.x

3. MongoDB - 3.x.x

## Steps to Setup

**1. Clone the application**

```bash
git clone git@github.com:callicoder/spring-webflux-reactive-rest-api-demo.git
```

**2. Build and run the backend app using maven**

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

## Running integration tests

The project also contains integration tests for all the Rest APIs. You can run the integration tests by going to the root directory of the project typing `mvn test` in the terminal.