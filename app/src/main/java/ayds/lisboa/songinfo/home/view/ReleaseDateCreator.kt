package ayds.lisboa.songinfo.home.view

interface ReleaseDateCreator{
    fun createDate(precision:String, date:String):String
}
internal class ReleaseDateCreatorIml: ReleaseDateCreator {

    private var dateArray = emptyArray<String>()
    private val monthArray = arrayOf( "January","February","March","April","May","June","July","August","September","October","November","December" )

    override fun createDate(precision: String, date: String): String {
        dateArray = date.split("-").toTypedArray()
        val datePrecision = when (precision) {
            "day" -> formatDay()
            "month" -> formatMonth()
            "year" -> formatYear()
            else -> "ERROR when(releaseDatePrecision)"
        }
        return datePrecision
    }

    private fun formatYear() = "${dateArray[0]}, (${leapYear(dateArray[0].toInt())})"
    private fun formatMonth() = "${getMonth(dateArray[1].toInt())}, ${dateArray[0]}"
    private fun formatDay() = "${dateArray[2]}/${dateArray[1]}/${dateArray[0]}"

    private fun getMonth(month: Int) = if (month in 1..12)  monthArray[month] else "ERROR getMonth(month)"

    private fun isLeapYear(year: Int) = (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0))

    private fun leapYear(year: Int) = if (isLeapYear(year)) "leap year" else "not leap year"

}