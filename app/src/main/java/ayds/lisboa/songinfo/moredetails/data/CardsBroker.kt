package ayds.lisboa.songinfo.moredetails.data

import ayds.lisboa.songinfo.moredetails.data.proxy.ProxyCard
import ayds.lisboa.songinfo.moredetails.domain.entities.Card

interface CardsBroker {
    fun getCards(artistName: String): List<Card>
}

internal class CardsBrokerImpl(
    private val proxyCard1: ProxyCard,
    private val proxyCard2: ProxyCard,
    private val proxyCard3: ProxyCard
) :CardsBroker {

    override fun getCards(artistName: String): List<Card> {
        val cardList = ArrayList<Card>()

        cardList.add(proxyCard1.getCard(artistName))
        cardList.add(proxyCard2.getCard(artistName))
        cardList.add(proxyCard3.getCard(artistName))

        return cardList.filterIsInstance<Card.CardData>()
    }
}