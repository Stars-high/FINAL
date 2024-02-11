package com.capgemini.tasktracker.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.capgemini.tasktracker.R
import com.capgemini.tasktracker.TaskListActivity
//import com.capgemini.tasktracker.viewmodel.DataObject

class TaskUpdateActivity : AppCompatActivity() {
/*    lateinit var titleText:TextView
    lateinit var priorityText: TextView
    lateinit var descText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_update)

        titleText = findViewById(R.id.create_title)
        priorityText = findViewById(R.id.create_priority)
        descText = findViewById(R.id.create_desc)

        val pos=intent.getIntExtra("id",-1)
        if(pos!=-1){
            val title= DataObject.getData(pos).taskName
            val priority=DataObject.getData(pos).priority
            val desc=DataObject.getData(pos).description

            delete_button.setOnClickListener {
                DataObject.deleteData(pos)
                myIntent()
            }
            update_button.setOnClickListener {
                DataObject.updateData(pos,create_title, create_priority, create_desc)
                Toast.makeText(this,title, Toast.LENGTH_LONG).show()
                myIntent()
            }
        }
    }
    fun myIntent(){
        val intent= Intent(this, TaskListActivity::class.java)
        startActivity(intent)
    }
 */
}