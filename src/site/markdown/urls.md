# Endpoint URLs

Each of the different endpoints is mapped to their own URL, based on the base default URL, which is as follows:

```
http://localhost:8080/swss
```

The following endpoints are mapped each to their own URL:

|Authentication method|WSS Implementation|URL|
|:-:|:-:|:-:|
|None|None|[http://localhost:8080/swss/unsecure/entities]|
|Plain Password|XWSS|[http://localhost:8080/swss/password/plain/xwss/entities]|
|Plain Password|WSS4J|[http://localhost:8080/swss/password/plain/wss4j/entities]|
|Digested Password|XWSS|[http://localhost:8080/swss/password/digest/xwss/entities]|
|Digested Password|WSS4J|[http://localhost:8080/swss/password/digest/wss4j/entities]|
|Signature|XWSS|[http://localhost:8080/swss/signature/xwss/entities]|
|Signature|WSS4J|[http://localhost:8080/wss4j/signature/xwss/entities]|
|Encryption|XWSS|[http://localhost:8080/swss/encryption/xwss/entities]|
|Encryption|WSS4J|[http://localhost:8080/wss4j/encryption/xwss/entities]|
