# Financify

**Financify** is a desktop personal finance management application built with **Java 21, JavaFX, and SQLite**. It helps users manage transactions, track major expenses, monitor net worth, manage loans, and set savings goals through a desktop interface.

This project was developed to strengthen my Java and desktop application development skills while building a practical finance management tool for personal use.

## Features

### Dashboard
- Overview of the current financial situation in the dashboard
- Monthly income, expenses, and cash flow
- Savings rate calculation
- Expense breakdown by category
- Monthly financial summaries for the past six months
- Net worth growth overview for the past six months

### Transactions
- Add, edit, and delete transactions
- Track income and expenses
- Organize transactions by category and description
- Filter transactions by month and year
- Mark and manage major expenses separately
- Highlight major expenses in transaction history

### Net Worth
- Track monthly bank balances
- Manage loans and outstanding balances
- Calculate net worth from financial records

### Savings Goals
- Create, update, and delete savings goals
- Track progress toward each goal
- Calculate remaining amounts
- View overall savings statistics
- Set deadlines and track goal completion

### Account Management
- Create a user account
- Authenticate users with password verification
- Recover accounts using a recovery token
- Reset account passwords
- Require authentication when launching the application

### Database Backup
- Create manual backups of the SQLite database
- Choose where to save backup files

### Financial Management
- Set and monitor a monthly spending limit
- Separate regular spending from major expenses
- Monitor income, expenses, savings, and cash flow

## Download the Application

The packaged Windows version is available in the [Financify v1.5.0 Release](https://github.com/zaid03/Financify/releases/tag/v1.5.0).

1. Open the latest release.
2. Download the `Financify-v1.5.0-Windows-x64.zip` asset.
3. Extract the ZIP archive.
4. Open the extracted `Financify` folder.
5. Launch `Financify.exe`.

The portable application includes its Java runtime and supporting files. A separate Java installation is not required.

**Important:** Keep the entire extracted folder together. Do not move or copy the executable, you may send it to your dekstop as a shortcut.

## Technologies

- Java 21
- JavaFX
- SQLite
- SQLite JDBC
- JDBC
- Gradle
- Java Platform Module System
- `jlink` and `jpackage` for packaging

## Project Structure

```text
Financify/
├── app/
│   └── src/
│       └── main/
│           ├── java/
│           │   └── com.financify/
│           │       ├── App.java
│           │       ├── Database.java
│           │       ├── models/
│           │       └── views/
│           └── resources/
├── Financify/          # Packaged Windows application
│   ├── Financify.exe
│   ├── app/
│   └── runtime/
├── gradlew
├── gradlew.bat
├── build.gradle
└── settings.gradle
```

*The structure above is simplified and may not include every source file.*

## Running from Source

### Requirements

- JDK 21
- Git

Gradle is provided through the Gradle Wrapper, so a separate Gradle installation is not required.


### Run the Application

After clonning the repository

```powershell
.\gradlew run
```

### Build the Project

On Windows:

```powershell
.\gradlew.bat build
```

### Generate the Windows Application Image

```powershell
./gradlew jpackageImage
```

The generated application image is available under:

```text
app/build/jpackage/Financify/
```

## Database

Financify uses SQLite to store financial records and account information.

The database contains tables for:

- Transactions
- Net worth
- Savings goals
- Loans
- Monthly spending limits
- User accounts and password recovery tokens

The database schema is initialized automatically when the application starts and the database does not yet exist.

Financial records and dashboard statistics are retrieved using SQL queries and aggregation.

**Data note:** Keep regular backups of your database. Ensure the database file remains accessible and writable by the application.

## Purpose

Financify was created as a personal project and portfolio application to develop practical skills in:

- Object-oriented programming
- Java and JavaFX development
- Desktop application architecture
- SQL and relational database design
- CRUD operations
- Authentication and password management
- Financial calculations and data visualization
- Gradle build automation
- Packaging Java applications for Windows

## License

This project is licensed under the MIT License. See the `LICENSE` file for details.

## Author

**Zaid Terguy**

Built with Java.