package ayds.lisboa.songinfo.moredetails.cards

interface CardRepository {

    fun getCards(artistName: String): List<Card>
}