#!/bin/sh

echo "Waiting for MySQL..."

until nc -z mysql-db 3306; do
  echo "MySQL not ready, waiting..."
  sleep 2
done

echo "MySQL is up - starting Tomcat"
exec catalina.sh run

