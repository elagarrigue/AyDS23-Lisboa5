package ayds.lisboa.songinfo.moredetails.fulllogic.data.local.artist

import ayds.lisboa.songinfo.moredetails.fulllogic.domain.Artist

interface ArtistLocalStorage {
    fun getArtist(artistName: String): Artist

    fun saveArtistInfo(artist: Artist?)
}