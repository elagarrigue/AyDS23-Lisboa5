package ayds.lisboa.songinfo.moredetails.fulllogic

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

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
        values.put("artist", artist)
        values.put("info", info)
        values.put("source", 1)

        database.insert("artists", null, values)
    }

    fun getInfo(artist: String): String? {
        val database = this.readableDatabase
        val table = "artists"
        val columnsToSelect= arrayOf(
            "id",
            "artist",
            "info"
        )
        val selectionArgs = arrayOf(artist)
        val sortOrder = "artist DESC"
        val cursor = database.query(
            table,
            columnsToSelect,
            artist,
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
            val numberColum = cursor.getColumnIndexOrThrow("info")
            val info = cursor.getString(numberColum)
            infoArtist.add(info)
        }
        cursor.close()
        return if (infoArtist.isEmpty()) null else infoArtist[0]
    }
}