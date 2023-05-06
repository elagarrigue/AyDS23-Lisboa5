package ayds.lisboa.songinfo.moredetails.fulllogic.data.local.sqldb

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import ayds.lisboa.songinfo.moredetails.fulllogic.domain.Artist


private const val DATABASE_VERSION = 1
private const val DB_NAME = "dictionary.db"

internal class DataBaseImp(
    context:Context?,
    private val cursorDataBase: CursorDataBase,
    ) : SQLiteOpenHelper(context, DB_NAME, null, DATABASE_VERSION),
    DataBase {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(createDataBaseTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {}

    override fun saveArtist(artist: String?, info: String?, url: String) {
        val values = ContentValues()
        values.put(ARTIST_NAME,artist)
        values.put(INFO, info)
        values.put(SOURCE, 1)
        values.put(ARTIST_URL,url)

        writableDatabase.insert(ARTISTS_TABLE, null, values)
    }


    override fun getArtist(artistName: String): Artist {

        val columnsToSelect = arrayOf(
            ID,
            ARTIST_NAME,
            INFO,
            ARTIST_URL
        )
        val selectionArgs = arrayOf(artistName)
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
        return cursorDataBase.cursorArtist(cursor)
    }

}