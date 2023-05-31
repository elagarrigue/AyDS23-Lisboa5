package ayds.lisboa.songinfo.moredetails.presentation

import ayds.lisboa.songinfo.moredetails.domain.entities.Source

interface CardSourceHelper{
    fun getSource(source: Source):SourceCreator
}

internal class CardSourceHelperImpl:CardSourceHelper {
    override fun getSource(source: Source):SourceCreator {
        return when (source){
            Source.CARD1 -> Card1Factory()
            Source.CARD2 -> Card2Factory()
            Source.CARD3 -> Card3Factory()
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
        return "NY Times"
    }

}
private class DefaultCardFactory : SourceCreator(){
    override fun createSource(): String {
        return "Default Source"
    }
}