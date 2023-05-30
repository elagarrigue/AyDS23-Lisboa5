package ayds.lisboa.songinfo.moredetails.cards

import android.database.Cursor
import ayds.lisboa.songinfo.moredetails.domain.entities.Card

interface CursorToCardLocal {

    fun cursorArtist(cursor: Cursor):  List<Card>
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
                val artistSource = getString(columnIndexSource)
                val artistDescription = getString(columnIndexDescription)
                val artistInfoURL = getString(columnIndexInfoURL)
                val artistLogoURL = getString(columnIndexSourceLogoURL)

                val card = Card.CardData(artistSource, artistDescription, artistInfoURL, artistLogoURL)
                cards.add(card)
            }
        }

        cursor.close()
        return cards
    }
}