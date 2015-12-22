# Spring Web Services

The [Spring-WS framework][spring-ws] allows creating new SOAP-based web services easily. These are meant to be contract-first services, meaning the service is created from a WSDL definition, and easy to build with Maven, as this example does.

---

## What is the reach of this example?

The project just wants to show how to set up WS-Security for a Spring-based web service. As a side effect it also shows how to set up such a service, but that's not the aim.

If it is to be run, several versions of the same basic web service will be created, starting from a basic unsecured service, and then adding to it different security protocols in each version.

## The various versions of the web service

The various web services included in the project each offer a different security system:

- No security, for the base service.
- User with password headers, digested and with nonce code.

[spring-ws]: http://projects.spring.io/spring-ws/