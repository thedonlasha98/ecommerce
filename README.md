# ecommerce
## Dependency
– If you want to use Oracle:
```xml
<dependencies>
        <dependency>
			<groupId>com.oracle</groupId>
			<artifactId>orai18n</artifactId>
			<version>11.2.0.4</version>
        </dependency>
		<dependency>
			<groupId>com.oracle</groupId>
			<artifactId>ojdbc6</artifactId>
			<version>11.2.0.4</version>
		</dependency>
		<dependency>
			<groupId>com.oracle.database.jdbc</groupId>
			<artifactId>ucp</artifactId>
			<version>11.2.0.4</version>
		</dependency>
</dependencies>
```

```
## Configure Spring Datasource, JPA, App properties
Open `src/main/resources/application.properties`
- For Oracle:
```
server.port=8000

#spring.datasource.url=jdbc:oracle:thin:@//localhost:1521/XE
#spring.datasource.username=lashabolga
#spring.datasource.password=lasha111
#spring.datasource.driver-class-name=oracle.jdbc.OracleDriver

spring.datasource.url=jdbc:oracle:thin:@//localhost:1521/XE
spring.datasource.username=test123
spring.datasource.password=test123
spring.datasource.driver-class-name=oracle.jdbc.OracleDriver

# JPA settings
spring.jpa.show-sql=false
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.user_sql_comments=true

# HikariCP settings
spring.datasource.hikari.minimumIdle=5
spring.datasource.hikari.maximumPoolSize=20
spring.datasource.hikari.idleTimeout=30000
spring.datasource.hikari.maxLifetime=2000000
spring.datasource.hikari.connectionTimeout=30000
spring.datasource.hikari.poolName=HikariPoolBooks

# JPA settings
spring.jpa.database-platform=org.hibernate.dialect.Oracle12cDialect
#spring.jpa.hibernate.use-new-id-generator-mappings=false
spring.jpa.hibernate.ddl-auto=create

spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=thedonlasha@gmail.com 
spring.mail.password=lasha111
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true

# App Properties
bezkoder.app.jwtSecret= dedededef
bezkoder.app.jwtExpirationMs= 1800000

hash.generator.alphabet=0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz
hash.generator.length= 100

```
## Build Maven Project
```
mvn clean install
mvn package
```
## Run Spring Boot application
```
mvn spring-boot:run
```

## Run following SQL insert statements
```
insert into roles values(1,'ROLE_USER');
insert into roles values(2,'ROLE_MODERATOR');
insert into roles values(3,'ROLE_ADMIN');
```

