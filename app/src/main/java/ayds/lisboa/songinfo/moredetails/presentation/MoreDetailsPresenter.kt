package ayds.lisboa.songinfo.moredetails.presentation


import ayds.lisboa.songinfo.moredetails.domain.entities.Card
import ayds.lisboa.songinfo.moredetails.domain.repository.CardRepository
import ayds.observer.Observable
import ayds.observer.Subject

interface MoreDetailsPresenter {
    val artistObservable: Observable<CardUiState>

    fun moreDetails(artistName: String)
}

internal class MoreDetailsPresenterImpl(
    private val cardDescriptionHelper: CardDescriptionHelper,
    private val repository: CardRepository,
    private val cardSourceHelper: CardSourceHelper
    ) :
    MoreDetailsPresenter {

    override val artistObservable = Subject<CardUiState>()

    override fun moreDetails(artistName: String) {
        Thread {
            fetchMoreDetails(artistName)
        }.start()
    }

    private fun fetchMoreDetails(artistName: String){
        val moreDetailsUiStates = getMoreDetailsUiState(artistName)
        if(moreDetailsUiStates.isEmpty()){
            artistObservable.notify(updateCardNoResultsUiState())
        }
        else{
            for (moreDetailsUiState in moreDetailsUiStates)
                artistObservable.notify(moreDetailsUiState)
        }
    }
    private fun getMoreDetailsUiState(artistName: String): List<CardUiState> {
        val cards = repository.getCards(artistName)
        val cardUiStates : MutableList<CardUiState> = ArrayList()
        for (card in cards){
            val reformattedText = cardDescriptionHelper.getCardDescription(card,artistName)
            cardUiStates.add(updateCardUiState(card, reformattedText))
        }

        return cardUiStates
    }

    private fun updateCardUiState(card: Card, reformattedText: String):CardUiState{
        return when (card) {
            is Card.CardData -> updateUiState(card, reformattedText)
            Card.EmptyCard -> updateCardNoResultsUiState()
        }
    }

    private fun updateCardNoResultsUiState(): CardUiState {
        return CardUiState(
            "",
            "No results",
            "",
            ""
        )
    }

    private fun getCardSource(card : Card.CardData): String {
        return cardSourceHelper.getSource(card.source)
    }

    private fun updateUiState(card: Card.CardData, reformattedText: String): CardUiState {
        return CardUiState(
            getCardSource(card),
            reformattedText,
            card.infoURL,
            card.sourceLogoURL
        )
    }

}