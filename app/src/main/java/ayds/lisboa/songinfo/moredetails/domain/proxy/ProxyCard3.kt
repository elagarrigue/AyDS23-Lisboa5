package ayds.lisboa.songinfo.moredetails.domain.proxy

import ayds.aknewyork.external.service.data.NYTimesService
import ayds.aknewyork.external.service.data.entities.ArtistDataExternal
import ayds.aknewyork.external.service.data.entities.NYT_LOGO_URL
import ayds.lisboa.songinfo.moredetails.domain.entities.Card
import ayds.lisboa.songinfo.moredetails.domain.entities.Source

class ProxyCard3(
    private val nyTimesService: NYTimesService
) : ProxyCard{

    override fun getCard(artistName: String): Card {
        return try {
            val artistAPIInfo = nyTimesService.getArtistInfo(artistName)

            when {
                (artistAPIInfo is ArtistDataExternal.ArtistWithDataExternal) -> Card.CardData(
                    Source.CARD3,
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