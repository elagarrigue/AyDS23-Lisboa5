package ayds.lisboa.songinfo.moredetails.data.external.proxy

import ayds.lisboa.songinfo.moredetails.domain.entities.Card

interface ProxyCard {
    fun getCard(artistName: String): Card
}
