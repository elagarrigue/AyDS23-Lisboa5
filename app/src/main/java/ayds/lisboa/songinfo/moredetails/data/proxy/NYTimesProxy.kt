package ayds.lisboa.songinfo.moredetails.data.proxy

import ayds.lisboa.songinfo.moredetails.domain.entities.Card
import ayds.lisboa.songinfo.moredetails.domain.entities.Source
import lisboa5lastfm.Artist

class NYTimesProxy(
    private val nyTimesService: NYTimesService
) : ProxyCard {

    override fun getCard(artistName: String): Card {
        return try {
            val artistAPIInfo = nyTimesService.getArtistInfo(artistName)
            createCardData(artistAPIInfo)
        } catch (e : Exception){
            Card.EmptyCard
        }
    }

    private fun createCardData(artistAPIInfo: Artist.ArtistData?): Card {
        if (artistAPIInfo is ArtistDataExternal.ArtistWithDataExternal) {
         return  Card.CardData(
            Source.NY_TIMES,
            artistAPIInfo.info!!,
            artistAPIInfo.url,
            NYT_LOGO_URL
        )
        }
       return Card.EmptyCard
    }
}