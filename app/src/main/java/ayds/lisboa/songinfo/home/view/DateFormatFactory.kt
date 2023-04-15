import ayds.lisboa.songinfo.home.view.DateWrapper

object DateFormatFactory {
    private const val DAY ="day"
    private const val MONTH ="month"
    private const val YEAR ="year"
    fun get(precision: String, date: String) = when (precision) {
        DAY -> DayFactory(date)
        MONTH -> MonthFactory(date)
        YEAR -> YearFactory(date)
        else -> DefaultFactory(date)
    }
}

sealed class DateCreator(val date: String) {
    abstract fun createDate(): String
}

private class DayFactory(date: String) : DateCreator(date) {
    override fun createDate() = DateWrapper(date).formatDay()
}

private class MonthFactory(date: String): DateCreator(date) {
    override fun createDate() = DateWrapper(date).formatMonth()
}

private class YearFactory(date: String): DateCreator(date) {
    override fun createDate()= DateWrapper(date).formatYear()
}

private class DefaultFactory(date: String): DateCreator(date) {
    override fun createDate()= date
}