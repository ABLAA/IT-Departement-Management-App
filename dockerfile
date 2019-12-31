FROM glassfish

WORKDIR /app/
COPY . .
RUN ./mvnw dependency:go-offline -B
RUN bash mvnw package -DskipTests
EXPOSE 8080
CMD [ "java","-jar", "target/SOA2-0.0.1-SNAPSHOT.jar" ]