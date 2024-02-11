package com.capgemini.tasktracker.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.capgemini.tasktracker.model.Task
import com.capgemini.tasktracker.model.TaskRepository
import com.capgemini.tasktracker.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskViewModel(application: Application): AndroidViewModel(application) {
    //owns repository
    private val repo = TaskRepository(application)

    // var taskList: MutableLiveData<List<Task>> = repo.allTasks as MutableLiveData<List<Task>>
    var isUserAdded = MutableLiveData<Boolean>(false)
    var taskList = repo.getTasks()



    fun insertUser(user: User) {
        CoroutineScope(Dispatchers.IO).launch {
            isUserAdded.postValue(repo.insertUser(user))
        }
    }

    fun isTaken(username: String): Boolean {
        return repo.isTaken(username)
    }

    fun getUsername(username: String, pwd: String): String {
        return repo.getUsername(username, pwd)
    }

    fun getPassword(username: String, pwd: String): String {
        return repo.getPassword(username, pwd)
    }

    fun insertTask(task: Task) {
        CoroutineScope(Dispatchers.IO).launch {
            repo.insertTask(task)
        }
    }

    fun deleteTask(position: Int) {

        val taskToDelete = taskList.value!!.get(position)
        viewModelScope.launch(Dispatchers.Default) {
           repo.deleteTask(taskToDelete)
        }
    }



    var highPriorityTasks: LiveData<List<Task>> = repo.highPriorityTasks()




}

/*fun searchTask(query: String): LiveData<List<Task>>{
        return repo.searchTask(query)
    }

 fun updateTask(task: Task){
        CoroutineScope(Dispatchers.IO).launch{
            repo.updateTask(task)
        }
    }

        }
    }
    fun searchTask(query: String): LiveData<List<Task>>{
        return repo.searchTask(query)
    }
    fun getAllTasks(): MutableLiveData<List<Task>>{
        return taskList
    }*/

/*object DataObject {
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

 */
