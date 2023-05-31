package ayds.lisboa.songinfo.moredetails.domain.proxy

import ayds.lisboa.songinfo.moredetails.domain.entities.Card
import ayds.lisboa.songinfo.moredetails.domain.entities.Source
import lisboa5lastfm.LASTFM_LOGO_URL
import lisboa5lastfm.artist.ArtistExternalService

class ProxyCard2(
    private val lastFMAPI: ArtistExternalService
) : ProxyCard {
    override fun getCard(artistName: String): Card {
        return try {
            val artistLastFMInfo = lastFMAPI.getArtistFromLastFMAPI(artistName)
            when {
                (artistLastFMInfo == null) -> Card.EmptyCard
                else -> Card.CardData(
                    Source.CARD2,
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