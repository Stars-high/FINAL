package com.capgemini.tasktracker.view

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.capgemini.tasktracker.R
import com.capgemini.tasktracker.TaskListActivity

class TaskDisplayActivity : AppCompatActivity() {

    lateinit var crossButton: Button
    lateinit var updateButton: Button
    lateinit var taskTitle: TextView
    lateinit var startDateTv:TextView
    lateinit var endDateTv:TextView
    lateinit var desc: TextView


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_display)

        taskTitle = findViewById(R.id.create_title)
        startDateTv=findViewById(R.id.startDateTv)
        endDateTv=findViewById(R.id.endDateTv)
        desc=findViewById(R.id.descTv)
        crossButton=findViewById(R.id.crossB)
        //updateButton=findViewById(R.id.updateB)


        val pos = intent.getIntExtra("id", -1)
        val taskName = intent.getStringExtra("taskName")
        val description = intent.getStringExtra("description")
        val startDate=intent.getStringExtra("startDate")
        val endDate=intent.getStringExtra("endDate")

        taskTitle.text = taskName
        desc.text = description
        startDateTv.text=startDate
        endDateTv.text=endDate

        crossButton.setOnClickListener {
            myIntent()
     }

        }
        fun myIntent() {
            val intent = Intent(this, TaskListActivity::class.java)
            startActivity(intent)
        }
    }


