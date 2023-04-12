package ayds.lisboa.songinfo.home.view

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.Year
import java.time.YearMonth
import java.util.*

class DateWrapper(var date: String) {

    @RequiresApi(Build.VERSION_CODES.O)
    fun formatYear() : String {
        val year = Year.parse(date)
        return "${year}, (${leapYear(year)})"
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun formatMonth(): String {
        val month= YearMonth.parse(date)
        val firstChar = month.month.toString().substring(0, 1)
        val otherCharsLowerCase = month.month.toString().substring(1).lowercase()
        return firstChar+otherCharsLowerCase+", ${month.year}"
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun formatDay() : String {
        val day = LocalDate.parse(date)
        return "${day.dayOfMonth}/${day.monthValue}/${day.year}"
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun leapYear(year: Year) : String = if (year.isLeap) "leap year" else "not leap year"

}