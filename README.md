# Intask 
### About 

A **Spring Boot** application is a Java-based web application framework that provides a comprehensive infrastructure for developing and deploying web applications quickly and easily. The application is **stateless** and uses **JWT token** authentication, **RESTful** architecture to expose resources as web services that can be consumed by clients over **HTTP**.

The application is a to-do list manager with a Board and Tasks. The Board is the main container that holds the tasks. Each task is a separate entity that can be added, modified, or removed. The Board and Tasks are represented as RESTful resources, with each resource having its own URI.

| PART        | Version        | 
|-------------|----------------|
| JAVA        |       17       | 
| SPRING BOOT |       2.7.9    | 


### API endpoints  
#### Login and registration users

| HTTP Method | Endpoint        | Description                      | Example                                           |
|-------------|----------------|----------------------------------|---------------------------------------------------|
| POST        | /api/v1/register | Register user                    | `{"username": "user", "password": "1234"}` |
| POST        | /api/v1/auth    | Authenticate user                | `{"username": "user", "password": "1234"}` |

#### Boards and tasks 
| HTTP Method | Endpoint                          | Description                                             |
| ----------- | -------------------------------- | ------------------------------------------------------- |
| GET         | /api/v1/boards                   | Get all boards belonging to the authenticated user      |
| GET         | /api/v1/boards/{id}              | Get a specific board by ID for the authenticated user   |
| POST        | /api/v1/boards                   | Create a new board for the authenticated user           |
| DELETE      | /api/v1/boards/{id}              | Delete a specific board by ID for the authenticated user|
| PUT         | /api/v1/boards/{id}              | Update a specific board by ID for the authenticated user|
| GET         | /api/v1/boards/{boardId}/tasks/{taskId} | Get a specific task on a specific board for the authenticated user |
| GET         | /api/v1/boards/{id}/tasks        | Get all tasks for a specific board for the authenticated user |
| POST        | /api/v1/boards/{id}/tasks        | Add a new task to a specific board for the authenticated user|
| PUT         | /api/v1/boards/{boardId}/tasks/{taskId} | Update a specific task on a specific board for the authenticated user |
| DELETE      | /api/v1/boards/{boardId}/tasks/{taskId} | Delete a specific task on a specific board for the authenticated user |



### Architecture

```mermaid
graph LR
A[DB] -- SQL --> B((REPOSITORY))
B --> D{Service}
D --> C{Controller}
C -- HTTP --> F[POSTMAN]
F -- HTTP --> C
C --> D
D --> B
B -- SQL -->  A
```

### DOCKER CONTAINER setup 
In directory find docker-compose file.
use it for create container with postman database and java application 


docker-compose.yaml



    version: "3.8"  
      
    services:  
      db:  
        image: 'postgres:13.1-alpine'  
      container_name: db  
        ports:  
          - 5433:5432  
        environment:  
          - POSTGRES_USER=postgres  
          - POSTGRES_PASSWORD=123123  
      app:  
        depends_on:  
          - db  
        image: kutik9232/intask-app  
        build: .  
        ports:  
          - 8081:8080  
        environment:  
          - SPRING_DATASOURCE_URL=jdbc:postgresql://db:5432/postgres  
          - SPRING_DATASOURCE_USERNAME=postgres  
          - SPRING_DATASOURCE_PASSWORD=123123  
          - SPRING_JPA_HIBERNATE_DDL_AUTO=update
`docker-compose up` command in same directory

After up the container you need insert in table roles value **"USER"**
we can do it 
* with connect to db with **Intelij ide** 
* go to **Docker's terminal** in container db -> 
   `#psql -U postgres` and sql script `INSERT INTO roles VALUES(1, 'USER');`  
That's all now you can send **HTTP** request for example **POSTMAN client**  

And the final collection for **POSTMAN** you can find in **resourses** directory `Intask.postman_collection.json`
