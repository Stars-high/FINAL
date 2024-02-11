package com.capgemini.tasktracker.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities=[User::class, Task::class], version = 2)
abstract class TaskTrackerDB : RoomDatabase(){
    abstract fun taskTrackerDAO(): TaskTrackerDAO

    companion object{
        private var INSTANCE: TaskTrackerDB?=null
        fun getInstance(context: Context): TaskTrackerDB{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TaskTrackerDB::class.java, "task_tracker_db").fallbackToDestructiveMigration().allowMainThreadQueries().build()
                INSTANCE = instance
                instance
            }
        }
    }
}