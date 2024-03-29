# ChâTop Rental Portal - Backend
Welcome to ChâTop! This backend project is designed to complement the existing 
[Angular application](https://github.com/OpenClassrooms-Student-Center/Developpez-le-back-end-en-utilisant-Java-et-Spring)
that facilitates the connection between potential tenants and property owners for seasonal rentals.

# Architecture Java
The application follows a layered architecture (Controller/Service/JPA Repository) for clear code organization.

# Security
Security is managed using Spring Security and JWT. All routes require authentication (except those for account creation 
or login). Passwords are encrypted in the database, and database credentials are not exposed in the code.

# Prerequisites
Ensure you have the following installed on your machine:
- [MySQL](https://dev.mysql.com/downloads/installer/)
- [Java 21](https://www.oracle.com/fr/java/technologies/downloads/#java21)
- [Maven](https://maven.apache.org/download.cgi)

# Getting Started
- **Clone the repository to your local machine:**

`git clone https://github.com/Mihai-Cojusnean/Developpez-le-back-end-en-utilisant-Java-et-Spring.git`

- **Navigate to the project directory:**

`cd Developpez-le-back-end-en-utilisant-Java-et-Spring.git`

- **Configure MySQL Database:**

1. Update the application properties in the backend project to connect to your MySQL database.
2. Import data from `resources/sql/script.sql`.

- **Run the Backend:**

Use your preferred IDE to open the backend project and run the Spring Boot application.

- **Explore Swagger Documentation:**

To access the Swagger documentation for a detailed understanding of each API endpoint navigate to
http://localhost:8075/swagger-ui/index.html.