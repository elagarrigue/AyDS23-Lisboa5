package ayds.lisboa.songinfo.moredetails.domain

import ayds.lisboa.songinfo.moredetails.domain.proxy.ProxyNYTimes
import ayds.lisboa.songinfo.moredetails.domain.proxy.ProxyLastFM
import ayds.lisboa.songinfo.moredetails.domain.proxy.ProxyWikipedia
import ayds.lisboa.songinfo.moredetails.domain.entities.Card

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

        return cardList.filterIsInstance<Card.CardData>()
    }
}