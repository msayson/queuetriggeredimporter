## Introduction
This project is a demo for a queue-based record importer following Clean Architecture principles.

### Scenario

We want to build a Records Importer program that will retrieve a "pending records" message from a queue that provides information on where to import data from.  The Records Importer will use this information to retrieve the records from the data source, process them, and delete the message from the queue after successful processing.

To allow us to make future infrastructure changes with minimal effort, we will decouple our internal entities and use cases from infrastructure decisions.

### Brief summary of Clean Architecture

* Decouple layers: domain logic, use cases, interface adapters, and frameworks and infrastructure.
  * This allows us to easily test each layer and replace elements with minimal effort.  For example, we could switch UI frameworks or migrate from SQL to NoSQL without needing to change any code in the domain logic or use cases layers.
* No objects in inner layers of the Clean Architecture diagram should know about any outer layers.
  * Entities in the domain logic layer should know nothing about application-specific use cases, frameworks, or infrastructure choices.
  * Use cases will reference entities, but should know nothing about frameworks or infrastructure.
  * Interface adapters (controllers, gateways, presenters) will reference use cases, but should know nothing about infrastructure (device hardware, database, UI framework, external interfaces).
* Use Dependency Inversion for crossing boundaries from inner layers to outer layers, using interfaces that abstract out details that outer layers implement.
  * For example, a use case class, eg. `SearchUsers`, should reference a data access interface, eg. `interface UsersRepository`, without having any dependency on how the database layer is implemented.  This allows us to implement the database using a MySQLUsersRepository, DynamoDBUsersRepository, or anything else, without impacting any inner layer code.  We can then make infrastructure decisions later or migrate from one option to another with much less effort, reducing migration effort from potentially months for complex projects to days.

### Helpful commands

* `gradle build` - evaluate checkstyle, run unit tests, and assemble project jars
* `gradle check` - evaluate code against checkstyle rules
* `gradle test` - run unit tests
