package com.capgemini.tasktracker

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
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
    lateinit var adapter:TaskAdapter

    var PREFS_KEY = "prefs"
    var UNAME_KEY = "uname"
    var uname = ""

    lateinit var sharedPreferences: SharedPreferences
    lateinit var taskVM: TaskViewModel

    val taskList = mutableListOf<Task>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_list)
        taskVM = ViewModelProvider(this)[TaskViewModel::class.java]

        sharedPreferences = getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE)
        uname = sharedPreferences.getString(UNAME_KEY, null)!!

        addButton=findViewById(R.id.addB)
        rView = findViewById(R.id.rView)

        rView.layoutManager = LinearLayoutManager(this)
        rView.adapter = TaskAdapter(taskList)

        val touchHelper = ItemTouchHelper(MyTouchHelper())
        touchHelper.attachToRecyclerView(rView)


        taskVM.taskList.observe(this){
            // executed as and when data is changed
            // and activity is in active state
            Log.d("MainActivity", "list observer called")
            rView.adapter = TaskAdapter(it)
        }



        addButton.setOnClickListener{
           startActivity(Intent(this,TaskCreateActivity::class.java))
        }


    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.filterandsort_menu, menu)
        val searchV = menu?.findItem(R.id.app_bar_search)?.actionView as SearchView
        val myTextListener = object : SearchView.OnQueryTextListener {


            override fun onQueryTextSubmit(query: String?): Boolean {

                Log.d("MainActivity", "Search:$query")
                val filteredList = taskList.filter {
                    it.taskName.startsWith(query ?: "Not Found", true)
                }
                if (taskList.isNotEmpty()) {
                    rView.adapter = TaskAdapter(filteredList )


                }
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                Log.d("MainActivity", "Search:$query")
                val filteredList = taskList.filter {
                    it.taskName.startsWith(query ?: "Not Found", true)
                }
                if (taskList.isNotEmpty()) {
                    rView.adapter = TaskAdapter(filteredList )
                }
                return true
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
           /* R.id.date_item -> {
                //sort by date
                taskList.sortBy { it.endDate }
                rView.adapter?.notifyDataSetChanged()
            }

            */
            R.id.priority_item_high->{
               /* val filteredList = taskList.filter {
                    it.priority=="high"
                }
                */


                if (taskList.isNotEmpty()) {
                    taskVM.highPriorityTasks
                        .observe(this,{ highPriorityTasks->rView.adapter = TaskAdapter(taskList )})

                }
            }
            R.id.priority_item_low->{
                val filteredList = taskList.filter {
                    it.priority=="low"
                }
                if (taskList.isNotEmpty()) {
                    rView.adapter = TaskAdapter(filteredList)
                }
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

                taskVM.deleteTask(viewHolder.adapterPosition)
               // rView.adapter?.notifyItemRemoved(viewHolder.adapterPosition)
                Snackbar.make(rView, "Task deleted", Snackbar.LENGTH_LONG).show()
            }
        }
    }
}