package ayds.lisboa.songinfo.home.view

interface ReleaseDateCreator{
    fun createDate(precision:String, date:String):String
}
internal class ReleaseDateCreatorIml: ReleaseDateCreator {
    override fun createDate(precision:String, date:String) = DateFormatFactory.get(precision,date).createDate()
}