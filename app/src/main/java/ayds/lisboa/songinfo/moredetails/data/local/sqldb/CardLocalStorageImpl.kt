package ayds.lisboa.songinfo.moredetails.data.local.sqldb

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import ayds.lisboa.songinfo.moredetails.cards.*
import ayds.lisboa.songinfo.moredetails.domain.entities.Card

class CardLocalStorageImpl (
    context: Context?,
    private val cursorDataBase: CursorToCardLocal,
    ) : SQLiteOpenHelper(context,
    DB_NAME, null,
    DATABASE_VERSION
),
    CardLocalStorage {

        override fun onCreate(db: SQLiteDatabase) {
            db.execSQL(createDataBaseTableQuery)
        }

        override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {}

        override fun saveCardList(cardList: List<Card>) {
            for (card in cardList){
                if(card is Card.CardData){
                    val values = ContentValues()
                    values.put(SOURCE, card.source)
                    values.put(DESCRIPTION, card.description)
                    values.put(INFO_URL, card.infoURL)
                    values.put(SOURCE_LOGO_URL, card.sourceLogoURL)
                    writableDatabase.insert(CARDS_TABLE, null, values)
                }
            }
        }


        override fun getCardList(artistName: String):List<Card> {

            val columnsToSelect = arrayOf(
                ID,
                ARTIST_NAME,
                SOURCE,
                DESCRIPTION,
                INFO_URL,
                SOURCE_LOGO_URL
            )
            val selectionArgs = arrayOf(artistName)
            val sortOrder = "$ARTIST_NAME DESC"
            val selection = "$ARTIST_NAME = ?"

            val cursor = readableDatabase.query(
                CARDS_TABLE,
                columnsToSelect,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
            )
            return cursorDataBase.cursorArtist(cursor).toList()
        }
}