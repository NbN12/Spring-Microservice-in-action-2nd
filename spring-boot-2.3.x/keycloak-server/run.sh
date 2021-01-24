#!/bin/bash
 
# https://stackoverflow.com/a/246128/1098564
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
 
# https://stackoverflow.com/a/47181236/1098564
wget -c https://github.com/keycloak/keycloak/releases/download/12.0.1/keycloak-12.0.1.zip
 
# #https://askubuntu.com/a/994965/109301
unzip -n keycloak-12.0.1.zip
./keycloak-12.0.1/bin/add-user-keycloak.sh -r master -u admin -p admin
./keycloak-12.0.1/bin/standalone.sh -Djboss.socket.binding.port-offset=10 -Dkeycloak.migration.action=import -Dkeycloak.migration.provider=singleFile -Dkeycloak.migration.file=$DIR/ostock-realm-config.json -Dkeycloak.migration.strategy=OVERWRITE_EXISTING
