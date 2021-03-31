# Maven modules
This application uses maven modules and the core benchmark module is located in `benchmark-app` folder
# Application properties
DB connection properties are segregated by spring profiles:
- crdb - Cockroach DB (uses default PGSQL jdbc driver)
- ybdb - Yugabyte DB (uses custom, cluster-aware jdbc driver)
- pgsql - PosgtreSQL DB (uses default PGSQL jdbc driver)

Update `./benchmark-app/src/main/resources/application.properties` to configure spring-boot benchmark-app application.
## Important properties
### `benchmark.min-threads`
Minimum number of process engine threads threads
### `benchmark.max-threads`
Maximum number of process engine threads threads
### `benchmark.iterations`
Number of iterations to execute every business process
### `benchmark.processes` 
Which processes to run the benchmark for (if not set then it is all)
Available business processes:
- allSequentialServiceTasks
- manyVariables
- parallelSubprocesses
- startToEnd
- terminateUserTasks
### `flowable.database-schema-update`
Controls how the DB schema is treated during the benchmark tests.
Possible values are:
 - `drop-create` - recreates all objects in the DB schema
 - `true` - runs liquibase schema upgrades
 - `none` - no schema updates

# Build jar

Run `./mvnw clean package -DskipTests`

A jar _benchmark-app-0.0.1-SNAPSHOT.jar_ with all dependencies will be created in the `./benchmark-app/target` folder.

# Run jar
```
java -Xms1024m -Xmx20148m -Dspring.profiles.active=<SPRING_PROFILE> -jar ./benchmark-app/target/benchmark-app-0.0.1-SNAPSHOT.jar
```

**Note:** if `-Dspring.profiles.active` is not specified, then default spring datasource properties must be provided in `benchmark-app/src/main/resources/application.properties`