package com.capgemini.tasktracker.view

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.lifecycle.ViewModelProvider
import com.capgemini.tasktracker.TaskListActivity
import com.capgemini.tasktracker.R
import com.capgemini.tasktracker.model.Task
import com.capgemini.tasktracker.viewmodel.TaskViewModel
import com.google.android.material.snackbar.Snackbar
import java.util.*

class TaskCreateActivity : AppCompatActivity() {

    lateinit var buttonSave: Button
    lateinit var editTextStartDate: EditText
    lateinit var editTextDueDate: EditText
    lateinit var taskVM: TaskViewModel
    lateinit var priority:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_task)

        buttonSave=findViewById(R.id.buttonSave)
        editTextStartDate=findViewById(R.id.startDatetT)
        editTextDueDate=findViewById(R.id.duedateT)

        //initialize the viewModel
        taskVM = ViewModelProvider(this)[TaskViewModel::class.java]

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
        lateinit var radioGroup:RadioGroup



        lateinit var sharedPreferences: SharedPreferences
        var PREFS_KEY = "prefs"
        var UNAME_KEY = "uname"
        var uname=""

        editTextTaskName = findViewById(R.id.taskNameT)
        editTextStartDate=findViewById(R.id.startDatetT)
        editTextDueDate=findViewById(R.id.duedateT)
        editTextDescription=findViewById(R.id.descT)
        radioGroup=findViewById(R.id.radioGroup)


        /*radioGroup.setOnCheckedChangeListener { group, checkedId ->
            val selectedRadioButton = findViewById<RadioButton>(checkedId)
           var priority = selectedRadioButton.tag.toString()


        }
        *
         */

        val selectedradioButtonId=radioGroup.checkedRadioButtonId
        if(selectedradioButtonId !=-1){
            val selectedRadioButton=findViewById<RadioButton>(selectedradioButtonId)
             priority=selectedRadioButton.text.toString()
        }else{
            showSnackbar("Please select priority")
        }


        val taskName = editTextTaskName.text.toString().trim()
        val startDate = editTextStartDate.text.toString().trim()
        val dueDate = editTextDueDate.text.toString().trim()
        val description = editTextDescription.text.toString().trim()



        if (validateInput(taskName, startDate, dueDate,priority,description)) {
            sharedPreferences = getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE)
            uname = sharedPreferences.getString(UNAME_KEY, "").toString()

            val task = Task(taskName, uname, startDate, dueDate, priority, false, description)
            taskVM.insertTask(task)
            showSnackbar("Task saved successfully")

            // you can navigate
            startActivity(Intent(this, TaskListActivity::class.java))
        }
    }

    private fun validateInput(taskName: String, startDate: String, dueDate: String,priority:String, description: String): Boolean {
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
            taskVM.isAvailable(taskName)->{
                showSnackbar("Task Name already exists")
                false
            }

            else -> true
        }
    }

    private fun showSnackbar(message: String) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT).show()
    }
}