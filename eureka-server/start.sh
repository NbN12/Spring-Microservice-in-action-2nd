#!/bin/bash
apt-get update
apt-get install -y netcat
while ! nc -z configserver 8071; do sleep 3; done
java org.springframework.boot.loader.JarLauncher