# Student Management Android App

A simple and efficient Android application built with Java for managing student records. The app uses the ROOM persistence library for local data storage and provides full CRUD (Create, Read, Update, Delete) functionality for student data, secured by a local login/signup system.

---

## Features

* **User Authentication**: Secure signup and login system to protect data.
* **Add Students**: Easily add new student records with details like name, surname, class, roll number, and marks.
* **View All Students**: A clean list view of all student records stored in the database.
* **Update & Delete**: Tap on any student to update their details, or long-press to delete a record.
* **Dynamic Search**: A powerful search bar to instantly find students by any field (name, class, roll number, etc.).
* **Local Storage**: Utilizes the Android ROOM library for robust and efficient local data management.
* **Logout**: Securely log out of the application.

---

## Application Flow & Screenshots

Here’s a visual walkthrough of the app's core functionalities.

| Splash Screen                                | Login Screen                           | Signup Screen                            |
| :------------------------------------------- | :------------------------------------- | :--------------------------------------- |
| ![Splash Screen](./images/start_SMS.png) | ![Login Screen](./images/login_SMS.png)    | ![Signup Screen](./images/signup_SMS.png)    |

| Dashboard                                | Add Student                                  | View All Students                                  |
| :--------------------------------------- | :------------------------------------------- | :------------------------------------------------- |
| ![Dashboard](./images/home_SMS.png)     | ![Add Student Form](./images/add_SMS.png) | ![View Students List](./images/view_SMS.png) |

| Update Student                                     |
| :------------------------------------------------- |
| ![Update Student Form](./images/update_SMS.png) |
| *Long-press on a student in the list to trigger the delete option.* |

---

## Technology Stack

* **Language**: Java
* **Platform**: Android
* **Database**: Android ROOM Persistence Library

---

## How To Use

1.  **Clone the repository:**
    ```bash
    git clone https://github.com/your-username/your-repo-name.git
    ```
2.  **Open in Android Studio:**
    * Open Android Studio.
    * Click on `File` > `Open` and navigate to the cloned project directory.
3.  **Build and Run:**
    * Let Android Studio sync the Gradle files.
    * Click the `Run` button to build and install the app on an emulator or a physical device.
