FROM openjdk
WORKDIR usr/lib
ADD ./target/authenticatedemo-0.0.1-SNAPSHOT.jar  /usr/lib/authenticatedemo-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","authenticatedemo-0.0.1-SNAPSHOT.jar"]