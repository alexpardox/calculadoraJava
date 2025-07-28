# Usar una imagen base de OpenJDK 17 con Alpine para menor tamaño
FROM openjdk:17-jdk-alpine

# Instalar curl para descargar Maven si es necesario
RUN apk add --no-cache curl

# Establecer el directorio de trabajo
WORKDIR /app

# Copiar los archivos de Maven wrapper y pom.xml primero para aprovechar el cache de Docker
COPY mvnw .
COPY mvnw.cmd .
COPY .mvn/ .mvn/
COPY pom.xml .

# Dar permisos de ejecución al wrapper de Maven
RUN chmod +x ./mvnw

# Verificar que el archivo de propiedades existe y descargar las dependencias
RUN ls -la .mvn/wrapper/ && \
    ./mvnw dependency:go-offline -B

# Copiar el código fuente
COPY src src

# Construir la aplicación
RUN ./mvnw clean package -DskipTests

# Exponer el puerto 8080
EXPOSE 8080

# Ejecutar la aplicación
CMD ["java", "-jar", "target/calc4-0.0.1-SNAPSHOT.jar"]
