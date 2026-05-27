package com.example.todoapp.ui.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todoapp.data.database.TodoDatabase
import com.example.todoapp.data.model.Todo
import com.example.todoapp.data.repository.TodoRepository
import com.example.todoapp.databinding.ActivityMainBinding
import com.example.todoapp.ui.adapter.TodoAdapter
import com.example.todoapp.ui.viewmodel.TodoViewModel
import com.example.todoapp.ui.viewmodel.TodoViewModelFactory
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var todoAdapter: TodoAdapter
    private val database by lazy { TodoDatabase.getDatabase(this) }
    private val repository by lazy { TodoRepository(database.todoDao()) }
    private val viewModel: TodoViewModel by viewModels {
        TodoViewModelFactory(repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupObservers()
        setupListeners()
    }

    private fun setupRecyclerView() {
        todoAdapter = TodoAdapter(
            onToggle = { todo -> viewModel.toggleTodoCompletion(todo) },
            onDelete = { todo -> showDeleteConfirmation(todo) },
            onEdit = { todo -> showEditDialog(todo) }
        )
        binding.todoRecyclerView.apply {
            adapter = todoAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

    private fun setupObservers() {
        viewModel.allTodos.observe(this) { todos ->
            todoAdapter.submitList(todos)
            binding.emptyState.visibility = if (todos.isEmpty()) android.view.View.VISIBLE else android.view.View.GONE
        }

        viewModel.activeTodoCount.observe(this) { count ->
            binding.todoCountText.text = "Active Tasks: $count"
        }
    }

    private fun setupListeners() {
        binding.addTodoButton.setOnClickListener {
            showAddTodoDialog()
        }

        binding.clearCompletedButton.setOnClickListener {
            viewModel.clearCompletedTodos()
            Snackbar.make(binding.root, "Completed tasks cleared", Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun showAddTodoDialog() {
        val view = layoutInflater.inflate(com.example.todoapp.R.layout.dialog_add_todo, null)
        MaterialAlertDialogBuilder(this)
            .setTitle("Add New Todo")
            .setView(view)
            .setPositiveButton("Add") { _, _ ->
                val titleInput = view.findViewById<com.google.android.material.textfield.TextInputEditText>(
                    com.example.todoapp.R.id.titleInput
                )
                val title = titleInput.text.toString().trim()
                if (title.isNotEmpty()) {
                    viewModel.addTodo(title)
                    Snackbar.make(binding.root, "Todo added", Snackbar.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showEditDialog(todo: Todo) {
        val view = layoutInflater.inflate(com.example.todoapp.R.layout.dialog_add_todo, null)
        val titleInput = view.findViewById<com.google.android.material.textfield.TextInputEditText>(
            com.example.todoapp.R.id.titleInput
        )
        titleInput.setText(todo.title)

        MaterialAlertDialogBuilder(this)
            .setTitle("Edit Todo")
            .setView(view)
            .setPositiveButton("Update") { _, _ ->
                val newTitle = titleInput.text.toString().trim()
                if (newTitle.isNotEmpty()) {
                    viewModel.updateTodo(todo.copy(title = newTitle))
                    Snackbar.make(binding.root, "Todo updated", Snackbar.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showDeleteConfirmation(todo: Todo) {
        MaterialAlertDialogBuilder(this)
            .setTitle("Delete Todo")
            .setMessage("Are you sure you want to delete this todo?")
            .setPositiveButton("Delete") { _, _ ->
                viewModel.deleteTodo(todo)
                Snackbar.make(binding.root, "Todo deleted", Snackbar.LENGTH_SHORT).show()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}
