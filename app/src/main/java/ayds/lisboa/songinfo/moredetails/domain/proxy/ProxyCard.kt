package ayds.lisboa.songinfo.moredetails.domain.proxy

import ayds.aknewyork.external.service.data.NYTimesService
import ayds.aknewyork.external.service.data.entities.ArtistDataExternal
import ayds.aknewyork.external.service.data.entities.NYT_LOGO_URL
import ayds.lisboa.songinfo.moredetails.domain.entities.Card
import ayds.lisboa.songinfo.moredetails.domain.entities.Source
import ayds.winchester3.wikiartist.artist.externalWikipedia.WIKIPEDIA_LOGO_URL
import ayds.winchester3.wikiartist.artist.externalWikipedia.WikipediaService
import lisboa5lastfm.LASTFM_LOGO_URL
import lisboa5lastfm.artist.ArtistExternalService

interface ProxyCard {
    fun getCard(artistName: String): Card
}

internal class WikipediaProxy(
    private val wikipediaAPI: WikipediaService
) : ProxyCard {

    override fun getCard(artistName: String): Card {

        return try {
            val artistWikiInfo = wikipediaAPI.getArtist(artistName)
            when {
                (artistWikiInfo == null) -> Card.EmptyCard
                else -> Card.CardData(
                    Source.WIKIPEDIA,
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

internal class LastFMProxy(
    private val lastFMAPI: ArtistExternalService
) : ProxyCard {
    override fun getCard(artistName: String): Card {
        return try {
            val artistLastFMInfo = lastFMAPI.getArtistFromLastFMAPI(artistName)
            when {
                (artistLastFMInfo == null) -> Card.EmptyCard
                else -> Card.CardData(
                    Source.LASTFM,
                    artistLastFMInfo.artistBioContent,
                    artistLastFMInfo.artistURL,
                    LASTFM_LOGO_URL,
                )
            }
        } catch (e: Exception) {
            Card.EmptyCard
        }
    }
}

internal class NYTimesProxy(
    private val nyTimesService: NYTimesService
) : ProxyCard{

    override fun getCard(artistName: String): Card {
        return try {
            val artistAPIInfo = nyTimesService.getArtistInfo(artistName)

            when {
                (artistAPIInfo is ArtistDataExternal.ArtistWithDataExternal) -> Card.CardData(
                    Source.NY_TIMES,
                    artistAPIInfo.info!!,
                    artistAPIInfo.url,
                    NYT_LOGO_URL
                )
                else -> Card.EmptyCard
            }
        } catch (e : Exception){
            Card.EmptyCard
        }
    }
}