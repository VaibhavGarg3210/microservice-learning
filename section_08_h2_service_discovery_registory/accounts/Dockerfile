#Dockerfile name is mandatory to run docker

#1st step to install java {image name}:{tag}

FROM openjdk:17-jdk-slim 

# In latest version we use LABEL 

MAINTAINER vaibhav.com

#Copy jar file to docker image
COPY target/accounts-0.0.1-SNAPSHOT.jar accounts-0.0.1-SNAPSHOT.jar

# When someone try to generate docker container  of my docker image try that cmd

ENTRYPOINT ["java","-jar","accounts-0.0.1-SNAPSHOT.jar"]

# to build docker build cmd - docker build . -t 
# to run docker container - docker run -p 8080:8080 learn/accounts:s4
# -p - Port outside expose: inside expose | learn/accounts:s4
# we can start in detach mode - docker run -d -p 8080:8080 vaibhavg3210/accounts:s4
# check running container docker ps
 