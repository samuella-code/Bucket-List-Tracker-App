package com.hfad.bucket_list_tracker_app_2

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Query
import kotlinx.coroutines.launch

class TaskViewModel(app: Application, private val taskRepository: TaskRepository): AndroidViewModel(app) {


    // on below line we are creating a new method for adding a new task to our database
    // we are calling a method from our repository to add a new task.
    fun addTask(task: Task) =
        viewModelScope.launch {
            taskRepository.insertTask(task)
        }

    // on below line we are creating a new method for deleting a task. In this we are
    // calling a delete method from our repository to delete our task.
    fun deleteTask(task: Task) =
        viewModelScope.launch {
            taskRepository.deleteTask(task)
        }

    // on below line we are creating a new method for updating a task. In this we are
    // calling a update method from our repository to update our task.
    fun updateTask(task: Task) =
        viewModelScope.launch {
            taskRepository.updateTask(task)
        }

    // on below line we are creating a function
    // for our all tasks list and repository
    fun getAllTasks() = taskRepository.getAllTasks()

    fun searchTask(query: String?) =
        taskRepository.searchTask(query)
}