# Spring Web Services and WSS

The [Spring-WS][spring-ws] framework allows creating new SOAP-based web services easily. These are meant to be contract-first services, where the service is created from a WSDL definition, taking advantage of the usual Spring features.

Among these the most important, the one being showcased by the project, is adding security with ease. Spring allows by default using the two most common [WS-Security][ws-security] implementations: [WSS4J][wss4j] and [XWSS][xwss].

---

## What is in this example?

The project just wants to show how to set up WS-Security for a Spring-based web service. And as a side effect it also shows how to set up a Spring-based web service and client.

For this several versions of the same basic endpoint are created by the project, and to each of them a different security protocols is added.

## Security

The various web services included in the project each offer a different security system:

- No security, for the base service.
- Username and password header, both plain and digested variants.
- Signed data.
- Encrypted data.

For each of them both the XWSS and WSS4J implementations have been used.

[spring-ws]: http://projects.spring.io/spring-ws/

[ws-security]: https://www.oasis-open.org/committees/wss/
[xwss]: https://docs.oracle.com/cd/E17802_01/webservices/webservices/docs/1.6/tutorial/doc/XWS-SecurityIntro4.html
[wss4j]: https://ws.apache.org/wss4j/
