# Usage

The project is prepared to be run though the Jetty or Tomcat plugins. If needed it can be manually packaged and run in a server as any other web project, but it is not recommended.

To run it with Jetty use the following command:

```
$ mvn jetty:run-war -P jetty
```

For Tomcat 7 use this one:

```
$ mvn tomcat7:run-war -P tomcat7
```

Using Jetty is recommended over Tomcat.

Once the project is running the various [endpoints][urls] will be accessible.

## Tests

Several unit and integration tests are included. These verify that the endpoints, and their security settings, work as expected.

To run them all use the following Maven command:

```
$ mvn verify -P jetty
```

This will run the tests and use Jetty for the integration tests. To test them by running the project on Tomcat 7 just use the following command:

```
$ mvn verify -P tomcat7
```

[urls]: ./urls.html
