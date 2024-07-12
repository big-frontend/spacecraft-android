package com.electrolytej.main.db

import androidx.room.TypeConverter
import java.util.*

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Jul/28/2019  Sun
 */
class Converters {
    @TypeConverter
    fun calendarToDatestamp(calendar: Calendar): Long = calendar.timeInMillis

    @TypeConverter
    fun datestampToCalendar(value: Long): Calendar = Calendar.getInstance().apply { timeInMillis = value }
}