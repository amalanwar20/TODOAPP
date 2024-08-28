package com.example.todoapp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

data class TodoItem(val task: String)

class TodoAdapter(private val items: MutableList<TodoItem>) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val todoText: TextView = itemView.findViewById(R.id.todoText)
        val editButton: Button = itemView.findViewById(R.id.editButton)
        val deleteButton: Button = itemView.findViewById(R.id.deleteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.todo_item, parent, false)
        return TodoViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val item = items[position]
        holder.todoText.text = item.task

        holder.editButton.setOnClickListener {
            // Handle edit action
            showEditDialog(holder.adapterPosition, holder.itemView.context)
        }

        holder.deleteButton.setOnClickListener {
            // Handle delete action
            items.removeAt(holder.adapterPosition)
            notifyItemRemoved(holder.adapterPosition)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun addItem(task: String) {
        items.add(TodoItem(task))
        notifyItemInserted(items.size - 1)
    }

    private fun showEditDialog(position: Int, context: Context) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Edit Task")

        val input = EditText(context)
        input.setText(items[position].task)
        builder.setView(input)

        builder.setPositiveButton("Save") { _, _ ->
            val newTask = input.text.toString()
            items[position] = TodoItem(newTask)
            notifyItemChanged(position)
        }
        builder.setNegativeButton("Cancel", null)

        builder.show()
    }
}



class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Load the fragment when the activity starts
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, TodoListFragment())
                .commit()
        }
    }
}
