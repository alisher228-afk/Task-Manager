📌 Task Management Service — Spring Boot Project

This project is a clean and structured Task Management backend built with Spring Boot.
It was developed as part of Module 5 (IITU) and demonstrates:

REST API design

Layered architecture

Business logic validation

Exception handling

Logging

Pagination and filtering using @Query

DTO ↔ Entity mapping

🚀 Features
✔ Create Tasks

Status is automatically set to CREATED

Creation date is assigned automatically

Priority is required

A user cannot have more than 5 active tasks

✔ Update Tasks

A DONE task cannot be modified unless moving it back to IN_PROGRESS

✔ Delete Tasks

Simple removal by ID

✔ Retrieve Tasks

Get a task by ID

Get all tasks

✔ Start Task (/start)

Validates that assigned user has fewer than 5 active tasks

Status becomes IN_PROGRESS

✔ Complete Task (/complete)

Status becomes DONE

doneDateTime is automatically assigned

✔ Task Search (Filtering + Pagination)

Filtering supports:

creatorId

assignedUserId

status

priority

Pagination:

pageNum

pageSize

🏛 Architecture Overview

Controller Layer
Handles all incoming REST requests.

Service Layer
Contains business rules, validation, and workflow logic.

Repository Layer
Uses Spring Data JPA to reduce boilerplate.

Entity Layer
Represents database structure.

DTO Layer
Defines input/output API models.

Mapper Layer
Converts entities to models and back.

GlobalExceptionHandler
Provides unified, structured error responses.

🛠 Technologies

Java 21+

Spring Boot 3

Spring Web

Spring Data JPA

Maven

PostgreSQL / H2

SLF4J logging

▶ How to Run
Build the project
mvn clean install

Start the application
mvn spring-boot:run


Server will run at:

http://localhost:8080

📦 Example API Requests
Create Task
POST /Task


Body:

{
  "creatorId": 1,
  "assignedUserId": 3,
  "priority": "HIGH",
  "deadlineDate": "2025-02-10"
}

Start Task
POST /Task/5/start

Complete Task
POST /Task/5/complete

Update Task
PUT /Task/3


Body:

{
  "assignedUserId": 7,
  "status": "IN_PROGRESS",
  "priority": "LOW",
  "deadlineDate": "2025-03-15"
}

Search Tasks

Examples:

/Task/search?creatorId=1&priority=HIGH&pageNum=0&pageSize=10
/Task/search?assignedUserId=3&status=IN_PROGRESS
/Task/search?pageNum=1&pageSize=5

📌 Business Rules Summary

A user cannot have more than 5 active (CREATED, IN_PROGRESS) tasks.

A DONE task cannot be edited.

deadlineDate must be in the future.

creatorId and priority are required fields.

Logging is added to key business operations.

🎯 Purpose of the Project

This project validates your ability to:

Structure a real production-like Spring Boot application

Use DTO + Entity separation

Avoid code duplication

Apply logging and exception handling

Utilize Git correctly (branching, commits, PR)

Prepare code for review
