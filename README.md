
# Electricity Management System
 
The Electricity Management System is a simple CRUD based spring boot application designed to manage operations related to customers, suppliers, meters, and pricing within an electricity distribution network. This system provides functionalities for creating, updating, and deleting customers, as well as calculating and managing their electricity bills based on meter readings and pricing information.
 
## Features
 
### Customer Management
- **Create Customer**: Allows users to create new customers with relevant information such as name, address, connection date, meter readings, and supplier details.
- **Update Customer**: Provides functionality to update existing customer information, including meter readings and supplier details.
- **Delete Customer**: Enables users to delete customers from the system.
 
### Meter Management
- **Create Meter**: Allows users to add new meters to the system, specifying meter ID and minimum bill amount.
- **Update Meter**: Provides functionality to update existing meter information, such as the minimum bill amount.
- **Delete Meter**: Enables users to remove meters from the system.
 
### Supplier Management
- **Create Supplier**: Allows users to add new electricity suppliers to the system, specifying supplier name and type.
- **Update Supplier**: Provides functionality to update existing supplier information, such as the supplier type.
- **Delete Supplier**: Enables users to remove suppliers from the system.
 
### Billing Calculation
- **Calculate Bill**: Automatically calculates the electricity bill for each customer based on their meter readings, pricing per unit, and minimum bill amount.
 
## Technologies Used
 
- **Java**: Programming language used for backend development.
- **Spring Boot**: Framework for building Java-based enterprise applications.
- **Hibernate**: Object-relational mapping tool for the Java programming language.
- **ModelMapper**: Java library for mapping Java beans properties.
- **JUnit 5**: Testing framework for Java applications.
- **Mockito**: Mocking framework for unit tests.
- **MySQL**: Relational database management system for data storage.
- **Maven**: Dependency management tool for Java projects.
- **Flyway**: Database migration tool for managing schema changes.
- **SpotBugs**: Static analysis tool for finding potential bugs in Java code.
- **Docker**: Containerization platform for packaging, distributing, and running applications during Integration Tests.
- **JaCoCo**:JaCoCo is a free Java code coverage library distributed under the Eclipse Public License.
## Getting Started
 
To run the Electricity Management System locally, follow these steps:
 
1. Clone this repository to your local machine.
2. Set up the MySQL database and configure the application.properties file with your database connection details.
3. Build the project using Maven: `mvn clean install`.
4. Run the application: `mvn spring-boot:run` or run through the IDE run button.
 

 
