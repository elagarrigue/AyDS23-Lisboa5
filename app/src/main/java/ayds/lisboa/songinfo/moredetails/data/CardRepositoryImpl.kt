package ayds.lisboa.songinfo.moredetails.data

import ayds.lisboa.songinfo.moredetails.domain.Broker
import ayds.lisboa.songinfo.moredetails.domain.entities.Card
import ayds.lisboa.songinfo.moredetails.data.local.sqldb.CardLocalStorage
import ayds.lisboa.songinfo.moredetails.domain.repository.CardRepository

class CardRepositoryImpl(
    private val cardLocalStorage: CardLocalStorage,
    private val broker: Broker
) : CardRepository {

    override fun getCards(artistName: String): List<Card> {
        var cards: List<Card>
        cards = cardLocalStorage.getCardList(artistName)
        when {
            (cards.isNotEmpty()) -> { markCardsAsLocal(cards) }
            else -> {
                cards = broker.getCards(artistName)
                saveCardInfo(cards)
            }
        }
        return cards
    }

    private fun saveCardInfo(cards: List<Card>) {
        cardLocalStorage.saveCardList(cards)
    }

    private fun markCardsAsLocal(cards: List<Card>){
        for (card in cards){
            if (card is Card.CardData)
                card.isLocallyStored = true
        }
    }

}