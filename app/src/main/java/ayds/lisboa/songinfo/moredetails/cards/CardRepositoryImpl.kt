package ayds.lisboa.songinfo.moredetails.cards

class CardRepositoryImpl(
    private val cardLocalStorage: CardLocalStorage,
    private val broker: Broker
) : CardRepository {

    override fun getCards(artistName: String): List<Card> {
        var cards: List<Card>
        cards = cardLocalStorage.getCardList(artistName)
        var someCardData = false
        for (c in cards) {
            if (c is Card.CardData) {
                someCardData = true
            }
        }
        when {
            (someCardData) -> { markCardsAsLocal(cards) } //Hay que hacer esto??
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
}