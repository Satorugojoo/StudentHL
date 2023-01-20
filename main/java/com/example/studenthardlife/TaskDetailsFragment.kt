package com.example.studenthardlife

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.studenthardlife.databinding.DialogBinding
import com.example.studenthardlife.databinding.FragmentTaskDetailsBinding

class TaskDetailsFragment : Fragment() {

    private lateinit var taskId: String
    private lateinit var dbHandler: DBHandler
    private lateinit var task: Task
    private val binding by lazy {
        FragmentTaskDetailsBinding.inflate(
            layoutInflater
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let { taskId = it.getString("taskId").toString() }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val context = this.context
        if(context != null) {
            dbHandler = DBHandler(context)

            task = dbHandler.getTasks().find { task -> task.id.toString() == taskId }!!
            binding.taskNameDetailsText.text = task.taskName
            binding.courseNameDetailsText.text = task.courseName
            binding.taskDescriptionDetailsText.text = task.taskDescription

            binding.deleteTaskButton.setOnClickListener {
                dbHandler.deleteTask(task)
                Navigation.findNavController(binding.root).navigate(R.id.action_taskDetailsFragment_to_taskOverviewFragment)
            }

            binding.editTaskButton.setOnClickListener {
                setupDialog(task)
            }

            binding.goBackButton.setOnClickListener {
                Navigation.findNavController(binding.root).navigate(R.id.action_taskDetailsFragment_to_taskOverviewFragment)
            }
        }

        return binding.root
    }

    private fun setupDialog(item: Task){
        val context = this.context
        if (context != null){
            val dialog = Dialog(context)
            val dialogBinding = DialogBinding.inflate(LayoutInflater.from(context))
            dialog.apply {
                setCancelable(false)
                setContentView(dialogBinding.root)
            }

            dialogBinding.apply {
                taskNameInput.setText(item.taskName)
                courseNameInput.setText(item.courseName)
                taskDescriptionInput.setText(item.taskDescription)
                updateButton.setOnClickListener {
                    updateDialog(dialogBinding, item, dialog)
                }

                cancelButton.setOnClickListener { dialog.dismiss() }
            }
            dialog.show()
        }
    }

    private fun updateDialog(
        dialogBinding: DialogBinding,
        item: Task,
        dialog: Dialog
    ) {
        val updateTaskName = dialogBinding.taskNameInput.text.toString()
        val updateCourseName = dialogBinding.courseNameInput.text.toString()
        val updateTaskDescription = dialogBinding.taskDescriptionInput.text.toString()

        if (updateTaskName.isNotEmpty() && updateCourseName.isNotEmpty() && updateTaskDescription.isNotEmpty()) {
            dbHandler.updateTask(item.id, updateTaskName, updateCourseName, updateTaskDescription)
            task = dbHandler.getTasks().find { task -> task.id.toString() == taskId }!!
            binding.taskNameDetailsText.text = task.taskName
            binding.courseNameDetailsText.text = task.courseName
            binding.taskDescriptionDetailsText.text = task.taskDescription
            dialog.dismiss()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }
}