package com.example.todoapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.data.model.Todo
import com.example.todoapp.databinding.ItemTodoBinding

class TodoAdapter(
    private val onToggle: (Todo) -> Unit,
    private val onDelete: (Todo) -> Unit,
    private val onEdit: (Todo) -> Unit
) : ListAdapter<Todo, TodoAdapter.TodoViewHolder>(TodoDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val binding = ItemTodoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TodoViewHolder(binding, onToggle, onDelete, onEdit)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class TodoViewHolder(
        private val binding: ItemTodoBinding,
        private val onToggle: (Todo) -> Unit,
        private val onDelete: (Todo) -> Unit,
        private val onEdit: (Todo) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(todo: Todo) {
            binding.apply {
                todoTitle.text = todo.title
                todoDescription.text = todo.description
                todoCheckbox.isChecked = todo.isCompleted

                val priorityColor = when (todo.priority) {
                    Todo.Priority.HIGH -> "🔴"
                    Todo.Priority.MEDIUM -> "🟡"
                    Todo.Priority.LOW -> "🟢"
                }
                todoPriority.text = priorityColor

                todoCheckbox.setOnCheckedChangeListener { _, _ ->
                    onToggle(todo)
                }

                deleteButton.setOnClickListener {
                    onDelete(todo)
                }

                editButton.setOnClickListener {
                    onEdit(todo)
                }

                root.setOnClickListener {
                    onEdit(todo)
                }
            }
        }
    }

    class TodoDiffCallback : DiffUtil.ItemCallback<Todo>() {
        override fun areItemsTheSame(oldItem: Todo, newItem: Todo) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Todo, newItem: Todo) = oldItem == newItem
    }
}
