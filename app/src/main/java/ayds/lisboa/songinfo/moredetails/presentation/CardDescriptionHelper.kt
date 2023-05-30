package ayds.lisboa.songinfo.moredetails.presentation

import ayds.lisboa.songinfo.moredetails.domain.entities.Card

private const val PREFIX = "[*]"
private const val HTML_OPENING_TAG = "<html><div width=400><font face=\"arial\">"
private const val HTML_CLOSING_TAG = "</font></div></html>"

interface CardDescriptionHelper {
    fun getCardInfo(card:Card):String
}
internal class CardDescriptionHelperImpl : CardDescriptionHelper{
    override fun getCardInfo(card: Card): String {
        return if (card is Card.CardData)
            card.description
        else
            "No results"
    }

}