package ayds.lisboa.songinfo.moredetails.data.local.sqldb

const val ARTIST_NAME = "artist"
const val ARTISTS_TABLE = "artists"
const val INFO = "info"
const val ID = "id"
const val SOURCE = "source"
const val ARTIST_URL = "artist_url"
const val DATABASE_VERSION = 1
const val DB_NAME = "dictionary.db"

const val createDataBaseTableQuery : String =
    "create table $ARTISTS_TABLE (" +
            "$ID integer PRIMARY KEY AUTOINCREMENT, " +
            "$ARTIST_NAME string, " +
            "$INFO string, " +
            "$SOURCE integer, " +
            "$ARTIST_URL string)"