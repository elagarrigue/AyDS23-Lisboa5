package ayds.lisboa.songinfo.moredetails.presentation

import ayds.lisboa.songinfo.moredetails.domain.entities.Source

interface CardSourceHelper{
    fun getSource(source: Source):SourceCreator
}

internal class CardSourceHelperImpl:CardSourceHelper {
    override fun getSource(source: Source):SourceCreator {
        return when (source){
            Source.WIKIPEDIA -> Card1Factory()
            Source.LASTFM -> Card2Factory()
            Source.NYTIMES -> Card3Factory()
        }
    }

}

sealed class SourceCreator{
    abstract fun createSource(): String
}

private class Card1Factory : SourceCreator() {
    override fun createSource(): String {
        return "Wikipedia"
    }

}
private class Card2Factory : SourceCreator() {
    override fun createSource(): String {
        return "Last FM"
    }

}
private class Card3Factory() : SourceCreator() {
    override fun createSource(): String {
        return "New York Times"
    }

}