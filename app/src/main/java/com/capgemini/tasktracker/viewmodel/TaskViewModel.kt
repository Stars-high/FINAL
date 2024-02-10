package com.capgemini.tasktracker.viewmodel

import android.app.Application
import android.app.DownloadManager.Query
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.capgemini.tasktracker.model.Task
import com.capgemini.tasktracker.model.TaskRepository
import com.capgemini.tasktracker.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskViewModel(application: Application): AndroidViewModel(application) {
    //owns repository
    private val repo =TaskRepository(application)
 // var taskList: MutableLiveData<List<Task>> = repo.allTasks as MutableLiveData<List<Task>>
    var isUserAdded= MutableLiveData<Boolean>(false)

    fun insertUser(user: User){
        CoroutineScope(Dispatchers.IO).launch{
          isUserAdded.postValue(repo.insertUser(user))
        }
    }
    fun is_taken(username: String): Boolean{
        if(repo.is_taken(username))
            return true
        else
            return false
    }
    fun checkCredential(username: String, pwd: String): User{
        return repo.getUser(username, pwd)
    }

/*    fun insertTask(task: Task){
        CoroutineScope(Dispatchers.IO).launch{
            repo.insertTask(task)
        }
    }
    fun updateTask(task: Task){
        CoroutineScope(Dispatchers.IO).launch{
            repo.updateTask(task)
        }
    }
    fun deleteTask(task: Task){
        CoroutineScope(Dispatchers.IO).launch{
            repo.deleteTask(task)
        }
    }
    fun searchTask(query: String): LiveData<List<Task>>{
        return repo.searchTask(query)
    }
    fun getAllTasks(): MutableLiveData<List<Task>>{
        return taskList
    }*/
}
object DataObject {
    var listData= mutableListOf<Task>()

    fun getData(pos:Int):Task{
        return listData[pos]
    }
    fun deleteData(pos: Int){
        listData.removeAt(pos)
    }
    fun updateData(pos: Int,task:String,priority:String,desc:String){
        listData[pos].taskName=task
        listData[pos].priority=priority
        listData[pos].description=desc
    }
}