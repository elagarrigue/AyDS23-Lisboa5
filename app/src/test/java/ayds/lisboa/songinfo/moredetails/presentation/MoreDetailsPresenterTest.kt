package ayds.lisboa.songinfo.moredetails.presentation

import ayds.lisboa.songinfo.home.controller.HomeControllerImpl
import ayds.lisboa.songinfo.home.model.HomeModel
import ayds.lisboa.songinfo.home.model.entities.Song
import ayds.lisboa.songinfo.home.view.HomeUiEvent
import ayds.lisboa.songinfo.home.view.HomeUiState
import ayds.lisboa.songinfo.home.view.HomeView
import ayds.lisboa.songinfo.moredetails.domain.entities.Artist
import ayds.lisboa.songinfo.moredetails.domain.repository.ArtistRepository
import ayds.lisboa.songinfo.moredetails.injector.MoreDetailsInjector
import ayds.observer.Subject
import io.mockk.InternalPlatformDsl.toArray
import io.mockk.InternalPlatformDsl.toStr
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test
import kotlin.math.log

class MoreDetailsPresenterTest {

    private val artistDescriptionHelper : ArtistDescriptionHelper = spyk(ArtistDescriptionHelperImpl());
    private val moreDetailsPresenter : MoreDetailsPresenter = spyk(MoreDetailsPresenterImpl(artistDescriptionHelper))

    private var onActionSubject = Subject<MoreDetailsUiState>()

    @Before
    fun setup() {

    }
    @Test
    fun `when the more details of an LastFM artist that is not store locally are updated should update`() {
        val artist : Artist.ArtistData = Artist.ArtistData(
            "Bizarrap",
            "Gonzalo Julián Conde, conocido artísticamente como Bizarrap, es un productor musical y DJ argentino. Se especializa en géneros como el trap, la música electrónica y el rap. Es conocido por sus Bzrp Music Session y sus Bzrp Freestyle Sessions, las que realiza junto a distintos artistas <a href=\"https://www.last.fm/music/Bizarrap\">Read more on Last.fm</a>. User-contributed text is available under the Creative Commons By-SA License; additional terms may apply.",
            "https://www.last.fm/music/Bizarrap",
            false)
        val expected = MoreDetailsUiState(
            "Bizarrap",
            "Gonzalo Julián Conde, conocido artísticamente como Bizarrap, es un productor musical y DJ argentino. Se especializa en géneros como el trap, la música electrónica y el rap. Es conocido por sus Bzrp Music Session y sus Bzrp Freestyle Sessions, las que realiza junto a distintos artistas <a href=\"https://www.last.fm/music/Bizarrap\">Read more on Last.fm</a>. User-contributed text is available under the Creative Commons By-SA License; additional terms may apply.",
            "https://www.last.fm/music/Bizarrap",
        )
        val artistName = artist.artistName
        val repository : ArtistRepository = mockk()
        every { repository.getArtist("Bizarrap") } returns artist
        moreDetailsPresenter.setRepository(repository)
        moreDetailsPresenter.moreDetails(artistName)
        onActionSubject.notify(expected)

        val result = onActionSubject.lastValue()
        assertTrue(onActionSubject == moreDetailsPresenter.artistObservable);
    }
    @Test
    fun `when the more details of an locally store artist are updated should update`() {

    }
    @Test
    fun `when the more details of an empty artist are updated should stay update the bio content with no result`() {

    }
}