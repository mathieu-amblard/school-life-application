# School Life Application

## Springdoc

http://localhost:8080/swagger-ui.html

## Postgres

### Start the container in the background

```shell
docker compose up --detach
```

### Error : PostgreSQL Database directory appears to contain a database; Skipping initialization

```shell
docker compose down --volumes
```

### Drop database

```shell
psql -U user postgres -h localhost
DROP DATABASE user_management_database;
\l # to list all databases
```