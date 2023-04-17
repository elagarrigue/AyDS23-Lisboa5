object DateFormatFactory {
    private const val DAY ="day"
    private const val MONTH ="month"
    private const val YEAR ="year"
    var dateArray = emptyArray<String>()
    fun get(precision: String, date: String):DateCreator{
        dateArray = date.split("-").toTypedArray()
        return when (precision) {
            DAY -> DayFactory(dateArray)
            MONTH -> MonthFactory(dateArray)
            YEAR -> YearFactory(dateArray)
            else -> DefaultFactory(dateArray)
        }
    }
}

sealed class DateCreator(val dateArray: Array<String>) {
    abstract fun createDate(): String
}

private class DayFactory(dateArray: Array<String>) : DateCreator(dateArray) {
    override fun createDate(): String {
        val day = dateArray[2]
        val month = dateArray[1]
        val year = dateArray[0]
        return "$day/$month/$year"
    }
}

private class MonthFactory(dateArray: Array<String>): DateCreator(dateArray) {
    private val monthArray = arrayOf( "January","February","March","April","May","June","July","August","September","October","November","December" )
    override fun createDate() = "${getMonth(dateArray[1].toInt())}, ${dateArray[0]}"

    private fun getMonth(month: Int) = if (month in 1..12)  monthArray[month-1] else "ERROR getMonth(month)"
}

private class YearFactory(dateArray: Array<String>): DateCreator(dateArray) {
    override fun createDate()= "${dateArray[0]}, (${leapYear(dateArray[0].toInt())})"

    private fun leapYear(year: Int) = if (isLeapYear(year)) "leap year" else "not leap year"
    private fun isLeapYear(year: Int) = (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0))
}

private class DefaultFactory(dateArray: Array<String>): DateCreator(dateArray) {
    override fun createDate()= dateArray.joinToString("-")
}






