# Usage

The project is just an example showing how to set up a Spring-based web service, for this reason it is meant to be easily run and tested.

If needed it can be manually packaged and run in a server, but it comes ready to be run with the Jetty plugin for Maven, which can be done just using the following command:

```
$ mvn jetty:run-war
```

## Tests

Several tests are included to verify the web services and the security systems work as expected. To run them all just use the following Maven command:

```
$ mvn verify
```
