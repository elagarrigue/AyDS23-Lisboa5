package ayds.lisboa.songinfo.moredetails.data

import ayds.lisboa.songinfo.moredetails.data.external.CardsBroker
import ayds.lisboa.songinfo.moredetails.data.local.sqldb.CardLocalStorage
import ayds.lisboa.songinfo.moredetails.domain.entities.Card
import ayds.lisboa.songinfo.moredetails.domain.entities.Source
import ayds.lisboa.songinfo.moredetails.domain.repository.CardRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.*
import org.junit.Test

class CardRepositoryTest {

    private val cardLocalStorage: CardLocalStorage = mockk(relaxUnitFun = true)
    private val cardsBroker: CardsBroker = mockk(relaxUnitFun = true)

    private val cardRepository: CardRepository by lazy {
        CardRepositoryImpl(cardLocalStorage, cardsBroker)
    }

    @Test
    fun `given existing artist by artistName should return artists cards and mark it as local`() {
        val card = Card.CardData(
            Source.LASTFM,
            "description",
            "url",
            "logoUrl",
            false
        )
        val cards = ArrayList<Card.CardData>()
        cards.add(card)
        cards.add(card)
        cards.add(card)
        every{ cardLocalStorage.getCardList("artistName")} returns cards

        val result = cardRepository.getCards("artistName")

        assertEquals(cards, result)
        for (c in cards)
            assertTrue(c.isLocallyStored)
    }

    @Test
    fun `given non existing artist in the LocalStorage by artistName should get the artist from the external service and store it`() {
        val card = Card.CardData(
            Source.LASTFM,
            "description",
            "url",
            "logoUrl",
            false
        )
        val cards = ArrayList<Card.CardData>()
        cards.add(card)
        cards.add(card)
        cards.add(card)
        every { cardLocalStorage.getCardList("artistName") } returns ArrayList() //retoraba artista vacio
        every {cardsBroker.getCards("artistName") } returns cards

        val result = cardRepository.getCards("artistName")

        assertEquals(cards, result)
        for (c in cards)
            assertFalse(c.isLocallyStored)
        verify{cardLocalStorage.saveCardList(cards, "artistName")}
    }

    @Test
    fun `given non existing artist in the local storage and the external service by artistName should return an empty list `(){
        val cards = ArrayList<Card.EmptyCard>()
        every { cardLocalStorage.getCardList("artistName") } returns ArrayList()
        every {cardsBroker.getCards("artistName")} returns ArrayList()

        val result = cardRepository.getCards("artistName")

        assertEquals(cards, result)
    }

    @Test
    fun `given service exception should return empty list`(){
        every { cardLocalStorage.getCardList("artistName") } returns ArrayList()
        every {cardsBroker.getCards("artistName")} returns ArrayList()

        val result = cardRepository.getCards("artistName")

        assertEquals(ArrayList<Card.EmptyCard>(), result)
    }
}