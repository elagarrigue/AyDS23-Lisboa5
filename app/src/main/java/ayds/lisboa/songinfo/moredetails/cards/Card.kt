package ayds.lisboa.songinfo.moredetails.cards

sealed class Card {
    data class CardData(
        val source: String,
        val description: String,
        val infoURL: String,
        var sourceLogoURL: String
    ) : Card()
    object EmptyCard : Card()
}