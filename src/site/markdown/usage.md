# Usage

The project is just an example showing how to set up a Spring-based web service, for this reason it is meant to be easily run and tested.

## Tests

If needed the can be manually packaged and run in a server as any other web project.

But to check it quickly the included Jetty plugin for Maven can be used with the following command:

```
$ mvn jetty:run-war
```

After this the various [endpoint URLs][urls] will be accessible.

## Tests

Several unit and integration tests are included. These verify that the endpoints and security systems work as expected.

To run them use the following Maven command:

```
$ mvn verify
```

[urls]: ./urls.html
