FROM glassfish as build
WORKDIR /app
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
RUN ./mvnw dependency:go-offline -B
COPY src src
RUN ./mvnw package -DskipTests


FROM glassfish
WORKDIR /app

COPY --from=build app/target/*.jar ./

EXPOSE 8080
CMD [ "java","-jar", "SOA2-0.0.1-SNAPSHOT.jar" ]