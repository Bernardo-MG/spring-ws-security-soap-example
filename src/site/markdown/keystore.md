# Key stores

Key stores are used for encryption and signature based authentication, and are included as part of the project.

If for some reason they need to be created anew use the [Keystore Generator][keystore-generator] project.

## Included key stores

There are three key stores used by the project:

|Key store name|Key store format|Use|
|:-:|:-:|:-:|
|keystore.jks|Java Key Store|Certificate-based security|
|keystore2.jks|Java Key Store|Used to collide with the main key store for invalid authentication tests|
|symmetric.jceks|Java Cryptographic Extension Key Store|Private-key-based security|

[keystore-generator]: https://github.com/Bernardo-MG/keystore-generator
