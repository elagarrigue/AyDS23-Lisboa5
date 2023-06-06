package ayds.lisboa.songinfo.moredetails.data.external.proxy

import ayds.lisboa.songinfo.moredetails.domain.entities.Card
import ayds.lisboa.songinfo.moredetails.domain.entities.Source
import ayds.winchester3.wikiartist.artist.externalWikipedia.WIKIPEDIA_LOGO_URL
import ayds.winchester3.wikiartist.artist.externalWikipedia.WikipediaArtist
import ayds.winchester3.wikiartist.artist.externalWikipedia.WikipediaService

class WikipediaProxy(
    private val wikipediaAPI: WikipediaService
    ) : ProxyCard {

        override fun getCard(artistName: String): Card {

            return try {
                val artistWikiInfo = wikipediaAPI.getArtist(artistName)
                createCardData(artistWikiInfo)
            } catch (e: Exception) {
                Card.EmptyCard
            }
        }

    private fun createCardData(artistWikiInfo: WikipediaArtist?): Card {
        if (artistWikiInfo != null) {
            return Card.CardData(
                Source.WIKIPEDIA,
                artistWikiInfo.description,
                artistWikiInfo.wikipediaURL,
                WIKIPEDIA_LOGO_URL
            )
        }
        return Card.EmptyCard
    }

    }

