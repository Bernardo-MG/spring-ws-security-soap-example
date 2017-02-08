# Generated code

JAXB2 is used to generate part of the code. This is handled through the [Maven JAXB2 plugin][maven-jaxb2] and the files contained inside *src\main\resources\xjb* and *src\main\resources\xsd*.

These files will be kept inside the target folder. While this means they are not persisted, and the latest generated files are always used, it also means that in some cases the files may be missing. If that happens clear and rebuild the project through Maven, it will take care of generating the code and adding it to the classpath.

The XSD file is the same one used for generating the WSDL, and the generated classes are an integral part of the [ExampleEntityEndpoint][entity-endpoint]. Thanks to the endpoint API will always match its WSDL.

[entity-endpoint]: ./apidocs/com/wandrell/example/swss/endpoint/ExampleEntityEndpoint.html

[maven-jaxb2]: https://java.net/projects/maven-jaxb2-plugin
