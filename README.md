# BlogApp

This repository contains the backend source code for a comprehensive blogging application. It is built with Java, Spring Boot, and Spring Security, providing a robust RESTful API for managing users, posts, categories, and comments. 
The application features JWT-based authentication and authorization, role-based access control, pagination, and file uploads.

## Key Features

*   **Authentication & Authorization**: Secure registration and login system using JSON Web Tokens (JWT).
*   **Role-Based Access Control**: Differentiates between `ADMIN_USER` and `NORMAL_USER` roles, with specific permissions for certain actions (e.g., deleting users).
*   **User Management**: Complete CRUD operations for user profiles.
*   **Post Management**: Full CRUD capabilities for blog posts, including pagination, sorting, and searching.
*   **Category Management**: Ability to create, read, update, and delete post categories.
*   **Comment System**: Users can add, update, and delete comments on posts.
*   **Image Uploads**: Functionality to upload and serve images for blog posts.
*   **Centralized Exception Handling**: Graceful handling of API errors and validation failures.
*   **API Documentation**: Integrated Swagger UI for interactive API documentation and testing.

## Technologies Used

*   **Framework**: Spring Boot 3
*   **Language**: Java 17
*   **Security**: Spring Security, JWT (JSON Web Token)
*   **Database**: Spring Data JPA, Hibernate, MySQL
*   **API**: Spring Web (RESTful)
*   **Build Tool**: Maven
*   **Utilities**: Lombok, ModelMapper
*   **API Documentation**: SpringDoc OpenAPI (Swagger UI)


