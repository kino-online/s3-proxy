FROM eclipse-temurin:17-jre-alpine
ENV TZ=Europe/Moscow
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

ENV spring_profiles_active=production

ADD target/app.jar .

ENTRYPOINT ["java", "-jar", "app.jar"]