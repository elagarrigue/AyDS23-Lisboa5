package ayds.lisboa.songinfo.moredetails.fulllogic.data.local.sqldb

const val ARTIST_NAME = "artist"
const val ARTISTS_TABLE = "artists"
const val INFO = "info"
const val ID = "id"
const val SOURCE = "source"
const val ARTIST_URL = "artist_url"

const val createDataBaseTableQuery : String =
    "create table $ARTISTS_TABLE (" +
            "$ID integer PRIMARY KEY AUTOINCREMENT, " +
            "$ARTIST_NAME string, " +
            "$INFO string, " +
            "$SOURCE integer, " +
            "$ARTIST_URL string)"