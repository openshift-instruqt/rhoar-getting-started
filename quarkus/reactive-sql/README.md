# quarkus-reactive-pg-client-example
An example on using the reactive pg client with Quarkus

If running this locally, use the following command with podman

``` 
podman run -d -e POSTGRESQL_USER=sa -e POSTGRESQL_PASSWORD=sa -e POSTGRESQL_DATABASE=quarkus_test -p 5432:5432 registry.access.redhat.com/rhscl/postgresql-96-rhel7
```

Or with Docker

```
docker run --ulimit memlock=-1:-1 -it --rm=true --memory-swappiness=0 --name quarkus_test -e POSTGRES_USER=quarkus_test -e POSTGRES_PASSWORD=quarkus_test -e POSTGRES_DB=quarkus_test -p 5432:5432 postgres:10.5
```

Once the application starts, it will create the database tables itself. 
The repo is inspired by the guide at 
https://quarkus.io/guides/reactive-sql-clients#!