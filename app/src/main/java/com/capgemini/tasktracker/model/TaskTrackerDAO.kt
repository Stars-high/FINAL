package com.capgemini.tasktracker.model

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TaskTrackerDAO {
    @Insert
    fun insertUser(user: User)

    @Query("SELECT EXISTS(SELECT * FROM User WHERE username = :username)")
    fun isTaken(username: String): Boolean

    @Query("SELECT EXISTS(SELECT * FROM Task WHERE task_name=:taskName)")
    fun isAvailable(taskName:String):Boolean

    @Query("Select username from User where username = :username and user_password = :pwd")
    fun getUsername(username: String, pwd: String): String

    @Query("Select user_password from User where username = :username and user_password = :pwd")
    fun getPassword(username: String, pwd: String): String

    @Insert
    fun insertTask(task: Task)
    //@Query("select * from task")
    //fun getAllTasks(): LiveData<List<Task>>

    @Query("SELECT * FROM Task where username = :username")
    fun getAllTasks(username: String): LiveData<MutableList<Task>>?

    @Delete
    fun deleteTask(task: Task)






    @Query("DELETE FROM Task WHERE username = :username and task_name = :taskName")
    fun deleteTaskByTName(username: String, taskName: String)

    @Query("SELECT * FROM Task WHERE task_priority= :priority")
    fun getTaskByPriority(priority: String): LiveData<List<Task>>

    @Query("SELECT * FROM Task WHERE task_name LIKE '%' || :searchQuery || '%'")
    fun searchTask(searchQuery: String?): LiveData<Task>


   /* @Query("SELECT * from  Task WHERE task_priority= 'HIGH'")
    fun getHighPriorityTasks():LiveData<List<Task>>

@Query("SELECT * FROM Task WHERE task_name LIKE :task")
    fun searchTask(searchQuery: String): LiveData<List<Task>>

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