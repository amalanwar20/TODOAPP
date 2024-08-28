package com.example.todoapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class TodoListFragment : Fragment() {

    private lateinit var todoAdapter: TodoAdapter
    private val todoList = mutableListOf<TodoItem>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_todo_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        todoAdapter = TodoAdapter(todoList)
        recyclerView.adapter = todoAdapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        val fab: FloatingActionButton = view.findViewById(R.id.fab)
        fab.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("New Task")

            val input = EditText(requireContext())
            input.hint = "Enter task"
            builder.setView(input)

            builder.setPositiveButton("Add") { _, _ ->
                val task = input.text.toString().trim()
                if (task.isNotEmpty()) {
                    todoAdapter.addItem(task)
                } else {
                    Toast.makeText(requireContext(), "Task cannot be empty", Toast.LENGTH_SHORT).show()
                }
            }
            builder.setNegativeButton("Cancel", null)

            builder.show()
        }
    }
}

