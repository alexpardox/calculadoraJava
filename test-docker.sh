# Script para probar el Dockerfile localmente
echo "Construyendo la imagen Docker..."
docker build -t calculadora-java .

echo "Ejecutando el contenedor..."
docker run -p 8080:8080 calculadora-java
