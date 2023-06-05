package ayds.lisboa.songinfo.moredetails.presentation

import ayds.lisboa.songinfo.moredetails.domain.entities.Card
import ayds.lisboa.songinfo.moredetails.domain.entities.Source
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Test


class CardDescriptionHelperTest {
    private val cardDescriptionHelper : CardDescriptionHelper by lazy { CardDescriptionHelperImpl() }

    @Test
    fun `given a non local artist it should return the description`() {
        val card = Card.CardData(
        Source.LASTFM,
        "Vida y obra de bizarrap",
        "infoUrl",
        "logoUrl"
        )

        val result = cardDescriptionHelper.getCardDescription(card,"artistName")

        val expected = "<html><div width=400><font face=\"arial\">Vida y obra de bizarrap</font></div></html>"
        assertEquals(expected, result)
    }

    @Test
    fun `given a local artist it should return the description with the prefix`() {
        val card = Card.CardData(
            Source.LASTFM,
            "Vida y obra de bizarrap",
            "infoUrl",
            "logoUrl",
            true
        )

        val result = cardDescriptionHelper.getCardDescription(card,"artistName")

        val expected = "<html><div width=400><font face=\"arial\">[*]Vida y obra de bizarrap</font></div></html>"
        assertEquals(expected, result)
    }

    @Test
    fun `given a artist that isn't in external service it should return the artist no results description`() {
        val card: Card = mockk()

        val result = cardDescriptionHelper.getCardDescription(card, "artistName")

        val expected = "No results"
        assertEquals(expected, result)
    }
}