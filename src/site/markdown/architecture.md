# Configuration info

## Spring usage

As the project is based on [Spring-WS][spring-ws] it makes a heavy use of [Spring][spring]. All the components are loaded and connected inside Spring contexts.

These contexts are defined in XML configuration files, found in the *context* folder. But these only tell which pieces are used and how are they connected. The actual values, including the classes used, are stored in properties files inside the *config* folder.

## The web service architecture

![Web service structure][ws-structure]

### Security concern

Security is a transversal need for the web service, and transparent to it. In the endpoint itself this is achieved with the use of interceptors, which will take all the SOAP messages sent or received and apply the security protocol to them.

### Endpoint layer

An endpoint is one of the access points to the web service, each having their own interface, based on a public contract offered as a WSDL file.

Each of them is based on the [ExampleEntityEndpoint][entity-endpoint].

### Service layer

The service layer contains all the business logic. In this example this takes the shape of a single service, the [ExampleEntityService][entity-service], which just hides the access to the persistence layer.

### Persistence layer

In the persistence layer access to the data used by the application is provided through the  [ExampleEntityRepository][entity-repository].

This is a [Spring-JPA][spring-jpa] repository, which with the help of [Hibernate][hibernate] will connect to an in-memory [H2][h2] database. This database is populated using a pair of scripts found inside the *db* resources folder.

## The client architecture

The clients work in a similar way to the endpoint, as they are based on a simple class, the [EntityClient][entity-client] in this case, to which the required security interceptors are added.

[h2]: http://www.h2database.com/
[hibernate]: http://hibernate.org/

[spring]: https://spring.io/
[spring-ws]: http://projects.spring.io/spring-ws/
[spring-jpa]: http://projects.spring.io/spring-data-jpa/

[entity-endpoint]: ./apidocs/com/wandrell/example/swss/endpoint/ExampleEntityEndpoint.html
[entity-client]: ./apidocs/com/wandrell/example/swss/client/EntityClient.html
[entity-service]: ./apidocs/com/wandrell/example/swss/service/data/ExampleEntityService.html
[entity-repository]: ./apidocs/com/wandrell/example/swss/repository/ExampleEntityRepository.html

[ws-structure]: ./images/web_service_structure.png
