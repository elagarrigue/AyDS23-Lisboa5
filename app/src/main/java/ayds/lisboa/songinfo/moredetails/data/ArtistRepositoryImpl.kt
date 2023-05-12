package ayds.lisboa.songinfo.moredetails.data

import ayds.lisboa.songinfo.moredetails.data.external.artist.ArtistExternalService
import ayds.lisboa.songinfo.moredetails.data.local.sqldb.ArtistLocalStorage
import ayds.lisboa.songinfo.moredetails.domain.entities.Artist
import ayds.lisboa.songinfo.moredetails.domain.repository.ArtistRepository

class ArtistRepositoryImpl(
    private val artistLocalStorage: ArtistLocalStorage,
    private val artistExternalService: ArtistExternalService,
) : ArtistRepository {

    override fun getArtist(artistName: String): Artist {
        var artist: Artist?
        artist = artistLocalStorage.getArtist(artistName)
        when (artist) {
            Artist.EmptyArtist -> try {
                artist = artistExternalService.getArtistFromLastFMAPI(artistName)
                artist?.let { saveArtistInfo(it) }
            } catch (e: Exception) {
                artist = null
            }
            is Artist.ArtistData -> markArtistAsLocal(artist)
        }
        return artist ?: Artist.EmptyArtist
    }

    private fun markArtistAsLocal(artist: Artist.ArtistData) {
        artist.isLocallyStored = true
    }

    private fun saveArtistInfo(artist: Artist.ArtistData) {
        artistLocalStorage.saveArtist(artist.artistName, artist.artistBioContent, artist.artistURL)
    }
}