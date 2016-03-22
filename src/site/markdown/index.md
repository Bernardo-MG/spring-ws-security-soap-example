# Spring Web Services and WSS

The [Spring-WS][spring-ws] framework allows creating new SOAP-based web services easily. These are meant to be contract-first services, where the service is created from a WSDL definition, taking advantage of the usual Spring features.

Among these the most important, the one being showcased by the project, is adding security with ease. Spring allows by default using the two most common [WS-Security][ws-security] implementations: [WSS4J][wss4j] and [XWSS][xwss].

---

## What will you find in this example?

The project just wants to show how to set up WS-Security for a Spring-based web service. And as a side effect it also shows how to prepare a Spring-based web service and client.

Several versions of the same endpoint are created, each using a different security
protocol. And for each of them there is a client.

All these endpoints can be accessed directly, by using [their own URLs][endpoint-url],
or with the help of the [console client][console-client], which just gives an UI to the various clients.

## Security

These are the security protocols used for the endpoints:

- No security, for the base service.
- Username and plain password header.
- Username and digested password header.
- Signed message.
- Encrypted data.

Each of them are offered in a XWSS and WSS4J variant.

[spring-ws]: http://projects.spring.io/spring-ws/

[ws-security]: https://www.oasis-open.org/committees/wss/
[xwss]: https://docs.oracle.com/cd/E17802_01/webservices/webservices/docs/1.6/tutorial/doc/XWS-SecurityIntro4.html
[wss4j]: https://ws.apache.org/wss4j/

[endpoint-url]: ./urls.html
[console-client]: ./client.html#consoleclient
