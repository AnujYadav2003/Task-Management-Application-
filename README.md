# Task Management System

## Overview
The **Task Management System** is a web application designed to facilitate task management. It allows us to create, update, delete, and manage tasks efficiently. The system is built using **Spring Boot** for the backend and provides RESTful APIs for task and user management.

## Features

### Task Features
- [ ] **Create a Task**: Users can create new tasks.
- [ ] **Update a Task**: Users can modify existing tasks.
- [ ] **Delete a Task**: Users can remove tasks from the system.
- [ ] **Get All Tasks**: Retrieve a list of all tasks.
- [ ] **Get Task by ID**: Fetch details of a specific task using its ID.
- [ ] **Group Tasks by Status**: View tasks categorized by their current status.
- [ ] **Filter Tasks by Status**: Retrieve tasks based on their completion or progress status.
- [ ] **Sort Tasks by Creation Date**: Sort tasks based on the time they were created.
- [ ] **Paginate Tasks**: Retrieve tasks in paginated form for better performance.
- [ ] **Search Tasks**: Search for tasks based on a query string.

### User Features
- [ ] **User Registration**: Users can register with their credentials.
- [ ] **User Login**: Authenticate users using JWT tokens.
- [ ] **Update User Information**: Users can update their details.
- [ ] **Delete User**: Users can remove their accounts.
- [ ] **Get All Users**: Retrieve a list of all registered users.
- [ ] **Get User by ID**: Fetch details of a specific user.

## Technologies Used
- [ ] **Java**: Core backend development.
- [ ] **Spring Boot**: Framework for building RESTful APIs.
- [ ] **Spring Security & JWT**: Authentication and authorization.
- [ ] **PostgreSQL**: Database for storing tasks and user data.
- [ ] **JPA (Java Persistence API)**: ORM for database interactions.
- [ ] **Swagger**: API documentation.
- [ ] **Maven**: Dependency and project management.
- [ ] **Unit Testing**: Testing the application using Junit/Mokito.   

## API Documentation

The Task Management System API provides the following endpoints:



### Task Endpoints
| Method | Endpoint | Description |
|--------|---------|-------------|
| `POST` | `/api/tasks` | Create a new task |
| `GET` | `/api/tasks` | Retrieve all tasks |
| `GET` | `/api/tasks/{id}` | Retrieve task by ID |
| `PUT` | `/api/tasks/{id}` | Update a task by ID |
| `DELETE` | `/api/tasks/{id}` | Delete a task by ID |
| `GET` | `/api/tasks/grouping` | Get tasks grouped by status |
| `GET` | `/api/tasks/filter?status={status}` | Filter tasks by status |
| `GET` | `/api/tasks/sorting` | Sort tasks by creation date |
| `GET` | `/api/tasks/pagination?page={page}&size={size}` | Paginate tasks |
| `GET` | `/api/tasks/search?query={query}` | Search tasks |

![image](https://github.com/user-attachments/assets/1ac4f5c6-9637-43da-a350-34977d9c6933)
![image](https://github.com/user-attachments/assets/b75a61a5-5cf5-420f-a384-1af2a9aedfab)

### User Endpoints
| Method | Endpoint | Description |
|--------|---------|-------------|
| `GET` | `/api/users` | Retrieve all users |
| `GET` | `/api/users/{id}` | Retrieve user by ID |
| `PUT` | `/api/users/{id}` | Update a user by ID |
| `DELETE` | `/api/users/{id}` | Delete a user by ID |

![image](https://github.com/user-attachments/assets/eae242a2-29f8-496d-90ad-41fb53549e9c)
![image](https://github.com/user-attachments/assets/02fb9c4e-4196-4b9a-8624-287e920428b9)

## Unit test coverage report 

![image](https://github.com/user-attachments/assets/3910cc99-bc5d-4ecf-96c8-a8d7c3b854f2)

## Setup Instructions

### Prerequisites
- **Java 17+**
- **Spring Boot 3+**
- **Maven**
- **PostgreSQL Database**

### Installation Steps
1. Clone the repository:
   ```sh
   git clone https://github.com/yourusername/task-management-system.git
   cd task-management-system
   ```
2. Configure the PostgreSQL database in `application.properties`:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/taskdb
   spring.datasource.username=postgres
   spring.datasource.password=yourpassword
   spring.jpa.hibernate.ddl-auto=update
   ```

3. Build and run the project:
   ```sh
   mvn clean install
   mvn spring-boot:run
   ```
4. Access Swagger API documentation at:
   ```
   http://localhost:8080/swagger-ui/index.html
   ```

## How to Run
1. Start the PostgreSQL database server.
2. Verify the database configuration in `application.properties`.
3. Run the following command to start the application:
   ```sh
   mvn spring-boot:run
   ```
4. Open the API documentation or use Postman to test endpoints.

## Contributing
Contributions are welcome! Follow these steps:
1. Fork the repository.
2. Create a new feature branch.
3. Commit your changes.
4. Push the changes and submit a pull request.

