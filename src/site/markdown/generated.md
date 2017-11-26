# Generated code

Part of the code is generated with JAXB through the [Maven JAXB2 plugin][maven-jaxb2]. The binding files are inside the *src\main\resources\xjb* folder, while the *src\main\resources\xsd* folder contains the files used for generating the code.

This code is kept inside the Maven target folder. While this means they are not persisted, and taht the latest generated files are always used, it also means that in some cases the files may be missing. If that happens clear and rebuild the project through Maven, it will take care of generating the code and adding it to the classpath.

The XSD file used for generating the code is the same one used for generating the WSDL, and the generated classes are an integral part of the [ExampleEntityEndpoint][entity-endpoint]. Thanks to this the endpoint API will always match its WSDL.

[entity-endpoint]: ./apidocs/com/bernardomg/example/swss/endpoint/ExampleEntityEndpoint.html

[maven-jaxb2]: https://java.net/projects/maven-jaxb2-plugin
