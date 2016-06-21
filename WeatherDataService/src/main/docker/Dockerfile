FROM frolvlad/alpine-oraclejdk8:slim
MAINTAINER ASE-Energy
VOLUME /tmp
EXPOSE 8091
ADD weatherdataservice-0.0.1-SNAPSHOT.jar app.jar
RUN sh -c 'touch /app.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
