FROM tomcat:9.0.74-jre8-temurin-jammy
ENV CATALINA_HOME=/usr/local/tomcat
COPY target/hotel-rest-api-0.0.1-SNAPSHOT.war $CATALINA_HOME/webapps/hotel-rest.war
