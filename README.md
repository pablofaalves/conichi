## Conichi Backend Challenge

This REST web services app contains 3 endpoints:

- [/api/datatime/currenttime] Returns the current server time with timezone
- [/api/currency/convert] Provides currency convertion according to a source/target
- [/api/vat/countrycode] Provides currency convertion according to a source/target

This applications is also [SWAGGER](http://localhost:8081/) Enabled. 

## Requirements

For building and running the application you need:

- [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Maven 3](https://maven.apache.org)

## Running the application locally

There are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` method in the `com.conichi.BackendChallengeApplication` class from your IDE.

Alternatively you can use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) like so:

```shell
mvn spring-boot:run
```

## Copyright

Released under the Apache License 2.0. See the [LICENSE](https://github.com/pablofaalves/conichi/backend-challenge/blob/master/LICENSE) file.