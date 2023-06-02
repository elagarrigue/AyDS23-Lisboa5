package ayds.lisboa.songinfo.moredetails.domain.entities

sealed class Card {
    data class CardData(
        val source: Source,
        var description: String,
        val infoURL: String,
        var sourceLogoURL: String,
        var isLocallyStored: Boolean = false
    ) : Card()
    object EmptyCard : Card()
}

enum class Source {
    WIKIPEDIA,
    LASTFM,
    NYTIMES
}