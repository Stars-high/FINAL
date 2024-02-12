
package com.capgemini.tasktracker

import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.capgemini.tasktracker.model.Task
import com.capgemini.tasktracker.view.TaskDisplayActivity
import java.time.LocalDate

class TaskAdapter(val taskList: List<Task>) :
    RecyclerView.Adapter<TaskAdapter.TaskHolder>() {
    inner class TaskHolder(inflated: View): RecyclerView.ViewHolder(inflated)

    {
        var taskNameTextView: TextView =inflated.findViewById(R.id.taskNameT)
        var priorityTextView: TextView =inflated.findViewById(R.id.priorityT)
        var endDateTextView:TextView=inflated.findViewById(R.id.endDateT)

        var statusCheckBox:CheckBox=inflated.findViewById(R.id.checkBox)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskHolder {
        val inflatedView= LayoutInflater.from(parent.context).inflate(R.layout.task_item,parent,false)//inflate returns root view that is card view
        return TaskHolder(inflatedView)
    }

    override fun getItemCount(): Int {
        Log.d("TaskAdapter","${taskList.size}")
        return taskList.size
    }

    override fun onBindViewHolder(holder: TaskHolder, position: Int) {
        val task=taskList[position]

        holder.taskNameTextView.text=task.taskName
        holder.priorityTextView.text=task.priority
        holder.endDateTextView.text= task.endDate.toString()
        holder.statusCheckBox.setOnCheckedChangeListener { _, isChecked ->
            task.status = isChecked
            changeColour(isChecked,holder)
        }

        holder.itemView.setOnClickListener {
            val intent= Intent(holder.itemView.context, TaskDisplayActivity::class.java)
            intent.putExtra("taskId",position)
            intent.putExtra("taskName",task.taskName)
            intent.putExtra("description",task.description)
            intent.putExtra("startDate",task.startDate)
            intent.putExtra("Enddate",task.endDate)
            holder.itemView.context.startActivity(intent)
        }
    }
    private fun changeColour(checked: Boolean,holder: TaskHolder) {
        if (checked == true) {
            (holder.itemView as CardView).setCardBackgroundColor(Color.LTGRAY)


        }
        else
            (holder.itemView as CardView).setCardBackgroundColor(Color.WHITE)
    }
}