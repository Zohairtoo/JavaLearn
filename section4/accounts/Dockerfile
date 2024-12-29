# We need java 17 for the account microservice
#Start with the base image, it can also be found out on docker desktop
FROM openjdk:17-jdk-slim

MAINTAINER zohairtoo

# copy the jar folder to docker container
COPY target/accounts-0.0.1-SNAPSHOT.jar accounts-0.0.1-SNAPSHOT.jar

# this is actually the command to run the jar; this is the entry point command 'java -jar /accounts-0.0.1-SNAPSHOT.jar'
# execution command
ENTRYPOINT ["java","-jar","/accounts-0.0.1-SNAPSHOT.jar"]

