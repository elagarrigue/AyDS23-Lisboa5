package ayds.lisboa.songinfo.moredetails.domain.proxy

import ayds.aknewyork.external.service.data.NYTimesService
import ayds.aknewyork.external.service.data.entities.ArtistDataExternal
import ayds.aknewyork.external.service.data.entities.NYT_LOGO_URL
import ayds.lisboa.songinfo.moredetails.domain.entities.Card
class ProxyNYTimes(
    private val nyTimesService: NYTimesService  //Importar de la libreria New York Times
) {

    fun getCard(artistName: String): Card {
        val artistAPIInfo = nyTimesService.getArtistInfo(artistName)
        return when {
            (artistAPIInfo is ArtistDataExternal.ArtistWithDataExternal) -> Card.CardData(
                "New York Times",
                artistAPIInfo.info!!,
                artistAPIInfo.url,
                NYT_LOGO_URL
            )
            else -> Card.EmptyCard
        }
    }
}