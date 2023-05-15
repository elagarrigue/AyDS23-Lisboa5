package ayds.lisboa.songinfo.moredetails.presentation

import ayds.lisboa.songinfo.home.controller.HomeControllerImpl
import ayds.lisboa.songinfo.home.model.HomeModel
import ayds.lisboa.songinfo.home.model.entities.Song
import ayds.lisboa.songinfo.home.view.HomeUiEvent
import ayds.lisboa.songinfo.home.view.HomeUiState
import ayds.lisboa.songinfo.home.view.HomeView
import ayds.lisboa.songinfo.moredetails.domain.entities.Artist
import ayds.lisboa.songinfo.moredetails.injector.MoreDetailsInjector
import ayds.observer.Subject
import io.mockk.InternalPlatformDsl.toArray
import io.mockk.InternalPlatformDsl.toStr
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test
import kotlin.math.log

class MoreDetailsPresenterTest {
    private val homeModel: HomeModel = mockk(relaxUnitFun = true)

    private val artistDescriptionHelper : ArtistDescriptionHelper = mockk(relaxUnitFun = true);

    private val onActionSubject = Subject<HomeUiEvent>()
    private val homeView: HomeView = mockk(relaxUnitFun = true) {
        every { uiEventObservable } returns onActionSubject
    }

    private val homeController by lazy {
        HomeControllerImpl(homeModel)
    }
    private val moreDetailsPresenter : MoreDetailsPresenter = mockk(relaxUnitFun = true)
    @Before
    fun setup() {
        homeController.setHomeView(homeView)
    }
    @Test
    fun `when the more details of an LastFM artist that is not store locally are updated should update`() {

    }
    @Test
    fun `when the more details of an locally store artist are updated should update`() {

    }
    @Test
    fun `when the more details of an empty artist are updated should stay update the bio content with no result`() {

    }
}