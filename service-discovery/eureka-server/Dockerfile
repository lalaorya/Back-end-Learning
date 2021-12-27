FROM registry.cn-shenzhen.aliyuncs.com/pcauto-public/openjdk:11-jre
LABEL maintainer=bifrost-eureka-server
ARG JAR_EXPOSE=80
EXPOSE $JAR_EXPOSE
COPY target/bifrost-eureka-server.jar /app.jar
ENTRYPOINT ["java", "-jar", "-Duser.timezone=GMT+08", "/app.jar", "--spring.config.location=/config/application.yml"]
