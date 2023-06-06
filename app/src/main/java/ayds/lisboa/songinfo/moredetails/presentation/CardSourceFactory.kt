package ayds.lisboa.songinfo.moredetails.presentation

import ayds.lisboa.songinfo.moredetails.domain.entities.Source

interface CardSourceFactory {
    fun getSourceTitle(source: Source): String
}

internal class CardSourceFactoryImpl : CardSourceFactory {
    override fun getSourceTitle(source: Source): String {
        return when (source) {
            Source.WIKIPEDIA -> "Wikipedia"
            Source.LASTFM -> "Last FM"
            Source.NY_TIMES -> "New York Times"
            Source.EMPTY_SOURCE -> ""
        }
    }
}
