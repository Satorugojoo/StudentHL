package com.example.studenthardlife

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView

class TaskAdapter(private val dbHandler: DBHandler) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {
    class TaskViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
            val taskImage : ImageView = itemView.findViewById(R.id.taskImageView)
            private val taskName : TextView = itemView.findViewById(R.id.taskNameText)
            private val courseName : TextView = itemView.findViewById(R.id.courseNameText)
            fun bind(item: Task){
                taskName.text = item.taskName
                courseName.text = item.courseName
                itemView.setOnClickListener {
                    val action = TaskOverviewFragmentDirections.actionTaskOverviewFragmentToTaskDetailsFragment(taskId = item.id.toString())
                    Navigation.findNavController(itemView).navigate(action)
                }
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.task_element, parent, false)
        return TaskViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val item = dbHandler.getTasks()[position]
        holder.bind(item)
    }

    override fun getItemCount() = dbHandler.getTasks().size
}