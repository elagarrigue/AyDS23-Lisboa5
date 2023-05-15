package ayds.lisboa.songinfo.moredetails.presentation

import ayds.lisboa.songinfo.home.model.entities.Song
import ayds.lisboa.songinfo.moredetails.domain.entities.Artist
import io.mockk.mockk
import org.junit.Assert.*

import org.junit.Test

class ArtistDescriptionHelperTest {
    private val artistDescriptionHelper : ArtistDescriptionHelper by lazy { ArtistDescriptionHelperImpl() }
    @Test
    fun `given a non local artist it should return the description`() {
        val artist: Artist = Artist.ArtistData(
        "Bizarrap",
        "Gonzalo Julián Conde, conocido artísticamente como Bizarrap, es un productor musical y DJ argentino. Se especializa en géneros como el trap, la música electrónica y el rap. Es conocido por sus Bzrp Music Session y sus Bzrp Freestyle Sessions, las que realiza junto a distintos artistas <a href=\"https://www.last.fm/music/Bizarrap\">Read more on Last.fm</a>. User-contributed text is available under the Creative Commons By-SA License; additional terms may apply.",
        "https://www.last.fm/music/Bizarrap",
        false
        )
        val result = artistDescriptionHelper.getArtistDescription(artist);
        val expected = "<html><div width=400><font face=\"arial\">Gonzalo Julián Conde, conocido artísticamente como <b>BIZARRAP</b>, es un productor musical y DJ argentino. Se especializa en géneros como el trap, la música electrónica y el rap. Es conocido por sus Bzrp Music Session y sus Bzrp Freestyle Sessions, las que realiza junto a distintos artistas <a href=\"https://www.last.fm/music/<b>BIZARRAP</b>\">Read more on Last.fm</a>. User-contributed text is available under the Creative Commons By-SA License; additional terms may apply.</font></div></html>"
        assertEquals(expected, result)
    }
    @Test
    fun `given a local artist it should return the description with the prefix`() {
        val artist: Artist = Artist.ArtistData(
            "Bizarrap",
            "Gonzalo Julián Conde, conocido artísticamente como Bizarrap, es un productor musical y DJ argentino. Se especializa en géneros como el trap, la música electrónica y el rap. Es conocido por sus Bzrp Music Session y sus Bzrp Freestyle Sessions, las que realiza junto a distintos artistas <a href=\"https://www.last.fm/music/Bizarrap\">Read more on Last.fm</a>. User-contributed text is available under the Creative Commons By-SA License; additional terms may apply.",
            "https://www.last.fm/music/Bizarrap",
            true
        )
        val result = artistDescriptionHelper.getArtistDescription(artist);
        val expected = "<html><div width=400><font face=\"arial\">[*]Gonzalo Julián Conde, conocido artísticamente como <b>BIZARRAP</b>, es un productor musical y DJ argentino. Se especializa en géneros como el trap, la música electrónica y el rap. Es conocido por sus Bzrp Music Session y sus Bzrp Freestyle Sessions, las que realiza junto a distintos artistas <a href=\"https://www.last.fm/music/<b>BIZARRAP</b>\">Read more on Last.fm</a>. User-contributed text is available under the Creative Commons By-SA License; additional terms may apply.</font></div></html>"
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