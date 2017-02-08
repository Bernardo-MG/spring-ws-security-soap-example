# URLs

Once it is running the web service will be accessible from the following base URL: [http://localhost:8080/swss/](http://localhost:8080/swss/).

But this is just an empty URL leading nowhere. To actually use the web service one of the endpoints should be queried.

## Endpoint URLs

Each endpoint uses their own combination of security protocol and WSS implementation:

|Security|WSS Implementation|URL|
|:-:|:-:|:-:|
|None|None|[http://localhost:8080/swss/unsecure/entities](http://localhost:8080/swss/unsecure/entities)|
|Plain Password|XWSS|[http://localhost:8080/swss/password/plain/xwss/entities](http://localhost:8080/swss/password/plain/xwss/entities)|
|Plain Password|WSS4J|[http://localhost:8080/swss/password/plain/wss4j/entities](http://localhost:8080/swss/password/plain/wss4j/entities)|
|Digested Password|XWSS|[http://localhost:8080/swss/password/digest/xwss/entities](http://localhost:8080/swss/password/digest/xwss/entities)|
|Digested Password|WSS4J|[http://localhost:8080/swss/password/digest/wss4j/entities](http://localhost:8080/swss/password/digest/wss4j/entities)|
|Signature|XWSS|[http://localhost:8080/swss/signature/xwss/entities](http://localhost:8080/swss/signature/xwss/entities)|
|Signature|WSS4J|[http://localhost:8080/wss4j/signature/xwss/entities](http://localhost:8080/wss4j/signature/xwss/entities)|
|Encryption|XWSS|[http://localhost:8080/swss/encryption/xwss/entities](http://localhost:8080/swss/encryption/xwss/entities)|
|Encryption|WSS4J|[http://localhost:8080/wss4j/encryption/xwss/entities](http://localhost:8080/wss4j/encryption/xwss/entities)|

## WSDL

All these endpoints generate a WSDL defining their interface, this can be found by just adding the ".wsdl" suffix to the URL.

|Security|WSS Implementation|URL|
|:-:|:-:|:-:|
|None|None|[http://localhost:8080/swss/unsecure/entities.wsdl](http://localhost:8080/swss/unsecure/entities.wsdl)|
|Plain Password|XWSS|[http://localhost:8080/swss/password/plain/xwss/entities.wsdl](http://localhost:8080/swss/password/plain/xwss/entities.wsdl)|
|Plain Password|WSS4J|[http://localhost:8080/swss/password/plain/wss4j/entities.wsdl](http://localhost:8080/swss/password/plain/wss4j/entities.wsdl)|
|Digested Password|XWSS|[http://localhost:8080/swss/password/digest/xwss/entities.wsdl](http://localhost:8080/swss/password/digest/xwss/entities.wsdl)|
|Digested Password|WSS4J|[http://localhost:8080/swss/password/digest/wss4j/entities.wsdl](http://localhost:8080/swss/password/digest/wss4j/entities.wsdl)|
|Signature|XWSS|[http://localhost:8080/swss/signature/xwss/entities.wsdl](http://localhost:8080/swss/signature/xwss/entities.wsdl)|
|Signature|WSS4J|[http://localhost:8080/wss4j/signature/xwss/entities.wsdl](http://localhost:8080/wss4j/signature/xwss/entities.wsdl)|
|Encryption|XWSS|[http://localhost:8080/swss/encryption/xwss/entities.wsdl](http://localhost:8080/swss/encryption/xwss/entities.wsdl)|
|Encryption|WSS4J|[http://localhost:8080/wss4j/encryption/xwss/entities.wsdl](http://localhost:8080/wss4j/encryption/xwss/entities.wsdl)|
