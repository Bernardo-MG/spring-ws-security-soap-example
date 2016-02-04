# Spring Web Services WS-Security Example

Small web service showing the use of Spring WS Security for a SOAP web service.

[![Release docs](https://img.shields.io/badge/docs-release-blue.svg)][site-release]
[![Development docs](https://img.shields.io/badge/docs-develop-blue.svg)][site-develop]

[![Release javadocs](https://img.shields.io/badge/javadocs-release-blue.svg)][javadoc-release]
[![Development javadocs](https://img.shields.io/badge/javadocs-develop-blue.svg)][javadoc-develop]

## Features

The project offers various examples showing how to set up the [WSS4J][wss4j] and [XWSS][xwss] [WS-Security][ws-security] implementations for the [Spring-WS][spring-ws] framework.

The following authentication methods, along a web service without any kind of authentication, are used:

- Plain password.
- Digested password.
- Signature.
- Encryption.

Each of them is applied to a different WS.

Additionally, several tests making sure that the web services work as intended.

## Documentation

Documentation is always generated for the latest release, kept in the 'master' branch:

- The [latest release documentation page][site-release].
- The [the latest release Javadoc site][javadoc-release].

Documentation is also generated from the latest snapshot, taken from the 'develop' branch:

- The [the latest snapshot documentation page][site-develop].
- The [the latest snapshot Javadoc site][javadoc-develop].

The documentation site sources come along the source code (as it is a Maven site), so it is always possible to generate them using the following Maven command:

```
$ mvn verify site
```

The verify phase is required, as otherwise some of the reports won't be created.

## Usage

The application is coded in Java, using Maven to manage the project.

### Prerequisites

The project has been tested on the following Java versions:
* JDK 7
* OpenJDK 7

All other dependencies are handled through Maven, and noted in the included POM file. This includes the plugins used for running the demo in an embedded web server.

### Running the demo

To run the demo just use the following Maven command:

```
$ mvn clean package jetty:run-war
```

### URLs for the web services

By default the web services will be deployed to the following URL:

```
http://localhost:8080/swss
```

But this can be changed in the POM, by editing the Jetty plugin's configuration.

With this, by running the project the following endpoints are mapped each to their own URL:

|Authentication method|WSS Implementation|URL|
|:-:|:-:|:-:|
|None|None|[http://localhost:8080/swss/unsecure/entities]|
|Plain Password|XWSS|[http://localhost:8080/swss/password/plain/xwss/entities]|
|Plain Password|WSS4J|[http://localhost:8080/swss/password/plain/wss4j/entities]|
|Digested Password|XWSS|[http://localhost:8080/swss/password/digest/xwss/entities]|
|Digested Password|WSS4J|[http://localhost:8080/swss/password/digest/wss4j/entities]|
|Signature|XWSS|[http://localhost:8080/swss/signature/xwss/entities]|
|Signature|WSS4J|[http://localhost:8080/wss4j/signature/xwss/entities]|
|Encryption|XWSS|[http://localhost:8080/swss/encryption/xwss/entities]|
|Encryption|WSS4J|[http://localhost:8080/wss4j/encryption/xwss/entities]|

#### WSDL

Each endpoint has their own WSDL file, which can be accessed by just adding the ".wsdl" suffix to the URL.

For example, for the unsecured web service the WSDL URL is as follows:

```
http://localhost:8080/swss/unsecure/entities.wsdl
```

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
[javadoc-develop]: http://docs.wandrell.com/maven/swss-soap-example/apidocs
[javadoc-release]: http://docs.wandrell.com/development/maven/swss-soap-example/apidocs
[license]: http://www.opensource.org/licenses/mit-license.php
[scm]: https://github.com/bernardo-mg/spring-ws-security-soap-example
[site-develop]: http://docs.wandrell.com/development/maven/swss-soap-example
[site-release]: http://docs.wandrell.com/maven/swss-soap-example

[ws-security]: https://www.oasis-open.org/committees/wss/
[xwss]: https://docs.oracle.com/cd/E17802_01/webservices/webservices/docs/1.6/tutorial/doc/XWS-SecurityIntro4.html
[wss4j]: https://ws.apache.org/wss4j/

[spring-ws]: http://projects.spring.io/spring-ws/
