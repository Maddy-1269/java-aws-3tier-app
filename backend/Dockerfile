FROM tomcat:10.1-jdk17-temurin

RUN rm -rf /usr/local/tomcat/webapps/*

COPY target/backend-1.0.war /usr/local/tomcat/webapps/ROOT.war
COPY wait-for-mysql.sh /wait-for-mysql.sh

RUN apt-get update && apt-get install -y netcat-openbsd

EXPOSE 8080

CMD ["/wait-for-mysql.sh"]

