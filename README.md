
# Sample REST API with Spring Boot  

## Steps to Setup

**Build and run the app using maven**

```bash
mvn clean install
java -jar target/contactDemo-0.0.1-SNAPSHOT.jar

```

Alternatively, you can run the app without packaging it using -

```bash
mvn spring-boot:run
```

The app will start running at <http://localhost:8080>.

## Explore Rest APIs

The app defines following REST APIs.

    GET /api/v1/contact
    
    GET /api/v1/contact/{contactId}

    POST /api/v1/contact
    
    PUT /api/v1/contact/{contactId}
    
    DELETE /api/v1/contact/{contactId}


## Explore H2 database

The app will use H2 running at <http://localhost:8080/h2>.


## test data script
```bash
sh ./src/main/resource/script/curlData.sh
