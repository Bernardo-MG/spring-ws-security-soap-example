# Spring SOAP-based WS Security Example

Small web service showing the use of Spring WS Security for a SOAP web service.

[![Release docs](https://img.shields.io/badge/docs-release-blue.svg)][site-release]
[![Development docs](https://img.shields.io/badge/docs-develop-blue.svg)][site-develop]

[![Release javadocs](https://img.shields.io/badge/javadocs-release-blue.svg)][javadoc-release]
[![Development javadocs](https://img.shields.io/badge/javadocs-develop-blue.svg)][javadoc-develop]

## Features

- Spring WS Security

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
* JDK 8
* OpenJDK 7

All other dependencies are handled through Maven, and noted in the included POM file. This includes the plugins used for running the demo in an embedded web server.

### Running the demo

To run the demo just use the following Maven command:

```
$ mvn clean package tomcat7:run-war
```

Note that as the project requires files on the WEB-INF folder the simpler tomcat7:run command can not be used.

### Memory problems with the Tomcat plugin

If the Tomcat plugin crashes due to memory problems add the following params to the virtual machine:

```
-Xmx1024m -Xms1024m -XX:MaxPermSize=512m -XX:PermSize=256m
```

In Eclipse this can be done in the 'run configuration' window, JRE panel, adding it to the VM arguments box.

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

[bintray-repo]: https://bintray.com/bernardo-mg/maven/spring-soap-ws-security-demo/view
[maven-repo]: http://mvnrepository.com/artifact/com.wandrell.demo/spring-soap-ws-security-demo
[issues]: https://github.com/bernardo-mg/spring-soap-ws-security-demo/issues
[javadoc-develop]: http://docs.wandrell.com/maven/spring-soap-ws-security-demo/apidocs
[javadoc-release]: http://docs.wandrell.com/development/maven/spring-soap-ws-security-demo/apidocs
[license]: http://www.opensource.org/licenses/mit-license.php
[scm]: https://github.com/bernardo-mg/spring-soap-ws-security-demo
[site-develop]: http://docs.wandrell.com/development/maven/spring-soap-ws-security-demo
[site-release]: http://docs.wandrell.com/maven/spring-soap-ws-security-demo
