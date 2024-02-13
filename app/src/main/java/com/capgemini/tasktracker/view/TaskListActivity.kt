package com.capgemini.tasktracker


import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.capgemini.tasktracker.view.TaskCreateActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.capgemini.tasktracker.model.Task
import com.capgemini.tasktracker.view.MainActivity
import com.capgemini.tasktracker.viewmodel.TaskViewModel
import com.google.android.material.snackbar.Snackbar

class TaskListActivity : AppCompatActivity() {
    lateinit var rView: RecyclerView
    lateinit var addButton: FloatingActionButton
    lateinit var txtEmptyList: TextView

    var PREFS_KEY = "prefs"
    var UNAME_KEY = "uname"
    var uname = ""

    lateinit var sharedPreferences: SharedPreferences
    lateinit var adapter:TaskAdapter
    lateinit var taskVM: TaskViewModel

    var taskList: MutableList<Task>? = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_list)

        addButton=findViewById(R.id.addB)
        rView = findViewById(R.id.rView)

        rView.layoutManager = LinearLayoutManager(this)
        adapter = TaskAdapter(taskList)
        rView.adapter = adapter

        sharedPreferences = getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE)
        uname = sharedPreferences.getString(UNAME_KEY, null)!!

        //initialize the viewModel
        taskVM = ViewModelProvider(this)[TaskViewModel::class.java]
        if(taskVM.getAllTasks(uname)==null){
            Log.d("taskList","No tasks added: Add your tasks and keep track of them")
            txtEmptyList.setVisibility(View.VISIBLE)
        }
        else{
            taskVM.getAllTasks(uname)?.observe(this, Observer { t ->
                adapter.setTask(t)
                rView.adapter = adapter
            })
            Log.d("taskList","${adapter.itemCount}")
        }

        addButton.setOnClickListener{
            startActivity(Intent(this,TaskCreateActivity::class.java))
        }
        val touchHelper = ItemTouchHelper(MyTouchHelper())
        touchHelper.attachToRecyclerView(rView)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.filterandsort_menu, menu)
        val searchV = menu?.findItem(R.id.app_bar_search)?.actionView as SearchView
        val myTextListener = object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return searchTask(query)
            }
            override fun onQueryTextChange(query: String?): Boolean {
                return searchTask(query)
            }
        }
        searchV.setOnQueryTextListener(myTextListener)
        searchV.setOnCloseListener {
            rView.adapter =TaskAdapter(taskList)
            false
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

            R.id.priority_item_high->{
                filterByPriority("HIGH")
            }
            R.id.priority_item_low->{
                filterByPriority("LOW")
            }
            R.id.logout -> {
                val editor: SharedPreferences.Editor = sharedPreferences.edit()
                editor.clear()
                editor.apply()
                val i = Intent(this, MainActivity::class.java)
                startActivity(i)
            }
        }
        return super.onOptionsItemSelected(item)
    }
    private fun filterByPriority(priority: String){
        taskVM.getTaskByPriority(priority).observe(this) { t ->
            adapter.setTask(t as MutableList<Task>)
            rView.adapter = adapter
        }
    }
    inner class MyTouchHelper :
        ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return false

        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            if (direction == ItemTouchHelper.RIGHT) {
                /*val taskToDelete = taskList?.get(viewHolder.adapterPosition-1)
                Log.d("taskList", "$taskToDelete")
                var taskName = taskToDelete?.taskName
                if (taskName != null) {
                    taskVM.deleteTask(uname, taskName)
                }*/

                taskVM.deleteTask(viewHolder.adapterPosition)

                rView.adapter?.notifyItemRemoved(viewHolder.adapterPosition)
                Snackbar.make(rView, "Task deleted", Snackbar.LENGTH_LONG).show()
            }
        }
    }
    private fun searchTask(query: String?): Boolean{
        var searchedTask = mutableListOf<Task>()
        taskVM.searchTask(query).observe(this@TaskListActivity) { t ->
            if(t!=null) searchedTask.add(t)
            if(searchedTask.isNotEmpty()) {
                adapter.setTask(searchedTask)
                rView.adapter = adapter
                return@observe
            }
            else{
                return@observe
            }
        }
        return true
    }
}