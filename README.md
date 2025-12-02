📄 README.md

# 🧠Todo App

![Java](https://img.shields.io/badge/Java-25-blue.svg)
![Build](https://img.shields.io/badge/build-passing-brightgreen)
![License](https://img.shields.io/badge/license-MIT-lightgrey)
![Tests](https://img.shields.io/badge/tests-JUnit5-green)

A clean, test-driven CLI task manager built in Java.

---
## 📌 Add Task
```
Choice:
1
Title: Buy groceries
Description: Milk, eggs, bread
Priority: HIGH
Created: ✅
Task{id=1001, title='Buy groceries', priority=HIGH, status=PENDING, createdAt=2025-12-02 12:32:12, completedAt=-}
```
---

## 📋 List Tasks
```
Choice:
2
[1001] Buy groceries — HIGH — PENDING — Created at 2025-12-02 12:32:12
```
## ✏️ Update Task
```
Choice:
3
ID: 1001
New Title: Buy groceries and fruits
New Description: Milk, eggs, bread, bananas
New Priority: MEDIUM
Updated: ✅
Task{id=1001, title='Buy groceries and fruits', description='Milk, eggs, bread, bananas', priority=MEDIUM, status=PENDING, createdAt=2025-12-02 12:32:12, completedAt=-}
```
## 🔄 Change Status
```
Choice:
4
Task ID to change status: 1001
New status (PENDING/IN_PROGRESS/DONE): done
Task{id=1001, title='Berke', priority=HIGH, status=DONE, createdAt=2025-12-02 12:32:12, completedAt=2025-12-03 12:48:21}
```
## ❌ Delete Task
```
Choice:
5
Task ID to delete: 1001
Deleted: 1001
```
## 🔍 Search by Title
```
Choice:
6
Keyword: groceries
Task{id=1001, title='Buy groceries', priority=HIGH, status=PENDING, createdAt=2025-12-02 12:32:12, completedAt=2025-12-03 12:48:21}
```
## 🕒 List Pending Tasks
```
Task{id=1002, title='Pay bills', priority=MEDIUM, status=PENDING, createdAt=2025-12-02 19:57:21, completedAt=-}
```
## 📅 Sort by Created Date
```
Task{id=1001, title='Buy groceries', priority=HIGH, status=DONE, createdAt=2025-12-02 12:32:12, completedAt=2025-12-03 12:48:21}
Task{id=1002, title='Pay bills', priority=MEDIUM, status=PENDING, createdAt=2025-12-03 19:57:21, completedAt=-}
```
## ✅ Sort by Completion Date
```
Task{id=1002, title='Pay bills', priority=MEDIUM, status=DONE, createdAt=2025-12-02 19:57:21, completedAt=2025-12-04 14:23:45}
Task{id=1001, title='Buy groceries', priority=HIGH, status=DONE, createdAt=2025-12-03 19:54:17, completedAt=2025-12-04 12:48:21}
```
## 🚀 Features
- Add, update, delete tasks
- Change status (Pending, In Progress, Done)
- Search and sort tasks
- Robust input parsing with alias support
- JUnit 5 test coverage

---

## ▶️ Run the App
```bash
mvn compile exec:java
```

---
### 🧪Run Tests
```
## Run Test
mvn test
```
## 📦 Requirements
- Java 25+
- Maven 3.8+

## 🤝 Contributing
Pull requests welcome. For major changes, please open an issue first to discuss what you’d like to change.

## 📄 License
This project is licensed under the MIT License — see the LICENSE file for details.

## 👨‍💻 Autor
Berke — Hungry for transformation, ruthless about quality.






