# Spring-microservice-in-action

---

## Table of contents

* [General info](#general-info)
  * [Prerequisites](#prerequisites)
  * [Installation](#installation)
* [Technologies](#technologies)
* [Setup](#setup)

## General info

This project from book *[Spring microservice in Action (2nd)](https://www.manning.com/books/spring-microservices-in-action-second-edition)*
with 2 different spring boot versions.

### Prerequisites

* Java 11
* Postgres version 9.0 above
* Docker (optional)
* Maven (optional if .mvn folder not presented)

### Installation

For more infomation aboud how to install enviroment see link below

* [Install JDK for windows 10](https://www.youtube.com/watch?v=IJ-PJbvJBGs)
* [Postgres](https://www.youtube.com/watch?v=e1MwsT5FJRQ)
* [Docker](https://www.youtube.com/watch?v=_9AWYlt86B8) (Optional)

## Setup

1. Clone the repo

```sh
git clone https://github.com/NbN12/Spring-microservice-in-action.git
```

2. Create db using psql command 
 ```bash 
  psql -U yourusername 
 ```
3. Enter your password
4. Then run command 
```sql 
CREATE DATABASE ostock; 
```
5. Exit psql cli

6. Run _**configuration server**_ first then _**eureka server**_. Others can run unordered.

7. To run project without package them and skip test use:

  * bash

  ```sh
  ./mvnw clean spring-boot:run -DskipTests
  ```

  * powershell or bat
  
  ```powershell
  .\mvnw clean spring-boot:run -DskipTests
  ```

8. Import postman collection.

9. Enjoy yourself.
