package com.hfad.bucket_list_tracker_app_2

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

// on below line we are specifying our table name
@Entity(tableName = "tasks")
//tables.

//Parcelization is a mechanism that can make complex data
// object into a simple fragment that can be
// easily transfer between activity or fragment.
@Parcelize

// on below line we are specifying our column info
// and inside that we are passing our column name
data class Task(
    //columns.
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val taskTitle: String,
    val taskContent: String,
): Parcelable
