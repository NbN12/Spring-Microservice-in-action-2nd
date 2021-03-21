#!/bin/bash
./mvnw clean package -DskipTests
docker build -t eureka-server:latest .