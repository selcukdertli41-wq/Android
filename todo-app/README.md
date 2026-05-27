# Todo App - Local Storage

A modern Android todo list application with local storage functionality built with Kotlin and Room Database.

## Features

✅ **Create Todos** - Add new tasks with title and description  
✅ **Local Storage** - Persistent data storage using Room Database  
✅ **Mark Complete** - Check off completed tasks  
✅ **Edit Todos** - Modify existing tasks  
✅ **Delete Todos** - Remove tasks individually or clear all completed  
✅ **Priority Levels** - Assign priority (Low, Medium, High) to tasks  
✅ **Task Statistics** - View active task count  
✅ **Clean UI** - Material Design 3 interface  

## Architecture

This app follows the **MVVM (Model-View-ViewModel)** architecture pattern:

```
┌─────────────────────────────────────────────────┐
│               UI Layer (Activity)               │
│            (MainActivity.kt)                    │
└────────────────────┬────────────────────────────┘
                     │
┌────────────────────▼────────────────────────────┐
│           ViewModel Layer                       │
│        (TodoViewModel.kt)                       │
│        - Manages UI state                       │
│        - Handles user interactions              │
└────────────────────┬────────────────────────────┘
                     │
┌────────────────────▼────────────────────────────┐
│          Repository Layer                       │
│       (TodoRepository.kt)                       │
│       - Abstraction over data sources           │
└────────────────────┬────────────────────────────┘
                     │
┌────────────────────▼────────────────────────────┐
│         Data Layer (Room Database)              │
│    (TodoDatabase.kt, TodoDao.kt)                │
│    - Local persistence with SQLite              │
└─────────────────────────────────────────────────┘
```

## Project Structure

```
todo-app/
├── src/main/
│   ├── java/com/example/todoapp/
│   │   ├── data/
│   │   │   ├── database/
│   │   │   │   ├── TodoDatabase.kt      # Room Database setup
│   │   │   │   └── TodoDao.kt           # Database queries
│   │   │   ├── model/
│   │   │   │   └── Todo.kt              # Data model
│   │   │   └── repository/
│   │   │       └── TodoRepository.kt    # Data abstraction
│   │   └── ui/
│   │       ├── activity/
│   │       │   └── MainActivity.kt      # Main screen
│   │       ├── adapter/
│   │       │   └── TodoAdapter.kt       # RecyclerView adapter
│   │       └── viewmodel/
│   │           └── TodoViewModel.kt     # Business logic
│   └── res/
│       ├── layout/
│       │   ├── activity_main.xml        # Main layout
│       │   ├── item_todo.xml            # Todo item layout
│       │   └── dialog_add_todo.xml      # Add/Edit dialog
│       └── drawable/
│           └── card_background.xml      # Card styling
└── build.gradle.kts                     # Dependencies
```

## Technologies Used

- **Language:** Kotlin
- **Database:** Room (SQLite)
- **Architecture:** MVVM
- **Concurrency:** Coroutines & Flow
- **UI Framework:** AndroidX, Material Design 3
- **RecyclerView:** For efficient list display

## Dependencies

```gradle
// Room Database
implementation("androidx.room:room-runtime:2.6.1")
kapt("androidx.room:room-compiler:2.6.1")
implementation("androidx.room:room-ktx:2.6.1")

// Jetpack
implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")

// Material Design
implementation("com.google.android.material:material:1.11.0")
```

## How to Use

1. **Add a Todo:** Tap the "Add Todo" button, enter the title, and confirm
2. **Complete a Todo:** Check the checkbox next to the todo item
3. **Edit a Todo:** Tap on a todo or the "Edit" button to modify it
4. **Delete a Todo:** Tap the "Delete" button on a specific todo
5. **Clear Completed:** Use "Clear Completed" button to remove all finished tasks

## Database Schema

### Todos Table

| Column | Type | Description |
|--------|------|-------------|
| id | INT | Primary Key (Auto-increment) |
| title | TEXT | Todo title |
| description | TEXT | Todo description |
| isCompleted | BOOLEAN | Completion status |
| createdAt | LONG | Creation timestamp |
| dueDate | LONG | Optional due date |
| priority | TEXT | Priority level (LOW, MEDIUM, HIGH) |

## Coroutines & Flow

- **Flow:** Used for reactive data streams (getAllTodos, getActiveTodos)
- **Coroutines:** Suspend functions for database operations to avoid blocking the UI thread
- **LiveData:** Bridges coroutines and UI lifecycle

## Future Enhancements

- 📅 Due date picker
- 🏷️ Categories/Tags support
- 🔔 Notifications for due tasks
- 📊 Statistics and charts
- ☁️ Cloud sync (Firebase)
- 🎨 Theme customization
- 🔐 Task encryption

## Getting Started

1. Clone the repository
2. Open in Android Studio
3. Build and run on emulator or device
4. Start creating todos!

## License

MIT License - Feel free to use this project as a learning resource or template.
