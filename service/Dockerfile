FROM maven:latest
LABEL authors="gnegDev"

COPY splitast/pom.xml .
COPY splitast/src ./src

RUN mvn clean package -DskipTests

ENTRYPOINT ["java","-jar","./target/splitast-0.0.1-SNAPSHOT.jar"]
