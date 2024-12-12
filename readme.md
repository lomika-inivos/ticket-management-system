# Ticket Management System

This repository contains a Ticket Management System with a core Java CLI application and a Spring backend.

## Project Structure

 ├── core-java-cli/ │ ├── .classpath │ ├── .gitignore │ ├── .idea/ │ ├── .project │ ├── .settings/ │ ├── config.json │ ├── pom.xml │ ├── src/ │ ├── target/ ├── react-frontend/ │ ├── .DS_Store │ ├── .gitignore │ ├── package.json │ ├── public/ │ ├── README.md │ ├── src/ ├── spring-backend/ │ ├── spring-backend/ │ ├── .gitignore │ ├── mvnw │ ├── mvnw.cmd │ ├── pom.xml │ ├── src/ │ ├── target/ └── .vscode/ ├── settings.json


## Core Java CLI

The core Java CLI application is located in the `core-java-cli` directory. It is responsible for managing the configuration settings of the ticket management system and simulating a vendor releasing tickets.

### Key Classes

- [`org.iit.oop.Main`](core-java-cli/src/main/java/org/iit/oop/Main.java): The main entry point of the application.
- [`org.iit.oop.config.Config`](core-java-cli/src/main/java/org/iit/oop/config/Config.java): Manages configuration settings and provides methods to save and load configurations from a file.
- [`org.iit.oop.springbackend.model.Vendor`](core-java-cli/src/main/java/org/iit/oop/springbackend/model/Vendor.java): Simulates a vendor releasing tickets at a specified rate.

### Build and Run

To build and run the core Java CLI application, navigate to the `core-java-cli` directory and use Maven:

```sh
cd core-java-cli
mvn clean install
mvn exec:java -Dexec.mainClass="org.iit.oop.Main"


Spring Backend
The Spring backend is located in the spring-backend directory. It provides RESTful APIs for managing tickets and vendors.

Build and Run
To build and run the Spring backend, navigate to the spring-backend directory and use Maven:

cd spring-backend
./mvnw clean install
./mvnw spring-boot:run

React Frontend
The React frontend is located in the react-frontend directory. It provides a user interface for interacting with the ticket management system.

Build and Run
To build and run the React frontend, navigate to the react-frontend directory and use npm:

cd react-frontend
npm install
npm start

Configuration
The configuration settings for the core Java CLI application are stored in the config.json file in the core-java-cli directory. The Config class provides methods to save and load configurations from this file.

License
This project is licensed under the MIT License. See the LICENSE file for details.
