package ayds.lisboa.songinfo.moredetails.fulllogic.model.entities.repository

import ayds.lisboa.songinfo.moredetails.fulllogic.model.entities.Artist
import ayds.lisboa.songinfo.moredetails.fulllogic.model.entities.repository.localArtist.ArtistLocalStorage

interface ArtistRepository{
    fun getArtist(artistName: String):Artist

}

internal class ArtistRepositoryImpl(
    private val artistLocalStorage: ArtistLocalStorage,
    private val artistExternalService: ArtistExternalService,
): ArtistRepository {

    override fun getArtist(artistName: String): Artist {
        var artist: Artist?
        artist = artistLocalStorage.getArtist(artistName)
        when (artist) {
            Artist.EmptyArtist -> try {
                artist = artistExternalService.getArtistFromLastFMAPI(artistName)
                artistLocalStorage.saveArtistInfo(artist)
            } catch (e: Exception) {
                artist = null
            }
            is  Artist.ArtistData -> markArtistAsLocal(artist)
        }
        return artist ?: Artist.EmptyArtist
    }

    private fun markArtistAsLocal(artist: Artist.ArtistData) {
        artist.isLocallyStored = true
    }

}
