# Usage

The project is prepared to be run though the Jetty or Tomcat plugins.

To run it with Jetty use the following command:

```
$ mvn jetty:run-war -P jetty
```

For Tomcat 7 use this one:

```
$ mvn tomcat7:run-war -P tomcat7
```

Once the project is running the various [endpoints][urls] will be accessible.

Packaging the project and running it out of the embedded servers is not recommended, but just because it requires a bit more of work which won't be detailed here.

## Tests

Unit and integration tests are included. Among other things they verify that the endpoints, and their security settings, work as expected.

To run them use the following Maven command:

```
$ mvn verify -P jetty
```

This will run the tests and use Jetty for the integration tests.

If you preffer by running the project on Tomcat 7 just use the following command:

```
$ mvn verify -P tomcat7
```

[urls]: ./urls.html
