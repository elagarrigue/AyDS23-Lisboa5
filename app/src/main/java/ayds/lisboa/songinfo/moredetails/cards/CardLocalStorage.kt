package ayds.lisboa.songinfo.moredetails.cards

interface CardLocalStorage {

    fun getCardList(artistName: String): List<Card>

    fun saveCardList(cardList: List<Card>)
}