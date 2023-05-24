package ayds.lisboa.songinfo.moredetails.presentation


import io.mockk.mockk
import lisboa5lastfm.Artist
import org.junit.Assert.*

import org.junit.Test

class ArtistDescriptionHelperTest {
    private val artistDescriptionHelper : ArtistDescriptionHelper by lazy { ArtistDescriptionHelperImpl() }

    @Test
    fun `given a non local artist it should return the description`() {
        val artist: Artist = Artist.ArtistData(
        "Bizarrap",
        "Vida y obra de bizarrap",
        "https://www.last.fm/music/Bizarrap",
        false
        )

        val result = artistDescriptionHelper.getArtistDescription(artist)

        val expected = "<html><div width=400><font face=\"arial\">Vida y obra de <b>BIZARRAP</b></font></div></html>"
        assertEquals(expected, result)
    }

    @Test
    fun `given a local artist it should return the description with the prefix`() {
        val artist: Artist = Artist.ArtistData(
            "Bizarrap",
            "Vida y obra de bizarrap",
            "https://www.last.fm/music/Bizarrap",
            true
        )

        val result = artistDescriptionHelper.getArtistDescription(artist)

        val expected = "<html><div width=400><font face=\"arial\">[*]Vida y obra de <b>BIZARRAP</b></font></div></html>"
        assertEquals(expected, result)
    }

    @Test
    fun `given a artist that isn't in LastFM it should return the artist no results description`() {
        val artist: Artist = mockk()

        val result = artistDescriptionHelper.getArtistDescription(artist)

        val expected = "No Results"
        assertEquals(expected, result)
    }
}