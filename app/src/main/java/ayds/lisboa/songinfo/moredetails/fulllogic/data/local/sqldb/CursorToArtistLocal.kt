package ayds.lisboa.songinfo.moredetails.fulllogic.data.local.sqldb

import android.database.Cursor
import ayds.lisboa.songinfo.moredetails.fulllogic.domain.entities.Artist

interface CursorToArtistLocal {

    fun cursorArtist(cursor: Cursor): Artist
}

internal class CursorToArtistLocalImpl : CursorToArtistLocal {

    override fun cursorArtist(cursor: Cursor): Artist {
        var artistName = ""
        var artistInfo = ""
        var artistURL = ""
        while (cursor.moveToNext()) {
            val numberColumName = cursor.getColumnIndexOrThrow(ARTIST_NAME)
            val numberColumInfo = cursor.getColumnIndexOrThrow(INFO)
            val numberColumURL = cursor.getColumnIndexOrThrow(ARTIST_URL)
            artistName = cursor.getString(numberColumName)
            artistInfo = cursor.getString(numberColumInfo)
            artistURL = cursor.getString(numberColumURL)
        }
        cursor.close()
        return if (artistInfo == "") Artist.EmptyArtist
        else Artist.ArtistData(artistName,artistInfo,artistURL)
    }
}