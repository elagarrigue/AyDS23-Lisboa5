package ayds.lisboa.songinfo.moredetails.fulllogic.presentation

import ayds.lisboa.songinfo.moredetails.fulllogic.domain.Artist
import ayds.lisboa.songinfo.moredetails.fulllogic.domain.ArtistRepository
import ayds.observer.Observable
import ayds.observer.Subject

interface MoreDetailsPresenter{
    val artistObservable: Observable<MoreDetailsUiState>
    var uiState: MoreDetailsUiState

    fun setRepository(repository: ArtistRepository)
    fun moreDetails(artistName: String)
}

internal class MoreDetailsPresenterImpl(private val artistDescriptionHelper: ArtistDescriptionHelper): MoreDetailsPresenter{

    private lateinit var repository: ArtistRepository

    override val artistObservable = Subject<MoreDetailsUiState>()
    override var uiState: MoreDetailsUiState = MoreDetailsUiState()

    override fun setRepository(repository: ArtistRepository) {
        this.repository=repository
    }

    override fun moreDetails(artistName: String){
        Thread {
            val artist=repository.getArtist(artistName)
            val reformattedText = artistDescriptionHelper.getArtistDescription(artist)
            updateArtistUiState(artist,reformattedText)
            uiState.let {
                artistObservable.notify(it)
            }
        }.start()
    }

    private fun updateArtistUiState(artist: Artist,reformattedText: String){
        when (artist) {
            is Artist.ArtistData -> updateUiState(artist,reformattedText)
            Artist.EmptyArtist -> updateNoResultsUiState()
        }
    }

    private fun updateUiState(artist: Artist.ArtistData,reformattedText: String){
        uiState = uiState.copy(
            artistName = artist.artistName,
            artistBioContent= reformattedText,
            artistURL= artist.artistURL,
        )
    }

    private fun updateNoResultsUiState(){
        uiState = uiState.copy(
            artistName = "",
            artistBioContent= "No Results",
            artistURL= "",
        )
    }
}