FROM eclipse-temurin:17-jdk-alpine

# Mejora de rendimiento al iniciar Spring Boot
ENV JAVA_OPTS="-Djava.security.egd=file:/dev/./urandom"

# Crear y usar el directorio de trabajo
WORKDIR /app

# Copiar el jar al contenedor
COPY target/api-gateway-0.0.1-SNAPSHOT.jar app.jar

# Exponer el puerto del gateway
EXPOSE 8085

# Comando de arranque
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
