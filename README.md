# School Life Application

## postgres

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