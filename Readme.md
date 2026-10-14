# Financify

**Financify** is a desktop personal finance management application built with **Java 21, JavaFX, and SQLite**. It helps users manage transactions, track major expenses, monitor net worth, manage loans, and set savings goals through a clean and intuitive interface.

This project was developed to strengthen my Java and desktop application development skills while building a practical finance management tool for personal use.

## Features

### 📊 Dashboard
- Overview of the current financial situation
- Monthly income, expenses, and cash flow
- Savings rate calculation
- Expense breakdown by category
- Monthly financial summaries for the past 6 months
- Net worth growth overview for the past 6 months

### 💳 Transactions
- Add, edit, and delete transactions
- Track income and expenses
- Organize transactions by category and description
- Filter transactions by month and year
- Mark and manage major expenses separately
- Highlight major expenses in the transaction history

### 💰 Net Worth
- Track monthly bank balances
- Manage loans and outstanding balances
- Calculate net worth based on financial records

### 🎯 Savings Goals
- Create, update, and delete savings goals
- Track current progress toward each goal
- Calculate remaining amounts
- View overall savings statistics
- Set deadlines and track goal completion

### 🔐 Account Management
- Create a user account
- Log in with password verification
- Recover accounts using a recovery token
- Reset account passwords
- Require authentication when launching the application

### 💾 Database Backup
- Create manual backups of the SQLite database
- Choose where to save the backup file

### 📈 Financial Management
- Set and monitor a monthly spending limit
- Separate regular spending from major expenses
- Monitor income, expenses, savings, and cash flow

## Technologies

- Java 21
- JavaFX
- SQLite
- SQLite JDBC
- Gradle
- JDBC
- Java modules
- `jlink` and `jpackage` for packaging

## Project Structure

```text
Financify/
├── app/
│   ├── src/
│   │   └── main/
│   │       ├── java/
│   │       │   └── com.financify/
│   │       │       ├── App.java
│   │       │       ├── Database.java
│   │       │       ├── models/
│   │       │       └── views/
│   │       └── resources/
│   └── build/
├── gradlew
├── gradlew.bat
├── build.gradle
└── settings.gradle
```

*The structure above is simplified and may not include every source file.*

## Running the Project

### Requirements
- Java Development Kit (JDK) 21
- Git (to clone the repository)

Gradle is included through the Gradle Wrapper, so a separate Gradle installation is not required.

### Clone the Repository

```bash
git clone https://github.com/yourusername/Financify.git
cd Financify
```

Replace `yourusername` with the actual GitHub username or repository URL.

### Run the Application

On Windows:

```powershell
.\gradlew.bat run
```

On Linux or macOS:

```bash
./gradlew run
```

### Build the Project

```bash
./gradlew build
```

### Generate the Portable Application

```powershell
.\gradlew jpackageImage
```

The generated application image is available at:

```text
app/build/jpackage/Financify/
```

### Using the Portable Application

A prebuilt portable version of Financify is included in the repository.

**Location in the project:**

```text
Financify/
├── app/
├── finanicify/
│   ├── Financify.exe
│   ├── app/
│   └── runtime/
└── ...
```

To use it:

1. Copy the entire `finanicify` folder to your preferred location.
2. Open the folder.
3. Run `Financify.exe`.

**Important:** Copy the entire folder, including its subfolders. Do not copy only the executable, as it requires the bundled runtime and supporting files.

Java does not need to be installed separately to run this portable version.

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

**Data note:** Keep regular backups of the database. The database file must remain accessible and writable by the application.

## Purpose

Financify was created as a personal project and portfolio application to improve practical skills in:

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

Made with Java.