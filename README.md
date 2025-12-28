# ToDo REST API Backend

A production-ready **RESTful ToDo backend** built with **Java Servlet, JDBC, and PostgreSQL**.  
The API is designed to be consumed by modern frontends such as **React (Tailwind CSS)** and **React Native**.

---

## ✨ Features

- RESTful CRUD APIs (JSON)
- Java Servlet (Jakarta – Tomcat 10/11)
- JDBC with PostgreSQL
- Centralized exception handling
- CORS support for web & mobile clients
- Clean, layered architecture
- Fully tested using Postman

---

## 🛠️ Tech Stack

### Backend
- Java 11+
- Jakarta Servlet API
- JDBC
- PostgreSQL
- Jackson (JSON serialization)

### Frontend (Planned)
- React + Tailwind CSS
- React Native

---

## 🏗️ Architecture

```
Client (React / React Native)
        │
        │ REST (JSON)
        ▼
TodoServlet (Controller)
        │
        ▼
TodoDAO (JDBC)
        │
        ▼
PostgreSQL
```

---

## 📂 Project Structure

```
src/main/java/com/nsgacademy/todo/
├── model/        # Domain model
├── dao/          # JDBC data access layer
├── util/         # Database utilities
├── filter/       # CORS & JSON filters
├── web/          # REST controllers
└── exception/    # Custom exceptions

sql/
└── todo_schema.sql
```

---

## 🗄️ Database Setup

### Create Database
```sql
CREATE DATABASE todo_db;
```

### Create Table
```sql
CREATE TABLE todo (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    priority VARCHAR(10) NOT NULL,
    status VARCHAR(10) NOT NULL,
    due_date DATE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

SQL schema is available in:
```
sql/todo_schema.sql
```

---

## 🌐 REST API Endpoints

Base URL:
```
http://localhost:8080/<context-path>/api/todos
```

| Method | Endpoint | Description |
|------|----------|-------------|
| GET | /api/todos | Get all todos |
| GET | /api/todos/{id} | Get todo by ID |
| POST | /api/todos | Create a new todo |
| PUT | /api/todos/{id} | Update an existing todo |
| DELETE | /api/todos/{id} | Delete a todo |

---

## 📦 Sample Request (POST / PUT)

```json
{
  "title": "Learn Servlet",
  "description": "Build REST backend",
  "priority": "HIGH",
  "status": "PENDING",
  "dueDate": "2025-01-10"
}
```

---

## 📤 Sample Response

```json
{
  "id": 1,
  "title": "Learn Servlet",
  "description": "Build REST backend",
  "priority": "HIGH",
  "status": "PENDING",
  "dueDate": "2025-01-10",
  "createdAt": "2025-12-27T21:26:54.700881",
  "updatedAt": "2025-12-27T21:26:54.700881"
}
```

---

## 🔐 Filters

### CORS Filter
- Allows cross-origin requests
- Supports React & React Native clients

### JSON Filter
- Enforces `Content-Type: application/json`
- Ensures REST API discipline

---

## ⚠️ Error Handling

- All database errors are wrapped in a custom `DAOException`
- Centralized error handling in REST controller
- Consistent JSON error responses
- No stack traces exposed to clients

---

## 🧪 API Testing

All endpoints are tested using **Postman**:
- GET (all / by ID)
- POST
- PUT
- DELETE

---

## 🚀 Getting Started

1. Clone the repository
2. Create the PostgreSQL database and table
3. Configure DB credentials in `DBUtil`
4. Deploy the project on Tomcat 10/11
5. Test APIs using Postman

---

## 🔮 Future Enhancements

- React (Tailwind) frontend
- React Native mobile app
- Input validation
- Pagination & sorting
- Authentication (JWT)

---

## 📌 Version

**v1.0-backend-rest-stable**
