package com.capgemini.tasktracker.model

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TaskTrackerDAO {
    @Insert
    fun insertUser(user: User)

    @Query("SELECT EXISTS(SELECT * FROM User WHERE username = :username)")
    fun is_taken(username: String): Boolean

    @Query("Select * from User where username = :username and user_password = :pwd")
    fun getUser(username: String, pwd: String): User
/*
    @Insert
    fun insertTask(task: Task)
    @Query("SELECT * FROM Task ORDER BY end_date ASC")
    fun getAllTasks(): LiveData<List<Task>>

    @Update
    fun updateTask(task: Task)

    @Delete
    fun deleteTask(task: Task)

    @Query("DELETE FROM Task")
    fun deleteAllTasks()

    @Query("SELECT * FROM Task WHERE task_name LIKE :task")
    fun searchTask(searchQuery: String): LiveData<List<Task>>*/
}