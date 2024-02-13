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
import androidx.recyclerview.widget.RecyclerView
import com.capgemini.tasktracker.model.Task
import com.capgemini.tasktracker.view.TaskDisplayActivity
class TaskAdapter(var listOfTask: MutableList<Task>?) : RecyclerView.Adapter<TaskAdapter.TaskHolder>() {
    inner class TaskHolder(view: View): RecyclerView.ViewHolder(view)
    {
        val taskNameTextView: TextView =view.findViewById(R.id.taskNameT)
        val priorityTextView: TextView =view.findViewById(R.id.priorityT)
        val endDateTextView:TextView=view.findViewById(R.id.endDateT)

        val statusCheckBox:CheckBox=view.findViewById(R.id.checkBox)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskHolder {
        val inflatedView= LayoutInflater.from(parent.context).inflate(R.layout.task_item,parent,false)//inflate returns root view that is card view
        return TaskHolder(inflatedView)
    }

    override fun getItemCount(): Int {
        val size = listOfTask?.size ?: 0
        Log.d("TaskAdapter","$size")
        return size
    }

    override fun onBindViewHolder(holder: TaskHolder, position: Int) {
        val task = listOfTask!![position]

        holder.taskNameTextView.text = task.taskName
        holder.priorityTextView.text = task.priority
        holder.endDateTextView.text = task.endDate
        holder.statusCheckBox.setOnCheckedChangeListener { _, isChecked ->
            task.status = isChecked
            changeColour(isChecked, holder)
        }

        holder.itemView.setOnClickListener {
            val intent= Intent(holder.itemView.context, TaskDisplayActivity::class.java)
            intent.putExtra("taskId",position)
            intent.putExtra("taskName",task.taskName)
            intent.putExtra("description",task.description)
            intent.putExtra("startDate",task.startDate)
            intent.putExtra("endDate",task.endDate)
            holder.itemView.context.startActivity(intent)
        }

    }
    private fun changeColour(checked: Boolean,holder: TaskHolder) {
        if (checked == true)
            (holder.itemView as CardView).setCardBackgroundColor(Color.LTGRAY)
        else
            (holder.itemView as CardView).setCardBackgroundColor(Color.WHITE)
    }
    fun setTask(tasks: MutableList<Task>){
        this.listOfTask = tasks
        notifyDataSetChanged()
    }
    fun getTaskNameByPosition(position: Int){
        //task name to be used in delete task
    }
}

