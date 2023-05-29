package ayds.lisboa.songinfo.moredetails.cards.proxy

import ayds.lisboa.songinfo.moredetails.cards.Card

class ProxyNYTimes(
    private val NYTimesAPI: ArtistExternalService //Importar de la libreria New York Times
) {

    fun getCard(artistName: String): Card {
        var artistAPIInfo = NYTimesAPI.getArtistFrom3API(artistName)
        return when {
            (artistAPIInfo == null) -> Card.EmptyCard
            else -> Card.CardData(
                "New York Times",
                artistAPIInfo.getInfo(),
                artistAPIInfo.getURL(),
                artistAPIInfo.getImageURL()
            )
        }
    }
}