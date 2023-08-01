# Border Control Management System

## Project Motivation

The `Border Control Management System` is a set of applications designed to provide a simple yet effective solution for managing and controlling the movement of passengers crossing the country's borders. The system comprises several applications, including administrative and client applications, as well as a simulation application for testing purposes.

## Applications

1. `Admin Application` is designed for administrators to manage the entire Border Control Management System. It features a user-friendly graphical interface (`JavaFX`) that facilitates the addition, modification, and deletion of car customs terminals, entry/exit points, and other critical data.Administrators can also monitor system activities, view detailed logs, and perform various administrative tasks through the Admin Application. Apache CXF is used to implement SOAP-based web services to communicate between the Admin Application and the central register.

2. `Client Application`: The client application represents individual checkpoints at customs terminals. It allows users to log in by providing the terminal ID, type of checkpoint (police or customs), and the name of the customs terminal. The user's credentials are verified through a REST service and stored in an in-memory database. The client application supports chat communication with other checkpoints. All chat communication is handled securely through a Socket server. Additionally, the client application maintains a record of verified passengers, which is stored in the central registry as a text file.

3. `Test Application`: This application simulates the entry or exit process at a specific customs terminal. It prompts users to input the terminal name, choose whether it is an entry or exit checkpoint (with the ID), and check whether the terminal exists through SOAP messages. The simulation then proceeds with the police and customs checks, communicating with the respective parts of the terminal through SOAP messages. If a person is on the wanted list, a message is sent to all checkpoints, temporarily closing the border crossing until the person is processed. Once the person is cleared, a notification is sent to the police checkpoint through a socket. The simulation also involves handling customs documents, which are stored on a file server in a structured and efficient manner.

4. `File Server`: The File Server is responsible for managing the storage and retrieval of various files within the system, such as customs documents and relevant travel records. It employs Java NIO to ensure efficient and non-blocking handling of file I/O operations, leading to optimal performance. The File Server ensures the secure and reliable storage of essential documents and data related to border crossings.

5. `Central Register Server`: The Central Register serves as the heart of the system, storing essential data, such as records of customs terminals, user credentials, and detected wanted persons. It manages the serialization and deserialization of data related to the customs terminals (including multiple serialization methods) and the registry of wanted persons (using XML format). 

6. `Chat Server`: The Chat Server enables real-time communication between different client applications within the system. Using socket programming, it allows clients to exchange messages via multicast and broadcast methods. The Chat Server handles message distribution, ensuring that relevant information reaches the intended recipients.

## Getting started

### Key Dependencies & Platforms

- [JDK 11]('https://www.oracle.com/java/technologies/javase/jdk11-archive-downloads.html') or later: The Java Development Kit (JDK) version 11 or a later version is required to develop and run the system. It provides the necessary tools and libraries for Java application development.

- [Eclipse IDE]('https://www.eclipse.org/ide/'): Eclipse IDE is the Integrated Development Environment used to develop Java applications. It offers various features, such as code editing, debugging, and version control integration, to facilitate efficient software development.

- [JavaFX]('https://openjfx.io/'): JavaFX is a set of libraries and tools for building modern, rich client applications using Java. It provides a powerful framework for creating GUI-based applications with a rich user interface.

- [Scene Builder]('https://www.oracle.com/java/technologies/javase/javafxscenebuilder-info.html'): Scene Builder is a visual layout tool for designing JavaFX application interfaces. It allows developers to create the graphical user interface of the applications using drag-and-drop features, which simplifies the design process.

Make sure to have these dependencies installed and configured correctly to develop and run the Border Control Management System smoothly. Additionally, you may need to set up the relevant project configurations in Eclipse IDE to ensure seamless integration with JavaFX and Scene Builder for GUI development.

## Key Features

- `Central Registry`: The system uses a central registry to store and manage terminal data. Terminals are added, updated, and deleted through the administrative application, and the data is serialized using four different serialization methods.

- `SOAP Communication`: The administrative application communicates with the central registry using SOAP messages to perform various operations on terminals.

- `REST Service`: The user credentials for the client application are verified using a REST service. Administrators can manage user credentials through the administrative application.

- `Chat Communication`: The client application supports chat communication between checkpoints through a secure Socket server.

- `Document Management`: The simulation application allows users to attach and handle customs documents, which are efficiently stored on a file server.

- `XML Data Storage`: The system stores the record of detected wanted persons and customs documents in XML format in the central registry.

- `Properties Files`: All parameters used in the GUI applications and other programs are defined in appropriate properties files for easy configuration.

- `Exception Handling`: The system employs the Logger class for effective exception handling.

- `Data Prepopulation`: A script or program can be used to populate initial data to facilitate testing.