#!/usr/bin/env sh

java -Djava.security.egd=file:/dev/./urandom \
     -Dspring.datasource.url=jdbc:mariadb://geodb:3306/geo \
     -Dserver.port=8080 \
     -Xmx1g \
     -Dgeo.backup.dir=/var/log/geo \
     -jar -server app.jar