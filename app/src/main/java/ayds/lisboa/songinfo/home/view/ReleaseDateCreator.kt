package ayds.lisboa.songinfo.home.view

interface ReleaseDateCreator{
    fun createDate(precision:String, date:String):String
}
internal class ReleaseDateCreatorIml: ReleaseDateCreator {
    override fun createDate(precision: String, date: String): String {
        val datePrecision = when (precision) {
            "day" -> {"${date[0]}/${date[1]}/${date[2]}"}
            "month" -> {"${getMonth(date[1].code)}, $date[0]"}
            "year" -> {"${date[0]}, (${leapYear(date[0].code)})"}
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
        if (isLeapYear(year))
            return "leap year"
        else
            return "not leap year"
    }

}