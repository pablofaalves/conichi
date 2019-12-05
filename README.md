## Conichi Backend Challenge

This REST web services app serves 3 endpoints:

- [/api/datatime/currenttime](http://localhost:8081/api/datatime/currenttime) 
	Returns the current server time with timezone
- [/api/currency/convert](http://localhost:8081/api/currency/convert) 
	Provides currency conversion according to a source/target
- [/api/vat/countrycode](http://localhost:8081/api/vat/countrycode) 
	Provides currency conversion according to a source/target

This applications is also [SWAGGER](http://localhost:8081/) Enabled. 

## Requirements

For building and running the application you need:

- [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Maven 3](https://maven.apache.org)
- [Docker CE](https://www.docker.com/) Optional

## Using Maven to Build the app

run `mvn clean package -P unpacked` on your shell


## Running the application locally

There are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` method in the `com.conichi.BackendChallengeApplication` class from your IDE.

Alternatively you can use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) like so:

```shell
mvn spring-boot:run
```

## Using Docker

Just run `docker-compose up` on your and access your [localhost](http://localhost:8081/)


## Copyright

Released under the Apache License 2.0. See the [LICENSE](https://github.com/pablofaalves/conichi/blob/master/LICENSE) file.