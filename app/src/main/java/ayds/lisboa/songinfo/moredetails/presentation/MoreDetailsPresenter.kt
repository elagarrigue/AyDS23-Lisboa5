package ayds.lisboa.songinfo.moredetails.presentation


import ayds.observer.Observable
import ayds.observer.Subject
import lisboa5lastfm.Artist

interface MoreDetailsPresenter {
    val artistObservable: Observable<MoreDetailsUiState>

    fun moreDetails(artistName: String)
}

internal class MoreDetailsPresenterImpl(private val artistDescriptionHelper: ArtistDescriptionHelper, private val repository: ArtistRepository) :
    MoreDetailsPresenter {

    override val artistObservable = Subject<MoreDetailsUiState>()

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