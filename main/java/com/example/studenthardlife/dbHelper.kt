package com.example.studenthardlife

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHandler(context: Context) : SQLiteOpenHelper (
    context, DATABASE_NAME, null, DATABASE_VERSION
) {
    private companion object{
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "tasksDBKotlin.db"
        private const val TABLE_TASKS = "TasksTable"

        private const val COLUMN_ID = "_id"
        private const val COLUMN_TASKNAME = "taskName"
        private const val COLUMN_COURSENAME = "courseName"
        private const val COLUMN_TASKDESCRIPTION = "taskDescription"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTasksTable =
            "CREATE TABLE $TABLE_TASKS(" +
                    "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    "$COLUMN_TASKNAME TEXT," +
                    "$COLUMN_COURSENAME TEXT," +
                    "$COLUMN_TASKDESCRIPTION TEXT)"
        db?.execSQL(createTasksTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_TASKS")
        onCreate(db)
    }

    fun addTask(task: Task){
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(COLUMN_TASKNAME, task.taskName)
        contentValues.put(COLUMN_COURSENAME, task.courseName)
        contentValues.put(COLUMN_TASKDESCRIPTION, task.taskDescription)

        db.insert(TABLE_TASKS, null, contentValues)
        db.close()
    }

    fun deleteTask(task: Task){
        val db = this.writableDatabase
        db.delete(
            TABLE_TASKS,
            "$COLUMN_ID=${task.id}",
            null)
        db.close()
    }

    fun updateTask (id: Int, taskName: String, courseName: String, taskDescription: String){
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(COLUMN_TASKNAME, taskName)
        contentValues.put(COLUMN_COURSENAME, courseName)
        contentValues.put(COLUMN_TASKDESCRIPTION, taskDescription)

        db.update(
            TABLE_TASKS,
            contentValues,
            "$COLUMN_ID=$id",
            null)

        db.close()
    }

    fun getTasks(): List<Task> {
        val tasks: MutableList<Task> = ArrayList()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_TASKS", null)

        if (cursor.moveToFirst()) {
            do {
                tasks.add(
                    Task(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),))
            } while (cursor.moveToNext())
        }

        db.close()
        cursor.close()
        return tasks
    }
}