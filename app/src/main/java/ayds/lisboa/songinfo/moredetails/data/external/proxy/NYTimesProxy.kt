package ayds.lisboa.songinfo.moredetails.data.external.proxy

import ayds.aknewyork.external.service.data.NYTimesService
import ayds.aknewyork.external.service.data.entities.ArtistDataExternal
import ayds.aknewyork.external.service.data.entities.NYT_LOGO_URL
import ayds.lisboa.songinfo.moredetails.domain.entities.Card
import ayds.lisboa.songinfo.moredetails.domain.entities.Source

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

    private fun createCardData(artistAPIInfo: ArtistDataExternal?): Card {
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