# Configuration info

While the architecture tries to be very simple there are some things which require a bit of detailing.

## Persistence

The persistence layer is created with the help of [Spring][spring], [Hibernate][hibernate] and an in-memory [H2][h2] database, and the context file for setting this up is in the *context/persistence.xml* file.

A pair of scripts for creating and populating tables are contained inside the *db* folder.

[h2]: http://www.h2database.com/
[hibernate]: http://hibernate.org/
[spring]: https://spring.io/
