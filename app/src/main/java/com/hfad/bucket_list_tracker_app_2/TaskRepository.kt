package com.hfad.bucket_list_tracker_app_2

import androidx.room.Query

//Repository means a container where something is contained or stored.
class TaskRepository(private val db: TaskDatabase) {

    // on below line we are creating an insert method
    // for adding the task to our database.
    suspend fun insertTask(task: Task) = db.getTaskDao().insertTask(task)

    // on below line we are creating a delete method
    // for deleting our task from database.
    suspend fun deleteTask(task: Task) = db.getTaskDao().deleteTask(task)

    // on below line we are creating a update method for
    // updating our note from database.
    suspend fun updateTask(task: Task) = db.getTaskDao().updateTask(task)

    // and we are getting all the tasks from our DAO class.
    fun getAllTasks() = db.getTaskDao().getAllTask()

    //and we are getting all the search task from our DAO class.
    fun searchTask(query: String?) = db.getTaskDao().searchTask(query)
}