# Endpoint clients

There is a client for each endpoint, but these are only accessible through Java code. Actually there are not several clients, but one with several configurations, one for each security protocol.

This single client is the [EntityClient][entity-client], and the configuration files, which can be used to generate a Spring context for each variant, are in the *context/client* and *config/client* folders.

## Console client

To ease the use of these clients where is the [ConsoleClient][console-client], offering, as the name implies, a console-based UI for all the others. To make use of it just run the ConsoleClient class in an environment with access to a command console.

[entity-client]: ./apidocs/com/bernardomg/example/swss/client/EntityClient.html
[console-client]: ./apidocs/com/bernardomg/example/swss/client/console/ConsoleClient.html
