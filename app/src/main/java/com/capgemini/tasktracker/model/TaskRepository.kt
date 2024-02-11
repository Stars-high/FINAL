package com.capgemini.tasktracker.model

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class TaskRepository(application: Application) {
    private val taskTrackerDao: TaskTrackerDAO
//    val allTasks: LiveData<List<Task>>
    init{
        val db: TaskTrackerDB = TaskTrackerDB.getInstance(application.applicationContext)
        taskTrackerDao = db.taskTrackerDAO()
//        allTasks = taskTrackerDao.getAllTasks()
    }
    fun insertUser(user: User):Boolean{
        var isAdded=false
        try {
            taskTrackerDao.insertUser(user)
            isAdded=true
        }catch (err:Exception){
        isAdded=false}

        return isAdded
    }
    fun isTaken(username: String): Boolean{
        return taskTrackerDao.isTaken(username)
    }
    fun getUsername(username: String,pwd: String): String{
        return taskTrackerDao.getUsername(username, pwd)
    }
    fun getPassword(username: String, pwd: String): String{
        return taskTrackerDao.getPassword(username, pwd)
    }

    suspend fun insertTask(task: Task){
        taskTrackerDao.insertTask(task)
    }

    fun getTasks(): LiveData<List<Task>> {

        return taskTrackerDao.getAllTasks()

    }



    suspend fun deleteTask(task: Task){
        taskTrackerDao.deleteTask(task)}


/*    suspend fun updateTask(task: Task){
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
}