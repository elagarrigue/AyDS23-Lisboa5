package ayds.lisboa.songinfo.moredetails.domain.entities

sealed class Card {
    data class CardData(
        val source: Source,
        val description: String,
        val infoURL: String,
        var sourceLogoURL: String,
        var isLocallyStored: Boolean = false
    ) : Card()
    object EmptyCard : Card()
}

enum class Source {
    CARD1,
    CARD2,
    CARD3
}