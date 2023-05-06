package ayds.lisboa.songinfo.moredetails.fulllogic.data.local.sqldb

import ayds.lisboa.songinfo.moredetails.fulllogic.domain.Artist

interface DataBase {
    fun saveArtist(artist: String?, info: String?,url: String)

    fun getArtist(artistName: String): Artist
}