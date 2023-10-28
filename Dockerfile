FROM tomcat:latest

WORKDIR /usr/local/tomcat

COPY target/*.war ./webapps

#COPY src/main/resources/*.xml ./conf

EXPOSE 8080


#copiar arquivo do .conf alterar não sei o que

#CMD ["cd", "webapps"]
#
#CMD ["catalina.sh", "run"]