# Java Template Service

This is a simple java service that contains a few implementations of the most common behaviours existent in our services.

## Motivation

The main objective of this project is to provide a template that contains the minimum requirements to spin up a java service with less effort. 

## Architecture 

Please, grab a coffee and read the chosen [architecture](ARCHITECTURE.MD) for this project.

## Functionalities and Technologies

- Rest endpoints (Vertx + OpenApi 3)
- Stream messages reading and writing (Kafka)
- Logs (Log4j / Slf4j)
- Metrics (Prometheus)
- Config (Typesafe)
- DI (Guice)
- Tests (Junit5 + Mockito)
- Test coverage (Jacoco)

## How to add or remove functionalities

### Dependencies
Take a look at the [dependencies](dependencies_versions.gradle) file and add or remove the necessary library.
Try to keep an organized list by the implementation/tests and alphabetical order.
Be sure that the new dependencies are being called on [build.gradle file](build.gradle).

### Creating Use Cases

1. Every UseCase needs an interface to be declared, with tha said, go to [input ports dir](src/main/java/com/viafoura/template/microservice/application/port/input) and create a new interface.
2. If the interface needs domain objects, please create them under [this structure](src/main/java/com/viafoura/template/microservice/domain)
3. Implement the UseCase under the [service package](src/main/java/com/viafoura/template/microservice/application/service).
4. Go to the [UseCase Module file](src/main/java/com/viafoura/template/microservice/infrastructure/guice/UseCaseModule.java) to point the implementation 
5. If the service requires external needs you might need to add output ports and use them.

### Adding rest endpoints

1. Change the [api spec file](src/main/resources/api-spec.yaml) according to your needs.
2. Go to the [api package](src/main/java/com/viafoura/template/microservice/infrastructure/vertx/api) and create the interface with the actions needed.
3. If you are adding an endpoint that requires requests or response objects you might need to create a new package under vertx/api dir.
4. Change the [EventBusType class](src/main/java/com/viafoura/template/microservice/infrastructure/vertx/event/EventBusType.java) to add the newer operations.
5. Go to the [verticle package](src/main/java/com/viafoura/template/microservice/infrastructure/vertx/verticle) and implement your verticle.
6. Don't forget to add your verticle as service connector implementation in the [guice module](src/main/java/com/viafoura/template/microservice/infrastructure/guice/ServiceConnectorModule.java)
7. The Api interface created at the 2nd step needs to be implemented inside the [adapter web dir](src/main/java/com/viafoura/template/microservice/adapter/input/web)
8. Obviously, the above adapter needs to call an [useCase class](#Creating Use Cases) and be tested.

### Kafka events 

The [reference.conf](src/main/resources/reference.conf) file covers lists of incoming and outgoing topics under 
the `kafka.topic.incoming` and `kafka.topic.outgoing` properties respectively.

#### Consumption

The events consumption are already being made by [EventTopologySupplier](src/main/java/com/viafoura/template/microservice/infrastructure/stream/EventTopologySupplier.java) and [EventsConsumerAdapter](src/main/java/com/viafoura/template/microservice/adapter/input/stream/EventsConsumerAdapter.java) classes.
Maybe you only need to change EventsConsumerAdapter logic to call different use cases.

#### Publishing

Publishing is also being done by [EventPublisher](src/main/java/com/viafoura/template/microservice/infrastructure/stream/EventPublisher.java) and [EventsConsumerAdapter](src/main/java/com/viafoura/template/microservice/adapter/output/stream/MessagePublisherAdapter.java) classes.
Change the output port and implementation according to your needs.

### Output Ports - External needs

Every external call must be implemented by creating its port and then the output adapter.

1. Create the port interface under the [output port dir](src/main/java/com/viafoura/template/microservice/application/port/output)
2. Once is an external need, the infrastructure under that must be provided in the [Infrastructure dir](src/main/java/com/viafoura/template/microservice/infrastructure).
3. Then implement the port interface under the [output adapter dir](src/main/java/com/viafoura/template/microservice/adapter/output).
4. Don't forget to tell guice about the implementation in the [AdapterModule file](src/main/java/com/viafoura/template/microservice/infrastructure/guice/AdapterModule.java).

### Config

Try to organize your configs into different interfaces. E.g. If you need a database config, follows:
1. Create an interface called `DatabaseConfig.java` under the [config infra dir](src/main/java/com/viafoura/template/microservice/infrastructure/config) and add the getters you need.
2. Then, change the [AppConfig.java](src/main/java/com/viafoura/template/microservice/infrastructure/config/AppConfig.java) file to implement the newer config interface and read the properties from the reference.conf string.
3. Finally, tell guice about the new implementation at the [ConfigModule class](src/main/java/com/viafoura/template/microservice/infrastructure/guice/ConfigModule.java).

### Metrics

Application metrics output adapter is already provided by [this class](src/main/java/com/viafoura/template/microservice/adapter/output/metrics/ServiceMetricsAdapter.java).
You only need to add the custom names into [MetricNames class](src/main/java/com/viafoura/template/microservice/infrastructure/metric/MetricNames.java) and add the functions inside [the port](src/main/java/com/viafoura/template/microservice/application/port/output/metric/ApplicationMetricsPort.java).

Finally, you'll be able to use the port and its functions in all services you need. 

### Removing functionalities

Feel free to change your project by removing unnecessary code from this structure. Try to check the following layers:

- Config
- Guice
- Metrics
- Adapters (input or output)

Then remove the unused libraries and compile the project.

## What else can we add here

Some improvements/changes are welcome in this project such as:

- Replacing infrastructure and adapters to external modules
- New naming conventions
- New adapters implementations (Database/jooq)
- Security layer for vertx
- Integration tests

Just keep in mind that this is a template service, we must try to keep the minimal and most used functionalities to serve as a quick startup way for a new java service.

hello
