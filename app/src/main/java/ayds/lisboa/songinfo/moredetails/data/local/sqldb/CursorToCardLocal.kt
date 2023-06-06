package ayds.lisboa.songinfo.moredetails.cards

import android.database.Cursor
import ayds.lisboa.songinfo.moredetails.domain.entities.Card
import ayds.lisboa.songinfo.moredetails.domain.entities.Source

interface CursorToCardLocal {

    fun cursorCard(cursor: Cursor): List<Card.CardData>
}

internal class CursorToCardLocalImpl : CursorToCardLocal {

    override fun cursorCard(cursor: Cursor): MutableList<Card.CardData> {
        val cards = mutableListOf<Card.CardData>()

        with(cursor) {
            val columnIndexSource = getColumnIndexOrThrow(SOURCE)
            val columnIndexDescription = getColumnIndexOrThrow(DESCRIPTION)
            val columnIndexInfoURL = getColumnIndexOrThrow(INFO_URL)
            val columnIndexSourceLogoURL = getColumnIndexOrThrow(SOURCE_LOGO_URL)

            while (moveToNext()) {
                val cardSource = getInt(columnIndexSource)
                val cardDescription = getString(columnIndexDescription)
                val cardInfoURL = getString(columnIndexInfoURL)
                val cardLogoURL = getString(columnIndexSourceLogoURL)

                val card = Card.CardData(
                    Source.values()[cardSource],
                    cardDescription,
                    cardInfoURL,
                    cardLogoURL
                )
                cards.add(card)
            }
        }

        cursor.close()
        return cards
    }
}