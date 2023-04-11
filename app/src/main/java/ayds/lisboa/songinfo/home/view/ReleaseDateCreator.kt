package ayds.lisboa.songinfo.home.view

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.Year
import java.time.YearMonth
import java.util.*

interface ReleaseDateCreator{
    fun createDate(precision:String, date:String):String
}
internal class ReleaseDateCreatorIml: ReleaseDateCreator {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun createDate(precision: String, date: String): String {

        val datePrecision = when (precision) {
            "day" -> formatDay(LocalDate.parse(date))
            "month" -> formatMonth(YearMonth.parse(date))
            "year" -> formatYear(Year.parse(date))
            else -> "ERROR when(releaseDatePrecision)"
        }
        return datePrecision
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun formatYear(year: Year) = "${year}, (${leapYear(year)})"
    @RequiresApi(Build.VERSION_CODES.O)
    private fun formatMonth(month: YearMonth) =
        "${month.month.toString().substring(0, 1) + 
                month.month.toString().substring(1).lowercase()}, ${month.year}"
    @RequiresApi(Build.VERSION_CODES.O)
    private fun formatDay(date: LocalDate) = "${date.dayOfMonth}/${date.monthValue}/${date.year}"

    @RequiresApi(Build.VERSION_CODES.O)
    private fun leapYear(year: Year) : String {
        return if (year.isLeap)
            "leap year"
        else
            "not leap year"
    }

}