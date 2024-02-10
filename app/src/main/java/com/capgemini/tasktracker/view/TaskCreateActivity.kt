package com.capgemini.tasktracker.view

import android.app.DatePickerDialog
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import com.capgemini.tasktracker.TaskListActivity
import com.capgemini.tasktracker.R
import com.google.android.material.snackbar.Snackbar
import java.util.*

class TaskCreateActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_task)


        lateinit var buttonSave: Button
        lateinit var editTextStartDate: EditText
        lateinit var editTextDueDate: EditText

        buttonSave=findViewById(R.id.buttonSave)
        editTextStartDate=findViewById(R.id.startDatetT)
        editTextDueDate=findViewById(R.id.duedateT)

        buttonSave.setOnClickListener { saveTask() }


        editTextStartDate.setOnClickListener{
            val c = Calendar.getInstance()

            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(this,
                { view: DatePicker, year:Int, monthOfYear:Int, dayOfMonth:Int ->
                    // setting date to our edit text.
//                    val sdate = (dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year)
//                    startdateEdt.setText(sdate)
                    val sDate= Calendar.getInstance()
                    sDate.set(year,monthOfYear,dayOfMonth)

                    val dateFormat= SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                    val formattedDate=dateFormat.format(sDate.time)

                    editTextStartDate.setText(formattedDate)

                },
                //passing year, month and day for the selected date in our date picker.
                year,
                month,
                day
            )
            datePickerDialog.show()
        }

        editTextDueDate.setOnClickListener {

            val c = Calendar.getInstance()

            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(this,
                { view, year, monthOfYear, dayOfMonth ->
                    //setting date to our edit text.
                    val dDate= Calendar.getInstance()
                    dDate.set(year,monthOfYear,dayOfMonth)

                    val dateFormat= SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                    val formattedDate=dateFormat.format(dDate.time)

                    editTextDueDate.setText(formattedDate)
                },
                //passing year, month and day for the selected date in our date picker.
                year,
                month,
                day

            )
            datePickerDialog.show()
        }


    }

    private fun saveTask() {

        lateinit var editTextTaskName: EditText
        lateinit var editTextStartDate: EditText
        lateinit var editTextDueDate: EditText
        lateinit var editTextDescription:EditText

        editTextTaskName = findViewById(R.id.taskNameT)
        editTextStartDate=findViewById(R.id.startDatetT)
        editTextDueDate=findViewById(R.id.duedateT)
        editTextDescription=findViewById(R.id.descT)


        val taskName = editTextTaskName.text.toString().trim()
        val startDate = editTextStartDate.text.toString().trim()
        val dueDate = editTextDueDate.text.toString().trim()
        val description = editTextDescription.text.toString().trim()

        if (validateInput(taskName, startDate, dueDate, description)) {
            showSnackbar("Task saved successfully")


            // you can navigate
startActivity(Intent(this, TaskListActivity::class.java))
        }
    }

    private fun validateInput(taskName: String, startDate: String, dueDate: String, description: String): Boolean {
        return when {
            taskName.isEmpty() -> {
                showSnackbar("Task name cannot be empty")
                false
            }
            startDate.isEmpty() -> {
                showSnackbar("Start date cannot be empty")
                false
            }
            dueDate.isEmpty() -> {
                showSnackbar("Due date cannot be empty")
                false
            }
            description.isEmpty() -> {
                showSnackbar("Description cannot be empty")
                false
            }
            else -> true
        }
    }

    private fun showSnackbar(message: String) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT).show()
    }


}