package ayds.lisboa.songinfo.moredetails.domain

import ayds.lisboa.songinfo.moredetails.domain.entities.Card
import ayds.lisboa.songinfo.moredetails.domain.proxy.ProxyCard

interface Broker {
    fun getCards(artistName: String): List<Card>
}

class BrokerImpl(
    private val proxyCard1: ProxyCard,
    private val proxyCard2: ProxyCard,
    private val proxyCard3: ProxyCard
) : Broker {

    override fun getCards(artistName: String): List<Card> {
        val cardList = ArrayList<Card>()

        cardList.add(proxyCard1.getCard(artistName))
        cardList.add(proxyCard2.getCard(artistName))
        cardList.add(proxyCard3.getCard(artistName))

        return cardList.filterIsInstance<Card.CardData>()
    }
}