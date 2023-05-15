package ayds.lisboa.songinfo.moredetails.presentation

import ayds.lisboa.songinfo.moredetails.domain.entities.Artist
import ayds.lisboa.songinfo.moredetails.injector.MoreDetailsInjector
import io.mockk.InternalPlatformDsl.toArray
import io.mockk.InternalPlatformDsl.toStr
import io.mockk.mockk
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test

class MoreDetailsPresenterTest {
    private val artistDescriptionHelper : ArtistDescriptionHelper = mockk(relaxUnitFun = true);
    private val moreDetailsPresenter : MoreDetailsPresenter by lazy {
        MoreDetailsPresenterImpl(artistDescriptionHelper)
    }
    private val moreDetailsInjector = MoreDetailsInjector
    private val moreDetailsView : MoreDetailsView = MoreDetailsViewActivity();
    @Before
    fun setup (){
        moreDetailsInjector.init(moreDetailsView)
    }
    @Test
    fun `when the more details of an LastFM artist that is not store locally are updated should update`() {
        val artist: Artist.ArtistData = Artist.ArtistData(
            "Bizarrap",
            "Gonzalo Julián Conde, conocido artísticamente como Bizarrap, es un productor musical y DJ argentino. Se especializa en géneros como el trap, la música electrónica y el rap. Es conocido por sus Bzrp Music Session y sus Bzrp Freestyle Sessions, las que realiza junto a distintos artistas <a href=\"https://www.last.fm/music/Bizarrap\">Read more on Last.fm</a>. User-contributed text is available under the Creative Commons By-SA License; additional terms may apply.",
            "https://www.last.fm/music/Bizarrap",
            false
        )
        val expected = MoreDetailsUiState(
            "Bizarrap",
            "Gonzalo Julián Conde, conocido artísticamente como Bizarrap, es un productor musical y DJ argentino. Se especializa en géneros como el trap, la música electrónica y el rap. Es conocido por sus Bzrp Music Session y sus Bzrp Freestyle Sessions, las que realiza junto a distintos artistas <a href=\"https://www.last.fm/music/Bizarrap\">Read more on Last.fm</a>. User-contributed text is available under the Creative Commons By-SA License; additional terms may apply.",
            "https://www.last.fm/music/Bizarrap",

        )

        moreDetailsPresenter.moreDetails(artist.artistName)

        val result = MoreDetailsUiState()
        assertEquals(expected, result)
    }
}