# ejercicioBCI

Prerequisites
- JDK 8+

To start execution of this demo, from command line run:
```sh
$ cd demoBci
$ ./gradlew build
$ ./gradlew bootRun
```

Application starts at port 8080 by default and creates (or opens) an H2 database file in `demoBci/data` folder

The demoBci app exposes 2 endpoints at:
- `HTTP POST` http://localhost:8080/api/sign-up for user sign up
- `HTTP GET` http://localhost:8080/api/login for user login and retrieving of user data

A postman collection is available in `postman` folder to test this demo
