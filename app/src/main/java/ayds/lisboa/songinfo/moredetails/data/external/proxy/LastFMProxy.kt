package ayds.lisboa.songinfo.moredetails.data.external.proxy

import ayds.lisboa.songinfo.moredetails.domain.entities.Card
import ayds.lisboa.songinfo.moredetails.domain.entities.Source
import lisboa5lastfm.Artist
import lisboa5lastfm.LASTFM_LOGO_URL
import lisboa5lastfm.artist.ArtistExternalService

class LastFMProxy(
    private val lastFMAPI: ArtistExternalService
) : ProxyCard {
    override fun getCard(artistName: String): Card {
        return try {
            val artistLastFMInfo = lastFMAPI.getArtistFromLastFMAPI(artistName)
            createCardData(artistLastFMInfo)
        } catch (e: Exception) {
            Card.EmptyCard
        }
    }

    private fun createCardData(artistLastFMInfo: Artist.ArtistData?): Card {
        if (artistLastFMInfo != null) {
            return Card.CardData(
                Source.LASTFM,
                artistLastFMInfo.artistBioContent,
                artistLastFMInfo.artistURL,
                LASTFM_LOGO_URL,
            )
        }
        return Card.EmptyCard
    }
}