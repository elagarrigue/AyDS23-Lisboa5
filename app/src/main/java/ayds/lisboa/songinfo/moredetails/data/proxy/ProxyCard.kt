package ayds.lisboa.songinfo.moredetails.data.proxy

import ayds.lisboa.songinfo.moredetails.domain.entities.Card

interface ProxyCard {
    fun getCard(artistName: String): Card
}
