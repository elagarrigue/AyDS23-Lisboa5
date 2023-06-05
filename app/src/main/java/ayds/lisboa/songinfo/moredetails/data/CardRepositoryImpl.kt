package ayds.lisboa.songinfo.moredetails.data

import ayds.lisboa.songinfo.moredetails.data.local.sqldb.CardLocalStorage
import ayds.lisboa.songinfo.moredetails.domain.entities.Card
import ayds.lisboa.songinfo.moredetails.domain.repository.CardRepository

class CardRepositoryImpl(
    private val cardLocalStorage: CardLocalStorage,
    private val cardBroker: CardsBroker
) : CardRepository {

    override fun getCards(artistName: String): List<Card> {
        var cards: List<Card>
        cards = cardLocalStorage.getCardList(artistName)
        when {
            (cards.isNotEmpty()) -> {
                markCardsAsLocal(cards)
            }
            else -> {
                cards = getBrokerCards(artistName)
                saveCardInfo(cards, artistName)
            }
        }
        return cards
    }

    private fun saveCardInfo(cards: List<Card>, artistName: String) {
        cardLocalStorage.saveCardList(cards, artistName)
    }

    private fun getBrokerCards(artistName: String) =
        cardBroker.getCards(artistName)


    private fun markCardsAsLocal(cards: List<Card.CardData>) {
        for (card in cards) {
            card.isLocallyStored = true
        }
    }
}