package ayds.lisboa.songinfo.moredetails.presentation

import ayds.lisboa.songinfo.moredetails.domain.entities.Source

interface CardSourceHelper {
    fun getSource(source: Source): String
}

internal class CardSourceHelperImpl : CardSourceHelper {
    override fun getSource(source: Source): String {
        return when (source) {
            Source.WIKIPEDIA -> "Wikipedia"
            Source.LASTFM -> "Last FM"
            Source.NYTIMES -> "New York Times"
        }
    }
}
