FROM openjdk:22 as build
LABEL authors="guilherme.ayusso"

WORKDIR /app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

RUN chmod +x ./mvnw
# Faça o download das dependencias do pom.xml
RUN ./mvnw dependency:go-offline -B

COPY src src

RUN ./mvnw package -DskipTests
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

# Definição de produção para a imagem do Spring boot
FROM amazoncorretto:22-alpine-jdk
MAINTAINER baeldung.com
ARG DEPENDENCY=/app/target/dependency

# Copiar as dependencias para o build artifact
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app

# Definir variável de ambiente para ativar o perfil de produção
ENV SPRING_PROFILES_ACTIVE=prd

# Rodar a aplicação Spring boot
ENTRYPOINT ["java", "-cp", "app:app/lib/*","br.com.fiap.paymentapi.PaymentApiApplication"]