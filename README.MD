# Application title

## Ports and adapter

### Application / Core

This layer is the core of the application itself. It consists in receiving the inputs, processing them as Domains, 
and sending the proper outputs.

#### Port
- **In**: Use cases interfaces.
- **Out**: Outbound interfaces that declare external needs.

#### Service

This layer implements the "**IN**"  use cases. 
Services also may use the out ports to send or get data that use cases ask for.

### Adapter
The outside part of hexagonal architecture are called Adapters which implement incoming data and outgoing data ports.

- **In**: Input data adapters implementation such as http endpoints or stream data consumers. As input adapters,
  their responsibility is to use the provided infrastructure to call the application input ports (`application/in`).
- **Out**: Outputs who implement outbound data handling or provide data from outside. It can be database connectors,
  http calls to external systems, external configurations, and outgoing stream providers. They must implement
  `application/out` ports that are called by `application/service` implementations


### Domain

In a domain rich with business rules, domain objects are the lifeblood of an application. Domain objects can contain 
both state and behavior. 
The closer the behavior is to the state, the easier the code will be to understand, reason about, and maintain.
Domain objects don’t have any outward dependency. They’re pure Java and provide an API for use cases to operate on them. 

### Infra

All code necessary to run the application, such as libraries configuration and dependency code for Adapters.


## Reference

- [Ports and Adapter](https://reflectoring.io/spring-hexagonal/)