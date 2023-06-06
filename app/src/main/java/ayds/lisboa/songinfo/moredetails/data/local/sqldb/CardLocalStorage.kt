package ayds.lisboa.songinfo.moredetails.data.local.sqldb

import ayds.lisboa.songinfo.moredetails.domain.entities.Card

interface CardLocalStorage {

    fun getCardList(artistName: String): List<Card.CardData>

    fun saveCardList(cardList: List<Card>,artistName: String)
}