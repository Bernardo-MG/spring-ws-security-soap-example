# Configuration info

## Spring usage

As the project is based on [Spring-WS][spring-ws] it makes heavy use of the [Spring][spring] framework. All the components are loaded and connected inside Spring contexts.

These contexts are defined in XML configuration files, found in the *context* folder. But these only tell which pieces are used and how are they connected. The actual values, including the classes used, are stored in properties files inside the *config* folder.

## The web service architecture

![Web service structure][ws-structure]

### Security concern

Security is a transversal need for the web service, and transparent to it. In practice it is applied to the endpoint itself the use of interceptors, which will take all the SOAP messages sent or received and apply the security protocol to them.

All the classes used for applying security and interceptors come from third parties, part from Spring and part from the XWSS or WSS4j security frameworks.

### Endpoint layer

An endpoint is one of the access points to the web service. There are several, each having their own interface based on a public contract offered as a WSDL file.

Each of them is based on the [ExampleEntityEndpoint][entity-endpoint]. One endpoint class for all the endpoints, no matter how they as secured, as security is applied transparently over the endpoint.

### Service layer

The service layer contains all the business logic. In this example this takes the shape of a single service, the [ExampleEntityService][entity-service], which just hides the access to the persistence layer.

In a real application this layer could become really complex, but for the example it is better having a minimal service layer.

### Persistence layer

In the persistence layer access to the data used by the application is provided through the  [ExampleEntityRepository][entity-repository].

This is a [Spring-JPA][spring-jpa] repository, which with the help of [Hibernate][hibernate] will connect to an in-memory [H2][h2] database. The database is populated using a pair of scripts found inside the *db* resources folder.

As with the service layer, this is just a minimal persistence layer.

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
