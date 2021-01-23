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
* Postgres
* Docker (optional)
### Installation
For more infomation aboud how to install Java see link below
* [Install JDK for windows 10](https://www.youtube.com/watch?v=IJ-PJbvJBGs)
* [Postgres](https://www.youtube.com/watch?v=e1MwsT5FJRQ)
* [Docker](https://www.youtube.com/watch?v=_9AWYlt86B8) (Optional)

## Setup
1. Clone the repo
```sh
git clone https://github.com/NbN12/Spring-microservice-in-action.git
```
2. Create database and datas from db files folder.
3. Run _**configuration server**_ first then _**eureka server**_. Others can run unordered.
4. To run project without package use:
  * bash
  ```sh
  ./mvnw clean spring-boot:run -DskipTests
  ```
  * powershell or bat
  ```powershell
  .\mvnw clean spring-boot:run -DskipTests
  ```
5. Import postman collection.
6. Enjoy yourself.
