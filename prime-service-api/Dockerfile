FROM adoptopenjdk/openjdk14:x86_64-tumbleweed-jre-14.0.2_12
EXPOSE 8080
ARG JAR_FILE=target/prime-service-api-1.0.jar
ADD ${JAR_FILE} prime-number-service-api-1.0.jar
ENTRYPOINT ["java","-jar","/prime-number-service-api-1.0.jar"]
