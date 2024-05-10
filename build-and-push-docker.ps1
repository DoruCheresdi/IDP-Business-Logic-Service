./mvnw package -DskipTests
docker build -t dorucheresdi/idp-business-service .
docker push dorucheresdi/idp-business-service
