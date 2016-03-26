# Usage

The project is just an example showing how to set up a Spring-based web service, for this reason it is meant to be easy to run and test.

If needed the project can be manually packaged and run in a server as any other web project, but it is meant to be run by using the included Maven Jetty plugin.

This can be done by using the following command:

```
$ mvn jetty:run-war
```

After this the various [endpoints][urls] will be accessible.

## Tests

Several unit and integration tests are included. These verify that the endpoints, and their security protocols, work as expected.

To run them all use the following Maven command:

```
$ mvn verify
```

[urls]: ./urls.html
