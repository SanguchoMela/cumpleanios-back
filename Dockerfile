# Etapa 1: Construir la aplicación usando Maven
FROM maven:3.8.5-openjdk-17 AS builder

# Configurar el directorio de trabajo
WORKDIR /app

# Copiar el archivo pom.xml y descargar las dependencias
COPY pom.xml .

# Descargar las dependencias necesarias
RUN mvn dependency:go-offline

# Copiar el código fuente
COPY src /app/src

# Compilar y empaquetar la aplicación (sin ejecutar tests)
RUN mvn clean package -DskipTests

# Etapa 2: Crear la imagen final usando OpenJDK
FROM openjdk:17-jdk-alpine

# Configurar el directorio de trabajo
WORKDIR /app

# Copiar el JAR generado desde la etapa de construcción
COPY --from=builder /app/target/cumpleanios-back-0.0.1-SNAPSHOT.jar app.jar

# Exponer el puerto en el que se ejecuta la aplicación (por defecto es 8080)
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
