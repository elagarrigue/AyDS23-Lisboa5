package ayds.lisboa.songinfo.moredetails.domain.proxy

import ayds.lisboa.songinfo.moredetails.domain.entities.Card

interface ProxyCard {
    fun getCard(artistName: String): Card
}