package com.example.todoapp.data.repository

import com.example.todoapp.data.database.TodoDao
import com.example.todoapp.data.model.Todo
import kotlinx.coroutines.flow.Flow

class TodoRepository(private val todoDao: TodoDao) {
    fun getAllTodos(): Flow<List<Todo>> = todoDao.getAllTodos()

    fun getActiveTodos(): Flow<List<Todo>> = todoDao.getActiveTodos()

    fun getCompletedTodos(): Flow<List<Todo>> = todoDao.getCompletedTodos()

    fun getActiveTodoCount(): Flow<Int> = todoDao.getActiveTodoCount()

    suspend fun getTodoById(id: Int): Todo? = todoDao.getTodoById(id)

    suspend fun insertTodo(todo: Todo): Long = todoDao.insertTodo(todo)

    suspend fun updateTodo(todo: Todo) = todoDao.updateTodo(todo)

    suspend fun deleteTodo(todo: Todo) = todoDao.deleteTodo(todo)

    suspend fun clearCompletedTodos() = todoDao.clearCompletedTodos()
}
