package ayds.lisboa.songinfo.home.view

interface ReleaseDateCreator{
    fun createDate(precision:String, date:String):String
}
internal class ReleaseDateCreatorIml: ReleaseDateCreator {
    override fun createDate(precision: String, date: String): String {
        val dateArray = date.split("-")
        val datePrecision = when (precision) {
            "day" -> {"${dateArray[0]}/${dateArray[1]}/${dateArray[2]}"}
            "month" -> {"${getMonth(dateArray[1].toInt())}, ${dateArray[0]}"}
            "year" -> {"${dateArray[0]}, (${leapYear(dateArray[0].toInt())})"}
            else -> { "ERROR when(releaseDatePrecision)" }

        }
        return datePrecision
    }

    private fun getMonth(month: Int) = when (month) {
        1 -> "January"
        2 -> "February"
        3 -> "March"
        4 -> "April"
        5 -> "May"
        6 -> "June"
        7 -> "July"
        8 -> "August"
        9 -> "September"
        10 -> "October"
        11 -> "November"
        12 -> "December"
        else -> "ERROR getMonth($month)"
    }

    private fun isLeapYear(year: Int): Boolean {
        return year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)
    }

    private fun leapYear(year: Int) : String {
        return if (isLeapYear(year))
            "leap year"
        else
            "not leap year"
    }

}