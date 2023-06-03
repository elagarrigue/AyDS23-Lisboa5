package ayds.lisboa.songinfo.moredetails.presentation

import ayds.lisboa.songinfo.moredetails.domain.entities.Source

interface CardSourceFactory {
    fun getSource(source: Source): String
}

internal class CardSourceFactoryImpl : CardSourceFactory {
    override fun getSource(source: Source): String {
        return when (source) {
            Source.WIKIPEDIA -> "Wikipedia"
            Source.LASTFM -> "Last FM"
            Source.NY_TIMES -> "New York Times"
            Source.EMPTY_SOURCE -> ""
        }
    }
}
