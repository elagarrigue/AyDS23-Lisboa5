package ayds.lisboa.songinfo.moredetails.presentation


import ayds.lisboa.songinfo.moredetails.domain.entities.Card
import ayds.lisboa.songinfo.moredetails.domain.entities.Source
import ayds.lisboa.songinfo.moredetails.domain.repository.CardRepository
import ayds.observer.Observable
import ayds.observer.Subject

private const val SOURCE = "Source: "
interface MoreDetailsPresenter {
    val artistObservable: Observable<List<CardUiState>>

    fun moreDetails(artistName: String)
}

internal class MoreDetailsPresenterImpl(
    private val cardDescriptionHelper: CardDescriptionHelper,
    private val repository: CardRepository,
    private val cardSourceFactory: CardSourceFactory
    ) :
    MoreDetailsPresenter {

    override val artistObservable = Subject<List<CardUiState>>()

    override fun moreDetails(artistName: String) {
        Thread {
            fetchMoreDetails(artistName)
        }.start()
    }

    private fun fetchMoreDetails(artistName: String){
        val moreDetailsUiStates = getMoreDetailsUiState(artistName)
        artistObservable.notify(moreDetailsUiStates)
    }
    private fun getMoreDetailsUiState(artistName: String): List<CardUiState> {
        val cards = repository.getCards(artistName)
        val cardUiStates : MutableList<CardUiState> = ArrayList()

        if (cards.isEmpty()) {
            cardUiStates.add(updateCardNoResultsUiState())
        } else {
            for (card in cards){
                val reformattedText = cardDescriptionHelper.getCardDescription(card,artistName)
                cardUiStates.add(updateCardUiState(card, reformattedText))
            }
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
            Source.EMPTY_SOURCE,
            "No results",
            "",
            "",
            ""
        )
    }

    private fun getCardSource(card : Card.CardData): String {
        return cardSourceFactory.getSourceTitle(card.source)
    }
    private fun getSourceDescription(card: Card.CardData)
       = "$SOURCE${getCardSource(card)}"


    private fun updateUiState(card: Card.CardData, reformattedText: String): CardUiState {
        return CardUiState(
            card.source,
            reformattedText,
            card.infoURL,
            card.sourceLogoURL,
           getSourceDescription(card)
        )
    }

}