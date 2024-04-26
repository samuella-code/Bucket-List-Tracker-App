package com.hfad.bucket_list_tracker_app_2

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update


// annotation for dao class.
@Dao
interface TaskDAO {

    // below is the insert method for
    // adding a new entry to our database.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task:Task)

    // below is the update method
    // for updating our task or goal.
    @Update
    suspend fun updateTask(task: Task)

    // below is the delete method
    // for deleting our task or goal.
    @Delete
    suspend fun deleteTask(task: Task)

    // below is the method to read all the tasks
    // from our database we have specified the query for it.
    // inside the query we are arranging it in ascending
    // order on below line and we are specifying
    // the table name from which.

    //The DESC means descending
    //Means that recently created task or goal will be at the top and
    // previously created task or goal will be at the bottom.

    @Query("SELECT * FROM TASKS ORDER BY id DESC ")
    fun getAllTask(): LiveData<List<Task>>

    //below is the method to search for task in the app
    @Query("SELECT * FROM TASKS WHERE taskTitle LIKE:query OR taskContent LIKE:query")
    fun searchTask(query: String?): LiveData<List<Task>>
}