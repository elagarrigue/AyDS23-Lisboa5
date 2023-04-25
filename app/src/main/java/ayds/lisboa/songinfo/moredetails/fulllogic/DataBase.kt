package ayds.lisboa.songinfo.moredetails.fulllogic

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

private const val ARTIST = "artist"
private const val ARTISTS = "artists"
private const val INFO = "info"
private const val ID = "id"
private const val SOURCE = "source"
class DataBase(context: Context?) : SQLiteOpenHelper(context, "dictionary.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            "create table artists (id INTEGER PRIMARY KEY AUTOINCREMENT, artist string, info string, source integer)"
        )
        Log.i("DB", "DB created")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {}

    fun saveArtist(artist: String?, info: String?) {
        val database = this.writableDatabase
        val values = ContentValues()
        values.put(ARTIST ,artist)
        values.put(INFO, info)
        values.put(SOURCE, 1)

        database.insert(ARTISTS, null, values)
    }

    fun getInfo(artist: String): String? {
        val database = this.readableDatabase
        val table = ARTISTS
        val columnsToSelect= arrayOf(
            ID,
            ARTIST,
            INFO
        )
        val selectionArgs = arrayOf(artist)
        val sortOrder = "$ARTIST DESC"
        val selection = "$ARTIST = ?"
        val cursor = database.query(
            table,
            columnsToSelect,
            selection,
            selectionArgs,  // The values for the WHERE clause
            null,
            null,
            sortOrder
        )
        return searchInfoArtist(cursor)
    }

    private fun searchInfoArtist(cursor: Cursor): String? {
        val infoArtist: MutableList<String> = ArrayList()
        while (cursor.moveToNext()) {
            val numberColum = cursor.getColumnIndexOrThrow(INFO)
            val info = cursor.getString(numberColum)
            infoArtist.add(info)
        }
        cursor.close()
        return if (infoArtist.isEmpty()) null else infoArtist[0]
    }
}