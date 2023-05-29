package ayds.lisboa.songinfo.moredetails.cards

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class CardLocalStorageImpl (
    context: Context?,
    ) : SQLiteOpenHelper(context, DB_NAME, null, DATABASE_VERSION),
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


        override fun getCardList(artistName: String): List<Card> {
            val returnList = ArrayList<Card>()
            return returnList
        }
}