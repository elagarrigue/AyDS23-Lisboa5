package ayds.lisboa.songinfo.moredetails.cards.proxy

import ayds.lisboa.songinfo.moredetails.cards.Card
import lisboa5lastfm.artist.ArtistExternalService

class ProxyLastFM(
    private val lastFMAPI: ArtistExternalService
) {

    fun getCard(artistName: String): Card {
        var artistLastFMInfo = lastFMAPI.getArtistFromLastFMAPI(artistName)
        return when {
            (artistLastFMInfo == null) -> Card.EmptyCard
            else -> Card.CardData(
                "LastFM",
                artistLastFMInfo.artistBioContent,
                artistLastFMInfo.artistURL,
                artistLastFMInfo.ImageURL, //Como obtener ImageURL???
            )
        }
}