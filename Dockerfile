# Usar una imagen base de OpenJDK 17 con Alpine para menor tamaño
FROM openjdk:17-jdk-alpine

# Establecer el directorio de trabajo
WORKDIR /app

# Copiar los archivos de Maven wrapper y pom.xml primero para aprovechar el cache de Docker
COPY mvnw .
COPY mvnw.cmd .
COPY .mvn .mvn
COPY pom.xml .

# Dar permisos de ejecución al wrapper de Maven
RUN chmod +x ./mvnw

# Descargar las dependencias (esto se cachea si no cambia el pom.xml)
RUN ./mvnw dependency:go-offline -B

# Copiar el código fuente
COPY src src

# Construir la aplicación
RUN ./mvnw clean package -DskipTests

# Exponer el puerto 8080
EXPOSE 8080

# Ejecutar la aplicación
CMD ["java", "-jar", "target/calc4-0.0.1-SNAPSHOT.jar"]
