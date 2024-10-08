# School Life Application

## Postgres

### Start the container in the background

```shell
docker compose up --detach
```

### Error : PostgreSQL Database directory appears to contain a database; Skipping initialization

```shell
docker compose down --volumes
```

## user-management-service

### Swagger

http://127.0.0.1:8080/user/swagger-ui/index.html

#### Database connection

```shell
# directly from bash (no need to connect to the container)
psql -U user -h localhost user_management_database
\d # to list tables
select * from users; # to select all users
```

#### Create/Drop database

```shell
# directly from bash (no need to connect to the container)
psql -U user postgres -h localhost
CREATE DATABASE user_management_database;
DROP DATABASE user_management_database;
\l # to list all databases
```

## student-service

### Swagger

http://127.0.0.1:8080/student/swagger-ui/index.html

#### Database connection

```shell
# directly from bash (no need to connect to the container)
psql -U user -h localhost student_database
\d # to list tables
select * from students; # to select all students
```

#### Create/Drop database

```shell
# directly from bash (no need to connect to the container)
psql -U user postgres -h localhost
CREATE DATABASE student_database;
DROP DATABASE student_database;
\l # to list all databases
```