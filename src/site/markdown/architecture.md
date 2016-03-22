# Configuration info

While the architecture tries to be very simple there are some things which require a bit of detailing.

## The endpoint architecture

### Endpoint layer

Each endpoint is based on the [ExampleEntityEndpoint][entity-endpoint]. To this a security layer is added through security interceptors, which take and process the SOAP messages.

### Service layer

The endpoints are supported by the [ExampleEntityService][entity-service]. This just hides a persistence repository.

### Persistence layer

The persistence layer is created with the help of [Spring][spring], [Hibernate][hibernate] and an in-memory [H2][h2] database. The context file for this is in the *context/persistence.xml* file.

A pair of scripts found inside the *db* resources folder create and populate the tables.

Access to the persistent data is done through the [ExampleEntityRepository][entity-repository], a Spring JPA repository.

## The client architecture

The clients work in a similar way to the endpoint, as they are based on a simple class, the [EntityClient][entity-client] in this case, to which the required security interceptors are added.

[h2]: http://www.h2database.com/
[hibernate]: http://hibernate.org/
[spring]: https://spring.io/

[entity-endpoint]: ./apidocs/com/wandrell/example/swss/endpoint/ExampleEntityEndpoint.html
[entity-client]: ./apidocs/com/wandrell/example/swss/client/EntityClient.html
[entity-service]: ./apidocs/com/wandrell/example/swss/service/data/ExampleEntityService.html
[entity-repository]: ./apidocs/com/wandrell/example/swss/repository/ExampleEntityRepository.html
