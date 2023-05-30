package ayds.lisboa.songinfo.moredetails.cards

const val CARD_NAME = "card"
const val ARTIST_NAME = "artistName"
const val CARDS_TABLE = "cards"
const val ID = "id"
const val SOURCE = "source"
const val DESCRIPTION = "description"
const val INFO_URL = "infoURL"
const val SOURCE_LOGO_URL = "sourceLogoURL"
const val DATABASE_VERSION = 1
const val DB_NAME = "dictionary.db"

const val createDataBaseTableQuery : String =
    "create table $CARDS_TABLE (" +
            "$ID integer PRIMARY KEY AUTOINCREMENT, " +
            "$ARTIST_NAME string," +
            "$SOURCE integer, " +
            "$CARD_NAME string, " +
            "$DESCRIPTION string, " +
            "$INFO_URL string, " +
            "$SOURCE_LOGO_URL string)"