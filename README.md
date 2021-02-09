# Spring microservice in action 2nd (Project repo)

---

## Table of contents

- [Spring microservice in action 2nd (Project repo)](#spring-microservice-in-action-2nd-project-repo)
  - [Table of contents](#table-of-contents)
  - [General info](#general-info)
    - [Prerequisites](#prerequisites)
    - [Installation](#installation)
  - [Setup](#setup)
  - [Tips](#tips)
  - [Vscode extensions for dev](#vscode-extensions-for-dev)

## General info

This project from book *[Spring microservice in Action (2nd)](https://www.manning.com/books/spring-microservices-in-action-second-edition)*
with 2 different spring boot versions.

### Prerequisites

* Java 11 or above
* Postgres version 9.0 above
* Kafka
* Redis
* Docker
* Maven (optional if .mvn folder not presented)

### Installation

For more infomation aboud how to install enviroment see link below
1. Locally(How to install)
   * **JDK**
     * [Windows](https://www.youtube.com/watch?v=IJ-PJbvJBGs)
     * [Ubuntu](https://stackoverflow.com/questions/52504825/how-to-install-jdk-11-under-ubuntu)
   * **Postgres**
     * [Windows](https://www.youtube.com/watch?v=e1MwsT5FJRQ)
     * [Ubuntu](https://www.postgresql.org/download/linux/ubuntu/)
   * **Docker**
     * [Windows](https://www.youtube.com/watch?v=_9AWYlt86B8) 
     * [Ubuntu](https://docs.docker.com/engine/install/ubuntu/)
   * **Kafka**
     * [Windows](https://www.goavega.com/install-apache-kafka-on-windows/)
     * [Ubuntu](https://hevodata.com/blog/how-to-install-kafka-on-ubuntu/)
   * **Redis**
     * [Windows](https://stackoverflow.com/questions/6476945/how-do-i-run-redis-on-windows)
     * [Ubuntu](https://redis.io/topics/quickstart)
2. Docker(How to install)
   * **Redis**
      ```bash
      docker run --name redis -p 6379:6379 -d redis
      ```

## Setup
1. Local
   1. Clone the repo

   ```bash
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

     ```bash
     ./mvnw clean spring-boot:run -DskipTests
     ```

     * powershell or bat
  
     ```powershell
     .\mvnw clean spring-boot:run -DskipTests
     ```

   8. Import postman collection.

   9. Enjoy yourself.
   
2. Docker (comming soon)

## Tips
* When you are tesing postman for non-authentication route simply change these line in SecurityConfig.java
   ```java
   http.csrf().disable().authorizeRequests().anyRequest().authenticated();
   ```
  change to
  ```java
   http.csrf().disable().authorizeRequests().anyRequest().permitAll();
  ```
* If you get **403 Access denied** caused by Organization service. Comment this line in SecurityConfig.java
  ```java
  @EnableGlobalMethodSecurity(jsr250Enabled = true)
  ```
* To turn off Eureka Client of service. Add these lines to bootstrap.yml
  ```yml
  eureka:
    client:
      enabled: false
  ```
* To get access_token without explicitly copy and paste. Simply do this following steps.
  1. Create environment in right corner (eye icon)
  2. In enviroment section. Click Add
  3. Type your environment name
  4. In variable tab type "access_token"
  5. Test with **Keycloak server: Get token** in Collection.

## Vscode extensions for dev
  1. gabrielbb.vscode-lombok
  2. vscjava.vscode-java-debug
  3. vscjava.vscode-java-test
  4. redhat.java
  5. vscjava.vscode-maven
  6. pivotal.vscode-spring-boot
  7. vscjava.vscode-spring-initializr
  8. redhat.vscode-xml
