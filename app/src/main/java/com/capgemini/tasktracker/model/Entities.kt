package com.capgemini.tasktracker.model

import androidx.room.*
import java.time.LocalDate

@TypeConverters(DateConverter::class)

@Entity(tableName="User")
data class User(
    @PrimaryKey
    @ColumnInfo("username")
    val username: String,

    @ColumnInfo("user_email")
    val email: String,

    @ColumnInfo("user_password")
    val password: String
)

@Entity(tableName = "Task", foreignKeys = [ForeignKey(entity = User::class, childColumns = ["username"], parentColumns = ["task_name"])])
data class Task(
    @PrimaryKey
    @ColumnInfo("task_name")
    var taskName: String,

    @ColumnInfo("username")
    val username: String,

    @ColumnInfo("task_start_date")
    var startDate: LocalDate,

    @ColumnInfo("task_end_date")
    var endDate: LocalDate,

    @ColumnInfo("task_priority")
    var priority: String,

    @ColumnInfo("task_status")
    var status: Boolean,

    @ColumnInfo("task_description")
    var description: String                    //later change it to checkpoints
)
