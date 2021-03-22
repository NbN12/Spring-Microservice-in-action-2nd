FROM openjdk:11.0.10-jre-slim as builder

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} app.jar 

RUN java -Djarmode=layertools -jar app.jar extract

FROM openjdk:11.0.10-jre-slim

COPY --from=builder /dependencies/ ./
COPY --from=builder /snapshot-dependencies/ ./
COPY --from=builder /spring-boot-loader/ ./
COPY --from=builder /application/ ./

ADD start.sh /start.sh
CMD [ "/start.sh" ]
