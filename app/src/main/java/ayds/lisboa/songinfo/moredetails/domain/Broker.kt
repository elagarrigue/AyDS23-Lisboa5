package ayds.lisboa.songinfo.moredetails.domain

import ayds.lisboa.songinfo.moredetails.domain.proxy.ProxyCard3
import ayds.lisboa.songinfo.moredetails.domain.proxy.ProxyCard1
import ayds.lisboa.songinfo.moredetails.domain.entities.Card
import ayds.lisboa.songinfo.moredetails.domain.proxy.ProxyCard2

interface Broker{
    fun getCards(artistName: String): List<Card>
}
class BrokerImpl(
    private val proxyCard1: ProxyCard1,
    private val proxyCard2: ProxyCard2,
    private val proxyCard3: ProxyCard3
) : Broker{

    override fun getCards(artistName: String): List<Card> {
        val cardList = ArrayList<Card>()

        cardList.add(proxyCard1.getCard(artistName))
        cardList.add(proxyCard2.getCard(artistName))
        cardList.add(proxyCard3.getCard(artistName))

        return cardList.filterIsInstance<Card.CardData>()
    }
}