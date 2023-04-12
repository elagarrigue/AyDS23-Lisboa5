package ayds.lisboa.songinfo.home.view

import android.os.Build
import java.time.LocalDate
import java.time.Year
import java.time.YearMonth

class DateWrapper(var date: String) {

    fun formatYear() : String {
        val year = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Year.parse(date)
        } else TODO("VERSION.SDK_INT < O")
        return "${year}, (${leapYear(year)})"
    }

    fun formatMonth(): String {
        val month= if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            YearMonth.parse(date)
        } else TODO("VERSION.SDK_INT < O")
        val firstChar = month.month.toString().substring(0, 1)
        val otherCharsLowerCase = month.month.toString().substring(1).lowercase()
        return firstChar+otherCharsLowerCase+", ${month.year}"
    }

    fun formatDay() : String {
        val day = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LocalDate.parse(date)
        } else TODO("VERSION.SDK_INT < O")
        return "${day.dayOfMonth}/${day.monthValue}/${day.year}"
    }

    private fun leapYear(year: Year) : String = if (if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            year.isLeap
        } else TODO("VERSION.SDK_INT < O")
    ) "leap year" else "not leap year"

}