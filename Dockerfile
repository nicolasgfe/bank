FROM tomcat:latest

WORKDIR /usr/local/tomcat

COPY target/*.war ./webapps

EXPOSE 8080

