# **Mini Survey Monkey - Milestone 2**

## **Project Overview**
Mini Survey Monkey is a web application designed to simplify the process of creating and participating in surveys. With this milestone, we have introduced new features and improvements to enhance functionality, user experience, and application reliability.

---

## **Recent Implementations and Improvements**

### **1. Survey Closure, Deletion, and Reopening**

#### **Survey Closure**
- **What’s New:**
   - Survey Status Update: Allow survey creators to change the survey status to closed.
   - Access Control: Prevent new participants from accessing closed surveys.
   - Notification: Inform users attempting to access closed surveys.

#### **Survey Deletion**
- **What’s New:**
   - Survey creators can now delete surveys they created, ensuring secure and restricted access to this feature.
   - Confirmation prompts and user feedback added for a seamless and safe user experience.
- **Implementation Details:**
   - Introduced a `delete` endpoint in the survey controller.
   - Added security configurations to protect the endpoint from unauthorized access.
   - Updated the frontend with a confirmation prompt to prevent accidental deletions.
   - Provided success messages upon successful deletion.

#### **Survey Reopening**
- **What’s New:**
   - Survey creators can reopen closed surveys, enabling participants to submit new responses.
   - Reopened surveys reflect as active in the frontend and are accessible to participants.
- **Implementation Details:**
   - Added a `reopen` endpoint in the survey controller.
   - Updated the survey model to toggle the `isClosed` status dynamically.
   - Ensured proper access control by restricting reopening functionality to survey creators.
   - Enhanced the frontend with a "Reopen Survey" button, including confirmation prompts and feedback messages.

---

### **2. Logout Functionality**
- **What’s New:**
   - Implemented a secure logout feature for authenticated users.
   - Upon logout, sessions are invalidated, cookies are cleared, and users are redirected to the login page with a success message.
- **Implementation Details:**
   - Configured logout URL and success URL in the security configuration.
   - Added a logout link in the navigation header for easy access.
   - Properly handled CSRF tokens during the logout process to maintain security standards.

---

### **3. Frontend Enhancements**

#### **Bootstrap Integration**
- **What’s New:**
   - Integrated Bootstrap 5 to modernize and enhance the UI.
   - Improved consistency and responsiveness across all pages using Bootstrap classes and components.
- **Templates Updated:**
   - **`index.html`**: Styled buttons, alerts, and list groups with Bootstrap. Improved layout for better readability.
   - **`login.html` & `register.html`**: Enhanced forms with Bootstrap classes and added validation feedback.
   - **`surveyForm.html`**: Used Bootstrap for survey creation inputs, buttons, and dynamic elements.
   - **`fillSurvey.html`**: Improved survey participant interface for better usability and responsiveness.
   - **`header.html`**: Created a reusable Bootstrap navbar for navigation across pages.

#### **JavaScript Adjustments**
- Refactored JavaScript to eliminate jQuery dependencies, leveraging vanilla JavaScript for better performance and maintainability.
- Verified that all dynamic functionalities (e.g., adding questions, handling form submissions) work seamlessly without jQuery.

#### **Static Resources Handling**
- Corrected paths to static files in templates.
- Updated security configuration to allow access to static files.
- Ensured that CSS and JS files are served correctly with appropriate MIME types.

---

### **4. Integration Testing and CI Workflow**
- **Integration Tests:**
   - Comprehensive integration testing was performed to validate the functionality of all features.
   - Verified that the application works cohesively, ensuring no regressions across the codebase.
- **CI Workflow:**
   - Created a GitHub Actions workflow to automatically run integration tests on every commit to the `main` branch.
   - Ensured that deployments only proceed if all tests pass successfully.

---

## **Team Contributions**

| Team Member         | Contribution                                           |
|----------------------|-------------------------------------------------------|
| **Adham Badawi**     | Survey deletion, reopening features, logout functionality |
| **Yasmina Younes**   | Frontend improvements, Bootstrap integration         |
| **Amr Abdelazeem**   | Integration testing and CI workflow                  |
| **Jaden Sutton**     | Integration testing                                  |
| **Ali El Khatib**    | Integration testing                                  |

---

## **Next Steps**
For the next milestone, we plan to:
1. Implement the **multiple-choice question type** in surveys.
2. Enhance error handling and validation across the application.
