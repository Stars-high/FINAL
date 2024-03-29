package com.capgemini.tasktracker.model

import android.app.Application
import androidx.lifecycle.LiveData

class TaskRepository(application: Application) {
    private val taskTrackerDao: TaskTrackerDAO

    //    val allTasks: LiveData<List<Task>>
    init {
        val db: TaskTrackerDB = TaskTrackerDB.getInstance(application.applicationContext)
        taskTrackerDao = db.taskTrackerDAO()
//        allTasks = taskTrackerDao.getAllTasks()
    }


    fun getAllTasks(username: String): LiveData<MutableList<Task>>? {
        return taskTrackerDao.getAllTasks(username)
    }

    fun insertUser(user: User): Boolean {
        var isAdded =false
        try {
            taskTrackerDao.insertUser(user)
            isAdded = true
        } catch (err: Exception) {
            isAdded = false
        }

        return isAdded
    }

    fun isTaken(username: String): Boolean {
        return taskTrackerDao.isTaken(username)
    }
    fun isAvailable(taskName:String):Boolean{
        return taskTrackerDao.isAvailable(taskName)
    }

    fun getUsername(username: String, pwd: String): String {
        return taskTrackerDao.getUsername(username, pwd)
    }

    fun getPassword(username: String, pwd: String): String {
        return taskTrackerDao.getPassword(username, pwd)
    }

    suspend fun insertTask(task: Task) {
        taskTrackerDao.insertTask(task)
    }

    //fun getTasks(): LiveData<List<Task>> {

      //  return taskTrackerDao.getAllTasks()

    //}


 /*   suspend fun deleteTask(task: Task) {
        taskTrackerDao.deleteTask(task)
    }

  */

    fun deleteTaskByTName(username: String, taskName: String){
        taskTrackerDao.deleteTaskByTName(username, taskName)
    }

    fun deleteTask(task: Task){
        taskTrackerDao.deleteTask(task)
    }

    fun getTaskByPriority(priority: String): LiveData<List<Task>>{
        return taskTrackerDao.getTaskByPriority(priority)
    }
    fun searchTask(query: String?): LiveData<Task>{
        return taskTrackerDao.searchTask(query)
    }


   /* fun highPriorityTasks():LiveData<List<Task>>
    {
       return taskTrackerDao.getHighPriorityTasks()
    }

    */




}




/*   fun searchTask(query: String): LiveData<List<Task>> {
        return taskTrackerDao.searchTask("%query%")
    }


 suspend fun updateTask(task: Task){
        taskTrackerDao.updateTask(task)
    }
    suspend fun deleteTask(task: Task){
        taskTrackerDao.deleteTask(task)
    }
    fun searchTask(query: String): LiveData<List<Task>>{
        return taskTrackerDao.searchTask("%query%")
    }
    suspend fun getAllTasks(): LiveData<List<Task>>{
        return allTasks
    }*/
