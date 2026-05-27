package com.example.todoapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "todos")
data class Todo(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String = "",
    val isCompleted: Boolean = false,
    val createdAt: Long = System.currentTimeMillis(),
    val dueDate: Long? = null,
    val priority: Priority = Priority.MEDIUM
) {
    enum class Priority {
        LOW, MEDIUM, HIGH
    }
}
