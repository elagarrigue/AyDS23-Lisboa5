package ayds.lisboa.songinfo.moredetails.presentation


import ayds.lisboa.songinfo.moredetails.domain.repository.ArtistRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import lisboa5lastfm.Artist

import org.junit.Test

class MoreDetailsPresenterTest {

    private val artistDescriptionHelper : ArtistDescriptionHelper = mockk(relaxUnitFun = true)

    private val repository: ArtistRepository = mockk()
    private val moreDetailsPresenter : MoreDetailsPresenter by lazy {
        MoreDetailsPresenterImpl(artistDescriptionHelper,repository)
    }

    @Test
    fun `when more details of an Artist are fetched should notify`() {
        val artist : Artist.ArtistData = Artist.ArtistData(
            "Artista",
            "Descripcion del artista",
            "url"
        )
        every { repository.getArtist("Artista") } returns artist
        every { artistDescriptionHelper.getArtistDescription(artist) } returns  "<html><div width=400><font face=\"arial\">Descripcion del <b>ARTISTA</b></font></div></html>"
        val artistTester: (MoreDetailsUiState) -> Unit = mockk(relaxed = true)

        moreDetailsPresenter.artistObservable.subscribe {
            artistTester(it)
        }
        moreDetailsPresenter.moreDetails("Artista")

        val moreDetailsUiStateExpected = MoreDetailsUiState(
            "Artista",
            "<html><div width=400><font face=\"arial\">Descripcion del <b>ARTISTA</b></font></div></html>",
            "url"
        )
        verify { artistTester(moreDetailsUiStateExpected) }

    }

    @Test
    fun `when more details of an EmptyArtist are fetched should notify`() {
        val artist : Artist = Artist.EmptyArtist
        every { repository.getArtist("artistName") } returns artist
        every { artistDescriptionHelper.getArtistDescription(artist) } returns ""
        val artistTester: (MoreDetailsUiState) -> Unit = mockk(relaxed = true)

        moreDetailsPresenter.artistObservable.subscribe {
            artistTester(it)
        }
        moreDetailsPresenter.moreDetails("artistName")

        val moreDetailsUiStateExpected = MoreDetailsUiState(
            "",
            "No Results",
            ""
        )
        verify { artistTester(moreDetailsUiStateExpected) }
    }
}