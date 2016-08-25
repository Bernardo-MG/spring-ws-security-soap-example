# Usage

The project is just an example showing how to set up a Spring-based web service, for this reason it is meant to be easy to run and test.

If needed the project can be manually packaged and run in a server as any other web project, but it is meant to be run by using one of the included embedded servers plugins.

To run it with Jetty use the following command:

```
$ mvn jetty:run-war -P jetty
```

For Tomcat 7 use this one:

```
$ mvn tomcat7:run-war -P tomcat7
```

It is recommended using Jetty instead of Tomcat.

After this is done the various [endpoints][urls] will be accessible.

## Tests

Several unit and integration tests are included. These verify that the endpoints, and their security protocols, work as expected.

To run them all use the following Maven command:

```
$ mvn verify -P jetty
```

[urls]: ./urls.html
