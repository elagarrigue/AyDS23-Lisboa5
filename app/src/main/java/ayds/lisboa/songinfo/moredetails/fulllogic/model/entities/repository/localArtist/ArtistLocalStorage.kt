package ayds.lisboa.songinfo.moredetails.fulllogic.model.entities.repository.localArtist

import ayds.lisboa.songinfo.moredetails.fulllogic.model.entities.Artist

interface ArtistLocalStorage {
    fun getArtist(artistName: String): Artist

    fun saveArtistInfo(artist: Artist?)
}