package com.hgshkt.todolistfirebase.view.fragments.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hgshkt.todolistfirebase.R
import com.hgshkt.todolistfirebase.view.model.TaskDisplay

class TaskListAdapter(
    private val list: MutableList<TaskDisplay>
): RecyclerView.Adapter<TaskListAdapter.TaskViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TaskViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.task_item_layout, parent, false)

        return TaskViewHolder(view)
    }

    override fun getItemCount() = list.count()

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.description.text = list[position].description
    }

    class TaskViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val description: TextView = view.findViewById(R.id.taskItemTextView)
    }
}