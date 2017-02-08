# Spring Web Services WS-Security Example

Sample [Spring WS][spring-ws] SOAP web service which sets up various [WS-Security][ws-security] protocols.

Two implementations of WS-Security, [WSS4J][wss4j] and [XWSS][xwss], are supported. For each of them there will be a different endpoint for each authentication methods:

- Unsecure.
- Plain password.
- Digested password.
- Signature.
- Encryption.

All these endpoint are based on the same structure: an annotated class, a XSD file used to generate the WSDL and a simple model. Only the actual Spring configuration varies from one another.

The configuration makes use of XML and properties files. This way it is easier noticing what is the actual reusable code, and what is just part of the configuration.

[![Release docs](https://img.shields.io/badge/docs-release-blue.svg)][site-release]
[![Development docs](https://img.shields.io/badge/docs-develop-blue.svg)][site-develop]

[![Release javadocs](https://img.shields.io/badge/javadocs-release-blue.svg)][javadoc-release]
[![Development javadocs](https://img.shields.io/badge/javadocs-develop-blue.svg)][javadoc-develop]

## Features

The following authentication methods are used:

- Unsecure.
- Plain password.
- Digested password.
- Signature.
- Encryption.

Each of them includes a WSS4J and XWSS variant with their own endpoint. To find out the URIs where each endpoint is deployed check this same readme below, or the documentation page.

There is also a client, an a few context files prepared to set up it to call any of the endpoints.

Several tests are included to make sure all these endpoints and clients work as expected. But they also show how to test secured endpoints.

## Documentation

Documentation is always generated for the latest release, kept in the 'master' branch:

- The [latest release documentation page][site-release].
- The [the latest release Javadoc site][javadoc-release].

Documentation is also generated from the latest snapshot, taken from the 'develop' branch:

- The [the latest snapshot documentation page][site-develop].
- The [the latest snapshot Javadoc site][javadoc-develop].

The documentation site sources come along the source code (as it is a Maven site), so it is always possible to generate them using the following Maven command:

```
$ mvn verify site -P jetty
```

The verify phase is required, as otherwise some of the reports won't be created, while the jetty profile is used to run the tests with Jetty.

## Usage

The application is coded in Java, using Maven to manage the project.

### Prerequisites

The project has been tested on the following Java versions:
* JDK 7
* OpenJDK 7

All other dependencies are handled through Maven, and noted in the included POM file. This includes the plugins used for running the example in an embedded web server.

### Generated code

The project includes generated code. This is handled automatically by Maven, but if for some reason the generation fails then there will be missing classes in the project.

If that happens clean and rebuild with Maven.

### Running the example

To run the example locally just use the following Maven command for Jetty:

```
$ mvn jetty:run-war -P jetty
```

Or this one for Tomcat:

```
$ mvn tomcat7:run-war -P tomcat7
```

Of course, you can always package it and deploy the WAR manually in your preferred server:

```
$ mvn package
```

After this all the endpoint URLs are accessible. These can be found below, and require some sort of SOAP client, such as the console one included in the project.

This console client can be used by just running the ConsoleClient class, located in the com.wandrell.example.swss.client.console folder.

### Running the example with Java 8

Sadly, the example currently can't be run on Java 8. This is due a compatibility issue with Spring-WS and the security implementations.

Even the backwards compatibility of the new Java versions fails. Because of this the project should be run by using one of the tested JDKs, otherwise nothing can assure the demo will work correctly.

### URLs for the web services

By default the web services will be deployed to the following URL:

```
http://localhost:8080/swss
```

But this can be changed in the POM, by editing the Jetty plugin's configuration.

With this, by running the project the following endpoints are mapped each to their own URL:

|Authentication method|WSS Implementation|URL|
|:-:|:-:|:-:|
|None|None|[http://localhost:8080/swss/unsecure/entities.wsdl](http://localhost:8080/swss/unsecure/entities.wsdl)|
|Plain Password|XWSS|[http://localhost:8080/swss/password/plain/xwss/entities.wsdl](http://localhost:8080/swss/password/plain/xwss/entities.wsdl)|
|Plain Password|WSS4J|[http://localhost:8080/swss/password/plain/wss4j/entities.wsdl](http://localhost:8080/swss/password/plain/wss4j/entities.wsdl)|
|Digested Password|XWSS|[http://localhost:8080/swss/password/digest/xwss/entities.wsdl](http://localhost:8080/swss/password/digest/xwss/entities.wsdl)|
|Digested Password|WSS4J|[http://localhost:8080/swss/password/digest/wss4j/entities.wsdl](http://localhost:8080/swss/password/digest/wss4j/entities.wsdl)|
|Signature|XWSS|[http://localhost:8080/swss/signature/xwss/entities.wsdl](http://localhost:8080/swss/signature/xwss/entities.wsdl)|
|Signature|WSS4J|[http://localhost:8080/swss/signature/wss4j/entities.wsdl](http://localhost:8080/swss/signature/wss4j/entities.wsdl)|
|Encryption|XWSS|[http://localhost:8080/swss/encryption/xwss/entities.wsdl](http://localhost:8080/swss/encryption/xwss/entities.wsdl)|
|Encryption|WSS4J|[http://localhost:8080/swss/encryption/wss4j/entities.wsdl](http://localhost:8080/swss/encryption/wss4j/entities.wsdl)|

## Collaborate

Any kind of help with the project will be well received, and there are two main ways to give such help:

- Reporting errors and asking for extensions through the issues management
- or forking the repository and extending the project

### Issues management

Issues are managed at the GitHub [project issues tracker][issues], where any Github user may report bugs or ask for new features.

### Getting the code

If you wish to fork or modify the code, visit the [GitHub project page][scm], where the latest versions are always kept. Check the 'master' branch for the latest release, and the 'develop' for the current, and stable, development version.

## License

The project has been released under the [MIT License][license].

[issues]: https://github.com/bernardo-mg/swss-soap-example/issues
[javadoc-develop]: http://docs.wandrell.com/development/maven/swss-soap-example/apidocs
[javadoc-release]: http://docs.wandrell.com/maven/swss-soap-example/apidocs
[license]: http://www.opensource.org/licenses/mit-license.php
[scm]: https://github.com/bernardo-mg/spring-ws-security-soap-example
[site-develop]: http://docs.wandrell.com/development/maven/swss-soap-example
[site-release]: http://docs.wandrell.com/maven/swss-soap-example

[ws-security]: https://www.oasis-open.org/committees/wss/
[xwss]: https://docs.oracle.com/cd/E17802_01/webservices/webservices/docs/1.6/tutorial/doc/XWS-SecurityIntro4.html
[wss4j]: https://ws.apache.org/wss4j/

[spring-ws]: http://projects.spring.io/spring-ws/
