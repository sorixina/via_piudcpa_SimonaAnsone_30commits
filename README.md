## OC Storage System

OC Storage System, also known as **Base**, is a desktop application developed in Java using JavaFX. It is designed to help users create, organize, and manage their original characters (OCs) through a simple and accessible interface.

The project was developed using Eclipse IDE as a personal Java application.

## Technologies Used

- Java 21
- JavaFX 21
- Eclipse IDE
- FXML and CSS
- MySQL (database integration)

## Setup Instructions

### 1. Download the Project

1. Click the green **Code** button on this GitHub repository.
2. Select **Download ZIP**.
3. Extract the downloaded ZIP file to a folder on your computer.

### 2. Import the Project into Eclipse

1. Open Eclipse IDE.
2. Select **File → Import...**
3. Expand **General** and select **Existing Projects into Workspace**.
4. Click **Next**.
5. Select **Browse...** next to *Select root directory*.
6. Navigate to the extracted `OCStorageSystem` folder.
7. Select the project and click **Finish**.

### 3. Configure JavaFX

This project uses JavaFX 21, which must be configured separately.

1. Download the JavaFX 21 SDK from [OpenJFX](https://openjfx.io/).
2. Extract the SDK to a folder on your computer.
3. In Eclipse, right-click **OCStorageSystem → Properties**.
4. Navigate to **Java Build Path → Libraries**.
5. Select **Classpath → Add External JARs...**
6. Open the `lib` folder inside the extracted JavaFX SDK.
7. Select the JavaFX `.jar` files and click **Open**.
8. Click **Apply and Close**.

### 4. Configure JavaFX Runtime

1. In Eclipse, select **Run → Run Configurations...**
2. Under **Java Application**, create or select a configuration for `OCMain`.
3. Open the **Arguments** tab.
4. Under **VM arguments**, enter:

   ```
   --module-path "PATH_TO_JAVAFX_SDK/lib" --add-modules javafx.controls,javafx.fxml
   ```

5. Replace `PATH_TO_JAVAFX_SDK` with the actual location of the extracted JavaFX SDK.
6. Click **Apply**.

### 5. Database Integration

The application was developed with MySQL database integration.

Database configuration and setup instructions will be provided in a future update (when the awesome lecturer teaches us about it :D). Until then, database-dependent features such as login, registration, and character management may not function without a correctly configured database and MySQL.

## Project Status

This project is currently under development.

The repository contains the application's Java source code, interface layouts, and styling resources.

Database configuration will be added in a future update.
