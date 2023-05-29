package ayds.lisboa.songinfo.moredetails.cards

import ayds.lisboa.songinfo.moredetails.cards.proxy.ProxyNYTimes
import ayds.lisboa.songinfo.moredetails.cards.proxy.ProxyLastFM
import ayds.lisboa.songinfo.moredetails.cards.proxy.ProxyWikipedia

class Broker(
    private val proxyWiki: ProxyWikipedia,
    private val proxyLastFM: ProxyLastFM,
    private val proxyNYTimes: ProxyNYTimes
) {

    fun getCards(artistName: String): List<Card> {
        val cardList = ArrayList<Card>()

        cardList.add(proxyWiki.getCard(artistName))
        cardList.add(proxyLastFM.getCard(artistName))
        cardList.add(proxyNYTimes.getCard(artistName))

        return cardList
    }
}