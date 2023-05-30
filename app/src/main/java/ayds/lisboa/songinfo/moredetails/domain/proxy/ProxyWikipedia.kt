package ayds.lisboa.songinfo.moredetails.domain.proxy

import ayds.lisboa.songinfo.moredetails.domain.entities.Card
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
                "artistWikiInfo.getImageURL()" //No esta el logo de la URL en el repo
            )
        }
    }
}