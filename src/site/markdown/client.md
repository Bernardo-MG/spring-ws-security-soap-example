# Endpoint clients

There is a client for each endpoint, which allows querying them from Java
code. Actually there is a single client, but several configuration files,
preparing said client for each of the endpoints.

This is the [EntityClient][entity-client], and the configuration files can
be found inside the resources folder, at the *config/client* folder.

## Console client

The [ConsoleClient][console-client] gives a UI to these clients, a simple
console-based interface. To make use of it just run this client, as it is
a Java runnable class.

[entity-client]: ./apidocs/com/wandrell/example/swss/client/EntityClient.html
[console-client]: ./apidocs/com/wandrell/example/swss/client/console/ConsoleClient.html
