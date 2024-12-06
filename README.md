# Mini Survey Monkey - Final Project

## Overview

Mini Survey Monkey is a web-based survey application that allows authenticated users to create surveys consisting of various question types, distribute these surveys, and collect responses. The application supports three types of questions:

1. **Open-ended questions:** Respondents provide a free-text answer.
2. **Number range questions:** Respondents select a number within a predefined minimum and maximum range.
3. **Multiple Choice (MCQ) questions:** Respondents choose from a list of options.

This project was developed using the Spring Boot framework with Spring MVC and JPA for backend logic, Thymeleaf for server-side template rendering, and standard web technologies (HTML, CSS, JavaScript) on the frontend. Security is handled by Spring Security, ensuring that only authorized users can create and manage surveys, while all users can participate if the survey is open.

### Relational Database Schema

![Relational Database Schema](docs/Relational%20Database%20Schema.jpeg)

### UML Class Diagram

![UML Class Diagram](docs/UML%20Class%20Diagram.jpeg)

### UML Class Diagram

![Sequence Diagram](docs/Sequence%20Diagram.jpeg)

## Key Features

- **User Authentication & Authorization:**
  - Users must be authenticated to create and manage surveys.
  - The creator of a survey can close the survey at any time, preventing new participants from responding.
  - Authorization checks ensure only the survey’s creator can close or view results that are restricted.

- **Survey Creation & Management:**
  - Authenticated users can create new surveys, providing a title and adding questions dynamically.
  - Questions can be of three types:
    - **Open-Ended:** Respondents provide free-form text answers.
    - **Number Range:** Respondents pick a number within defined min and max values.
    - **Multiple Choice (MCQ):** Respondents pick one option from a provided set of choices.
  - Surveys can be updated (title, questions, etc.) and closed by their creator.

- **Participation & Response Collection:**
  - Any user (including anonymous or just-registered users, depending on configuration) can participate in open surveys. If you have chosen to restrict participation to logged-in users, then only authenticated users can respond.
  - For MCQ questions, respondents select an option from a set of predefined choices.
  - For Number Range questions, the application enforces min/max constraints and provides an error message if a user submits an out-of-range value.

- **Results & Visualization:**
  - The survey creator can view the survey results once the survey is closed (or even before, if configured).
  - Open-ended responses are displayed as a list.
  - Number range responses can be aggregated (e.g., displayed as histograms).
  - MCQ responses can be aggregated to show counts or percentages of each option selected.

## Architecture & Technologies

- **Backend:**
  - **Spring Boot:** Simplifies application setup and configuration.
  - **Spring MVC:** Used to implement the RESTful endpoints and traditional MVC controllers for serving Thymeleaf templates.
  - **Spring Data JPA:** Offers easy integration with a relational database. Entities and repositories are defined using standard JPA mappings.
  - **Spring Security:** Handles user authentication and authorization checks.

- **Frontend:**
  - **Thymeleaf:** Templating engine that integrates seamlessly with Spring MVC. Templates are used for rendering HTML pages like survey creation forms, participation forms, and result pages.
  - **Bootstrap & Custom CSS:** For styling and responsive layout.
  - **JavaScript:** Used for dynamic form manipulation (e.g., adding multiple questions, switching question types, and rendering MCQ options). Simple client-side validation for user inputs.

- **Database:**
  - Uses a relational database (e.g., H2 in-memory database for development and testing, or any other relational database configured in `application.properties`).
  - Entities are defined with standard JPA annotations. `@OneToMany`, `@ManyToOne`, and `@ElementCollection` are used to manage relationships and collections like MCQ options.

## Running the Application

1. **Prerequisites:**
  - Java 17+
  - Maven 3.6+

2. **Build & Run:**
  - Clone the repository from your version control system.
  - Navigate to the project directory.
  - Run `mvn clean install` to build and test the application.
  - Run `mvn spring-boot:run` to start the server.
  - By default, the application runs on `http://localhost:8080`.

3. **Accessing the Application:**
  - Open `http://localhost:8080/` in your web browser.
  - If security is enabled, register a new user or log in with existing credentials.
  - Once authenticated, you can create a survey, add various question types, and save it.
  - Share the survey link (e.g., `http://localhost:8080/survey/{id}/participate`) with participants.

## Configuration

- **`application.properties`:**
  - Configure database settings, Thymeleaf, security, and other application properties here.
  - Example (for H2 in-memory):
    ```properties
    spring.datasource.url=jdbc:h2:mem:testdb
    spring.datasource.driver-class-name=org.h2.Driver
    spring.jpa.hibernate.ddl-auto=update
    spring.jpa.show-sql=true
    ```

## Testing

- **Unit Tests:**
  - Basic unit tests are provided for the model layer, ensuring that adding and removing responses and setting up MCQ options works as expected.
  - Example tests in `MultipleChoiceQuestionTest.java` verify that MCQ questions and responses behave correctly.

- **Integration Tests:**
  - Tests can be extended to cover full request/response cycles with a running Spring Boot context (e.g., using `@SpringBootTest` and `TestRestTemplate`).
  - H2 is typically used for integration tests to mimic real database operations.

## Deployment

### Local Deployment:
  - The application runs locally by default on `http://localhost:8080`.

### Deployment on Azure & CI/CD with GitHub Actions

The application is continuously deployed to Azure App Service, ensuring that the production environment is always running the latest tested code.

**Key Points:**

1. **Azure App Service Deployment:**  
   The application is hosted on Azure App Service and can be accessed at:  
   [https://mini-survey-monkey-app-cdbjhrh9c7hycqa0.canadacentral-01.azurewebsites.net/](https://mini-survey-monkey-app-cdbjhrh9c7hycqa0.canadacentral-01.azurewebsites.net/)  
   By leveraging Azure's managed hosting environment, we benefit from automatic scaling, load balancing, and integrated logging and monitoring features.

2. **CI/CD with GitHub Actions:**  
   The project includes a `.github/workflows` directory containing YAML files that define a CI/CD pipeline using GitHub Actions. This pipeline is triggered whenever a push is made to the `main` branch or when a pull request is merged into `main`.  
   **Pipeline Steps:**
  - **Build & Test:** The pipeline runs `mvn clean install` to build the project and execute unit and integration tests.
  - **Integration Tests:** As part of the build step, integration tests run against an in-memory database (H2) environment. These tests ensure that no regressions are introduced before deployment.
  - **Deployment to Azure:** If all tests pass, the pipeline uses the `Azure Web Apps Deploy` GitHub Action to push the latest build artifact (JAR file) to Azure App Service. Azure then restarts the application with the newly deployed code.

3. **Benefit of CI/CD Setup:**
  - **No Manual Intervention:** The entire process from code merge to production deployment is automated, reducing human error.
  - **Continuous Quality Assurance:** The integration tests act as a gatekeeper, ensuring only code that passes all quality checks is deployed, preserving application integrity and stability.
  - **Rapid Iteration:** Developers can merge code confidently, knowing that an automated system will catch errors and prevent faulty code from reaching production.

By integrating with Azure and setting up a CI/CD pipeline with GitHub Actions, the application maintains a high standard of code quality, reliability, and consistency for users accessing it through the provided Azure-hosted URL.

## Contributing

- Fork the repository and create a new branch for your feature or bug fix.
- Make changes and run tests locally.
- Submit a pull request with a clear description of your changes.

## License

- This project is distributed under the [MIT License](LICENSE).

---

**In summary**, this final project provides a complete system for survey creation and participation with multiple question types including MCQs, fully integrated with proper validation, security, and data integrity checks. The included tests and runtime instructions ensure easy maintainability and extensibility for future enhancements.