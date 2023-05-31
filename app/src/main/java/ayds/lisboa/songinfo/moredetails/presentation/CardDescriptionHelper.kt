package ayds.lisboa.songinfo.moredetails.presentation

import ayds.lisboa.songinfo.moredetails.domain.entities.Card
import java.util.*

private const val PREFIX = "[*]"
private const val HTML_OPENING_TAG = "<html><div width=400><font face=\"arial\">"
private const val HTML_CLOSING_TAG = "</font></div></html>"

interface CardDescriptionHelper {
    fun getCardDescription(card: Card,artistName: String): String
}

internal class CardDescriptionHelperImpl : CardDescriptionHelper {

    override fun getCardDescription (card: Card,artistName: String): String {
        val artistDesc = getCardInfo(card)
        return if (card is Card.CardData) {
            val artistDescAsHTML = artistDescAsHTML(artistDesc)
            textToHtml(artistDescAsHTML, artistName)
        } else artistDesc
    }
    private fun getCardInfo(card: Card): String {
        return if (card is Card.CardData) {
            if (card.isLocallyStored) {
                card.description = "$PREFIX${card.description}"
            }
            card.description
        } else
            "No results"
    }

    private fun artistDescAsHTML(artistBioContent: String) = artistBioContent.reformatArtistBio()

    private fun String.reformatArtistBio() = this.replace("\\n", "\n")
    private fun textToHtml(text: String, artistName: String): String {
        val builder = StringBuilder()
        val textWithBold = textAsBold(text,artistName)

        builder.append(HTML_OPENING_TAG)
        builder.append(textWithBold)
        builder.append(HTML_CLOSING_TAG)

        return builder.toString()
    }
    private fun textAsBold(text: String,artistName: String) : String {
        return text
            .replace("'", " ")
            .replace("\n", "<br>")
            .replace(
                "(?i)${artistName}".toRegex(),
                "<b>" + artistName.uppercase(Locale.getDefault()) + "</b>"
            )
    }
}