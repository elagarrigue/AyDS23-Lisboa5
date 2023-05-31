package ayds.lisboa.songinfo.moredetails.cards

import android.database.Cursor
import ayds.lisboa.songinfo.moredetails.domain.entities.Card
import ayds.lisboa.songinfo.moredetails.domain.entities.Source

interface CursorToCardLocal {

    fun cursorArtist(cursor: Cursor): List<Card>
}

internal class CursorToCardLocalImpl : CursorToCardLocal {

    override fun cursorArtist(cursor: Cursor): MutableList<Card> {
        val cards = mutableListOf<Card>()

        with(cursor) {
            val columnIndexSource = getColumnIndexOrThrow(SOURCE)
            val columnIndexDescription = getColumnIndexOrThrow(DESCRIPTION)
            val columnIndexInfoURL = getColumnIndexOrThrow(INFO_URL)
            val columnIndexSourceLogoURL = getColumnIndexOrThrow(SOURCE_LOGO_URL)

            while (moveToNext()) {
                val artistSource = getInt(columnIndexSource)
                val artistDescription = getString(columnIndexDescription)
                val artistInfoURL = getString(columnIndexInfoURL)
                val artistLogoURL = getString(columnIndexSourceLogoURL)

                val card = Card.CardData(
                    ordinalToSource(artistSource),
                    artistDescription,
                    artistInfoURL,
                    artistLogoURL
                )
                cards.add(card)
            }
        }

        cursor.close()
        return cards
    }

    private fun ordinalToSource(ordinal: Int): Source {
        return when (ordinal) {
            (1) -> Source.CARD1
            (2) -> Source.CARD2
            else -> Source.CARD3
        }
    }
}