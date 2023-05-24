package ayds.lisboa.songinfo.moredetails.data.local.sqldb

import com.example.lisboa5lastfm.lastfm.external.Artist

interface ArtistLocalStorage {
    fun saveArtist(artist: String?, info: String?,url: String)

    fun getArtist(artistName: String): Artist
}