package ayds.lisboa.songinfo.moredetails.domain.proxy

import ayds.lisboa.songinfo.moredetails.domain.entities.Card
import ayds.winchester3.wikiartist.artist.externalWikipedia.WIKIPEDIA_LOGO_URL
import ayds.winchester3.wikiartist.artist.externalWikipedia.WikipediaService

class ProxyWikipedia(
    private val wikipediaAPI: WikipediaService
) {

    fun getCard(artistName: String): Card {
        val artistWikiInfo = wikipediaAPI.getArtist(artistName)
        return when {
            (artistWikiInfo == null) -> Card.EmptyCard
            else -> Card.CardData(
                "Wikipedia",
                artistWikiInfo.description,
                artistWikiInfo.wikipediaURL,
                WIKIPEDIA_LOGO_URL
            )
        }
    }
}