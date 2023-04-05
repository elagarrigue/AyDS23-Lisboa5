package ayds.lisboa.songinfo.home.view

interface ReleaseDateCreator{
    fun createDate(precision:String, date:String):String
}
internal class ReleaseDateCreatorIml: ReleaseDateCreator {

    private var dateArray = emptyArray<String>()

    override fun createDate(precision: String, date: String): String {
        dateArray = date.split("-").toTypedArray()
        val datePrecision = when (precision) {
            "day" -> formatDay()
            "month" -> formatMouth()
            "year" -> formatYear()
            else -> "ERROR when(releaseDatePrecision)"
        }
        return datePrecision
    }

    private fun formatYear() = "${dateArray[0]}, (${leapYear(dateArray[0].toInt())})"
    private fun formatMouth() = "${getMonth(dateArray[1].toInt())}, ${dateArray[0]}"
    private fun formatDay() = "${dateArray[0]}/${dateArray[1]}/${dateArray[2]}"

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