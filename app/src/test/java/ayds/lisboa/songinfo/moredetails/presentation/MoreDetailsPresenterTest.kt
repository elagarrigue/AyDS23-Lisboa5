package ayds.lisboa.songinfo.moredetails.presentation


import ayds.lisboa.songinfo.moredetails.domain.entities.Card
import ayds.lisboa.songinfo.moredetails.domain.entities.Source
import ayds.lisboa.songinfo.moredetails.domain.repository.CardRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify

import org.junit.Test

class MoreDetailsPresenterTest {

    private val cardDescriptionHelper : CardDescriptionHelper = mockk(relaxUnitFun = true)
    private val cardSourceFactory : CardSourceFactory = mockk()
    private val repository: CardRepository = mockk()
    private val moreDetailsPresenter : MoreDetailsPresenter by lazy {
        MoreDetailsPresenterImpl(cardDescriptionHelper,repository,cardSourceFactory)
    }

    @Test
    fun `when more details of an Artist are fetched should notify`() {
        val card = Card.CardData (
            Source.LASTFM,
            "Descripcion del artista",
            "infoUrl",
            "logoUrl"
        )
        val cards = ArrayList<Card.CardData>()
        cards.add(card)
        cards.add(card)
        cards.add(card)
        every { repository.getCards("Artista") } returns cards
        every { cardDescriptionHelper.getCardDescription(card,"Artista") } returns  "<html><div width=400><font face=\"arial\">Descripcion del <b>ARTISTA</b></font></div></html>"
        every { cardSourceFactory.getSourceTitle(Source.LASTFM) } returns "Last FM"
        val artistTester: (List<CardUiState>) -> Unit = mockk(relaxed = true)

        moreDetailsPresenter.artistObservable.subscribe {
            artistTester(it)
        }
        moreDetailsPresenter.moreDetails("Artista")

        val cardUiStateExpected = CardUiState(
            Source.LASTFM,
            "<html><div width=400><font face=\"arial\">Descripcion del <b>ARTISTA</b></font></div></html>",
            "infoUrl",
            "logoUrl",
            "Source: Last FM"
        )
        val cardUiStateListExpected = ArrayList<CardUiState>()
        cardUiStateListExpected.add (cardUiStateExpected)
        cardUiStateListExpected.add(cardUiStateExpected)
        cardUiStateListExpected.add(cardUiStateExpected)
        verify { artistTester(cardUiStateListExpected) }

    }

    @Test
    fun `when more details of an EmptyArtist are fetched should notify`() {
        val card = Card.EmptyCard
        val cards = ArrayList<Card.EmptyCard>()
        cards.add(card)
        cards.add(card)
        cards.add(card)
        every { repository.getCards("artistName") } returns cards
        every { cardDescriptionHelper.getCardDescription(card, "artistName") } returns "No results"

        val artistTester: (List<CardUiState>) -> Unit = mockk(relaxed = true)

        moreDetailsPresenter.artistObservable.subscribe {
            artistTester(it)
        }
        moreDetailsPresenter.moreDetails("artistName")

        val cardUiStateExpected = CardUiState(
            Source.EMPTY_SOURCE,
            "No results",
            "",
            ""
        )
        val cardUiStateListExpected = ArrayList<CardUiState>()
        cardUiStateListExpected.add(cardUiStateExpected)
        cardUiStateListExpected.add(cardUiStateExpected)
        cardUiStateListExpected.add(cardUiStateExpected)
        verify { artistTester(cardUiStateListExpected) }
    }
}