package ayds.lisboa.songinfo.moredetails.fulllogic

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import ayds.lisboa.songinfo.moredetails.fulllogic.domain.Artist

private const val ARTIST_NAME = "artist"
private const val ARTISTS_TABLE = "artists"
private const val INFO = "info"
private const val ID = "id"
private const val SOURCE = "source"
private const val CREATE_ARTISTS_QUERY = "create table artists (id INTEGER PRIMARY KEY AUTOINCREMENT, artist string, info string, source integer, artist_url string)"
private const val DB_NAME = "dictionary.db"
private const val ARTIST_URL = "artist_url"
class DataBase(context: Context?) : SQLiteOpenHelper(context, DB_NAME, null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_ARTISTS_QUERY)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {}

    fun saveArtist(artist: String?, info: String?,url: String?) {
        val values = ContentValues()
        values.put(ARTIST_NAME ,artist)
        values.put(INFO, info)
        values.put(SOURCE, 1)
        values.put(ARTIST_URL,url)

        writableDatabase.insert(ARTISTS_TABLE, null, values)
    }

    fun getArtist(artistName: String): Artist {
        val cursor = createCursorArtist(artistName)
        return searchArtist(cursor)
    }

    private fun createCursorArtist(artistName: String): Cursor {
        val columnsToSelect = arrayOf(
            ID,
            ARTIST_NAME,
            INFO,
            ARTIST_URL
        )
        val selectionArgs = arrayOf(artistName)
        val sortOrder = "$ARTIST_NAME DESC"
        val selection = "$ARTIST_NAME = ?"
        return readableDatabase.query(
            ARTISTS_TABLE,
            columnsToSelect,
            selection,
            selectionArgs,
            null,
            null,
            sortOrder
        )
    }

    private fun searchArtist(cursor: Cursor): Artist {
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