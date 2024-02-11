package com.capgemini.tasktracker.model

import androidx.room.TypeConverter
import java.time.LocalDate

class DateConverter {
    @TypeConverter
    fun fromTimestamp(s : String) : LocalDate {
        return LocalDate.parse(s)
    }
    @TypeConverter
    fun toTimestamp(time : LocalDate) : String {
        return time.toString()
    }
}
