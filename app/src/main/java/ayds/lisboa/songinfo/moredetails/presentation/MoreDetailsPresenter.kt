package ayds.lisboa.songinfo.moredetails.presentation

import ayds.lisboa.songinfo.moredetails.domain.entities.Artist
import ayds.lisboa.songinfo.moredetails.domain.repository.ArtistRepository
import ayds.observer.Observable
import ayds.observer.Subject

interface MoreDetailsPresenter {
    val artistObservable: Observable<MoreDetailsUiState>

    fun setRepository(repository: ArtistRepository)
    fun moreDetails(artistName: String)
}

internal class MoreDetailsPresenterImpl(private val artistDescriptionHelper: ArtistDescriptionHelper) :
    MoreDetailsPresenter {

    private lateinit var repository: ArtistRepository

    override val artistObservable = Subject<MoreDetailsUiState>()

    override fun setRepository(repository: ArtistRepository) {
        this.repository = repository
    }

    override fun moreDetails(artistName: String) {
        Thread {
            fetchMoreDetails(artistName)
        }.start()
    }

    private fun fetchMoreDetails(artistName: String){
        artistObservable.notify(getMoreDetailsUiState(artistName))
    }
    private fun getMoreDetailsUiState(artistName: String): MoreDetailsUiState {
        val artist = repository.getArtist(artistName)
        val reformattedText = artistDescriptionHelper.getArtistDescription(artist)
        return updateArtistUiState(artist, reformattedText)
    }

    private fun updateArtistUiState(artist: Artist, reformattedText: String): MoreDetailsUiState {
        return when (artist) {
            is Artist.ArtistData -> updateUiState(artist, reformattedText)
            Artist.EmptyArtist -> updateNoResultsUiState()
        }
    }

    private fun updateUiState(
        artist: Artist.ArtistData,
        reformattedText: String
    ): MoreDetailsUiState {
        return MoreDetailsUiState(
            artistName = artist.artistName,
            artistBioContent = reformattedText,
            artistURL = artist.artistURL,
        )
    }

    private fun updateNoResultsUiState(): MoreDetailsUiState {
        return MoreDetailsUiState(
            artistName = "",
            artistBioContent = "No Results",
            artistURL = "",
        )
    }
}