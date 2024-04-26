package com.hfad.bucket_list_tracker_app_2

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Task::class], version = 1)

//class is abstract because there is a abstract function in it
abstract class TaskDatabase: RoomDatabase() {

    abstract fun getTaskDao(): TaskDAO

    companion object{

        // Singleton prevents multiple
        // instances of database opening at the
        // same time.
        @Volatile
        private var instance: TaskDatabase? = null

        //LOCK object is used to synchronize the database creation process.
        private val LOCK = Any()

        // if the INSTANCE is not null, then return it,
        // if it is, then create the database
        //invoke function is used for simplicity when creating a instance.
        operator fun invoke(context: Context) = instance ?:
        synchronized(LOCK){
            instance ?:
            createDatabase(context).also{
                instance = it
            }
        }

        //createDatabase function is used to create the
        // TaskDatabase instance using databaseBuilder method and it also
        // specify the database name which is task_db and then finally build it.
        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                TaskDatabase::class.java,
                "task_db"
            ).build()
    }
}