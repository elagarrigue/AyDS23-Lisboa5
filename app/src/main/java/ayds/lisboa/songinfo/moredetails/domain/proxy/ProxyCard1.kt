package ayds.lisboa.songinfo.moredetails.domain.proxy

import ayds.lisboa.songinfo.moredetails.domain.entities.Card
import ayds.lisboa.songinfo.moredetails.domain.entities.Source
import ayds.winchester3.wikiartist.artist.externalWikipedia.WIKIPEDIA_LOGO_URL
import ayds.winchester3.wikiartist.artist.externalWikipedia.WikipediaService

class ProxyCard1(
    private val wikipediaAPI: WikipediaService
) : ProxyCard{

    override fun getCard(artistName: String): Card {

        return try {
            val artistWikiInfo = wikipediaAPI.getArtist(artistName)
            when {
                (artistWikiInfo == null) -> Card.EmptyCard
                else -> Card.CardData(
                    Source.CARD1,
                    artistWikiInfo.description,
                    artistWikiInfo.wikipediaURL,
                    WIKIPEDIA_LOGO_URL
                )
            }
        } catch (e: Exception) {
            Card.EmptyCard
        }
    }
}