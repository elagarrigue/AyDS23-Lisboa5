package ayds.lisboa.songinfo.moredetails.data.local.sqldb

import lisboa5lastfm.Artist

interface ArtistLocalStorage {
    fun saveArtist(artist: String?, info: String?,url: String)

    fun getArtist(artistName: String): Artist
}