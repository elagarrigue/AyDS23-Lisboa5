package ayds.lisboa.songinfo.moredetails.presentation


import ayds.lisboa.songinfo.moredetails.domain.entities.Card
import ayds.lisboa.songinfo.moredetails.domain.repository.CardRepository
import ayds.observer.Observable
import ayds.observer.Subject

interface MoreDetailsPresenter {
    val artistObservable: Observable<MoreDetailsUiState>

    fun moreDetails(artistName: String)
}

internal class MoreDetailsPresenterImpl(
    private val cardDescriptionHelper: CardDescriptionHelper,
    private val repository: CardRepository,
    private val cardSourceHelper: CardSourceHelper
    ) :
    MoreDetailsPresenter {

    override val artistObservable = Subject<MoreDetailsUiState>()

    override fun moreDetails(artistName: String) {
        Thread {
            fetchMoreDetails(artistName)
        }.start()
    }

    private fun fetchMoreDetails(artistName: String){
        val moreDetailsUiStates = getMoreDetailsUiState(artistName);
        for (moreDetailsUiState in moreDetailsUiStates)
            artistObservable.notify(moreDetailsUiState)
    }
    private fun getMoreDetailsUiState(artistName: String): List<MoreDetailsUiState> {
        val cards = repository.getCards(artistName)
        var moreDetailsUiStates : MutableList<MoreDetailsUiState> = ArrayList()
        for (card in cards){
            val reformattedText = cardDescriptionHelper.getCardInfo(card)
            moreDetailsUiStates.add(updateCardUiState(card, reformattedText))
        }

        return moreDetailsUiStates
    }

    private fun updateCardUiState(card: Card, reformattedText: String):MoreDetailsUiState{
        return when (card) {
            is Card.CardData -> updateUiState(card, reformattedText)
            Card.EmptyCard -> updateCardNoResultsUiState()
        }
    }

    private fun updateCardNoResultsUiState(): MoreDetailsUiState {
        return MoreDetailsUiState(
            "",
            "No results",
            "",
            ""
        )
    }

    private fun getCardSource(card : Card.CardData): String {
        return cardSourceHelper.getSource(card.source).createSource()
    }

    private fun updateUiState(card: Card.CardData, reformattedText: String): MoreDetailsUiState {
        return MoreDetailsUiState(
            getCardSource(card),
            reformattedText,
            card.infoURL,
            card.sourceLogoURL
        )
    }

}