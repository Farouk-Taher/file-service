## Steps to Setup

**1. Clone the repository** 

**2. Configure MySQL database**

Create a MySQL database named `file_demo`, and change the username and password in `src/main/resources/application.properties` as per your MySQL
installation -

```properties
spring.datasource.username= <YOUR MYSQL USERNAME>
spring.datasource.password= <YOUR MYSQL PASSWORD>
```

**3. Run the app using maven**

```bash
cd spring-boot-file-service-rest-api-mysql-database-storage
mvn spring-boot:run
```

That's it! The application can be accessed at `http://localhost:8080`.
