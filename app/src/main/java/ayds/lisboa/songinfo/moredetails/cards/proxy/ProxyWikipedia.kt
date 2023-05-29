package ayds.lisboa.songinfo.moredetails.cards.proxy

import ayds.lisboa.songinfo.moredetails.cards.Card

class ProxyWikipedia(
    private val wikipediaAPI: ArtistExternalService //De libreria Wikipedia
) {

    fun getCard(artistName: String): Card {
        var artistWikiInfo = wikipediaAPI.getArtistFromWikipediaAPI(artistName)
        return when {
            (artistWikiInfo == null) -> Card.EmptyCard
            else -> Card.CardData(
                "Wikipedia",
                artistWikiInfo.getInfo(),
                artistWikiInfo.getURL(),
                artistWikiInfo.getImageURL()
            )
        }
    }
}