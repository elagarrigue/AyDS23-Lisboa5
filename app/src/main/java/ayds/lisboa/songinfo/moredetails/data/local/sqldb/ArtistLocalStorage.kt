package ayds.lisboa.songinfo.moredetails.data.local.sqldb

import ayds.lisboa.songinfo.moredetails.domain.entities.Artist

interface ArtistLocalStorage {
    fun saveArtist(artist: String?, info: String?,url: String)

    fun getArtist(artistName: String): Artist
}