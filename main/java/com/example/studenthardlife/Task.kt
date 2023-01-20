package com.example.studenthardlife

data class Task(val taskName: String, val courseName: String, val taskDescription: String) {
    var id: Int = 0

    constructor(id: Int, taskName: String, courseName: String, taskDescription: String) : this(taskName, courseName, taskDescription) {
        this.id = id
    }

}