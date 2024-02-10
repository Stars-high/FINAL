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
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.capgemini.tasktracker.view.TaskCreateActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.capgemini.tasktracker.model.Task
import com.capgemini.tasktracker.view.MainActivity
import com.google.android.material.snackbar.Snackbar
import java.time.LocalDate

class TaskListActivity : AppCompatActivity() {
    lateinit var rView: RecyclerView
    lateinit var addButton: FloatingActionButton
    lateinit var adapter:TaskAdapter

    var PREFS_KEY = "prefs"
    var UNAME_KEY = "uname"
    var uname = ""

    lateinit var sharedPreferences: SharedPreferences

    val taskList = mutableListOf<Task>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_list)

        sharedPreferences = getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE)
        uname = sharedPreferences.getString(UNAME_KEY, null)!!

        addButton=findViewById(R.id.addB)
        rView = findViewById(R.id.rView)
        rView.layoutManager = LinearLayoutManager(this)
        rView.adapter = TaskAdapter(taskList) {
            Toast.makeText(this, "Selected task:${taskList[it].taskName}", Toast.LENGTH_LONG).show()
        }
        addButton.setOnClickListener{
           startActivity(Intent(this,TaskCreateActivity::class.java))
        }
        val touchHelper = ItemTouchHelper(MyTouchHelper())
        touchHelper.attachToRecyclerView(rView)
        populateData()
    }

    fun populateData()
    {
        val t1 = Task("Eat", "", LocalDate.parse("2024-01-01"),
            LocalDate.parse("2024-12-01"),"high",true, "")
        taskList.add(t1)
        val t2 = Task("Play","", LocalDate.parse("2018-01-01"),
            LocalDate.parse("2018-12-01"),"low",true,"")
        taskList.add(t2)
        val t3 = Task("dance","", LocalDate.parse("2018-04-11"),
            LocalDate.parse("2018-06-11"),"low",true,"")
        taskList.add(t3)
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
                    rView.adapter = TaskAdapter(filteredList as MutableList<Task>) {

                    }
                }
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                Log.d("MainActivity", "Search:$query")
                val filteredList = taskList.filter {
                    it.taskName.startsWith(query ?: "Not Found", true)
                }
                if (taskList.isNotEmpty()) {
                    rView.adapter = TaskAdapter(filteredList as MutableList<Task>) {

                    }
                }
                return true
            }
        }
        searchV.setOnQueryTextListener(myTextListener)
        searchV.setOnCloseListener {
            rView.adapter =TaskAdapter(taskList) {

            }
            false
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.date_item -> {
                //sort by date
                taskList.sortBy { it.endDate }
                rView.adapter?.notifyDataSetChanged()
            }
            R.id.priority_item_high->{
                val filteredList = taskList.filter {
                    it.priority=="high"
                }
                if (taskList.isNotEmpty()) {
                    rView.adapter = TaskAdapter(filteredList as MutableList<Task>) {

                    }
                }
            }
            R.id.priority_item_low->{
                val filteredList = taskList.filter {
                    it.priority=="low"
                }
                if (taskList.isNotEmpty()) {
                    rView.adapter = TaskAdapter(filteredList as MutableList<Task>) {

                    }
                }
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
                taskList.removeAt(viewHolder.adapterPosition)
                rView.adapter?.notifyItemRemoved(viewHolder.adapterPosition)
                Snackbar.make(rView, "Task deleted", Snackbar.LENGTH_LONG).show()
            }
        }
    }
}