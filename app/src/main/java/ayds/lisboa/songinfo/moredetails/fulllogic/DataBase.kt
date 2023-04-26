package ayds.lisboa.songinfo.moredetails.fulllogic

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

private const val ARTIST_NAME = "artist"
private const val ARTISTS_TABLE = "artists"
private const val INFO = "info"
private const val ID = "id"
private const val SOURCE = "source"
class DataBase(context: Context?) : SQLiteOpenHelper(context, "dictionary.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            "create table artists (id INTEGER PRIMARY KEY AUTOINCREMENT, artist string, info string, source integer)"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {}

    fun saveArtist(artist: String?, info: String?) {
        val values = ContentValues()
        values.put(ARTIST_NAME ,artist)
        values.put(INFO, info)
        values.put(SOURCE, 1)

        writableDatabase.insert(ARTISTS_TABLE, null, values)
    }

    fun getInfo(artist: String): String {
        val columnsToSelect= arrayOf(
            ID,
            ARTIST_NAME,
            INFO
        )
        val selectionArgs = arrayOf(artist)
        val sortOrder = "$ARTIST_NAME DESC"
        val selection = "$ARTIST_NAME = ?"
        val cursor = readableDatabase.query(
            ARTISTS_TABLE,
            columnsToSelect,
            selection,
            selectionArgs,
            null,
            null,
            sortOrder
        )
        return searchInfoArtist(cursor)
    }

    private fun searchInfoArtist(cursor: Cursor): String {
        val infoArtistList: MutableList<String> = ArrayList()
        while (cursor.moveToNext()) {
            val numberColum = cursor.getColumnIndexOrThrow(INFO)
            val info = cursor.getString(numberColum)
            infoArtistList.add(info)
        }
        cursor.close()
        return infoArtistList.firstOrNull() ?: ""
    }
}