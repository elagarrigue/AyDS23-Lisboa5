package ayds.lisboa.songinfo.moredetails.presentation

import ayds.lisboa.songinfo.moredetails.domain.entities.Artist
import ayds.lisboa.songinfo.moredetails.domain.repository.ArtistRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify

import org.junit.Test

class MoreDetailsPresenterTest {

    private val artistDescriptionHelper : ArtistDescriptionHelper = spyk(ArtistDescriptionHelperImpl())

    private val repository: ArtistRepository = mockk()
    private val moreDetailsPresenter : MoreDetailsPresenter by lazy {
        MoreDetailsPresenterImpl(artistDescriptionHelper,repository)
    }

    @Test
    fun `when more details of an Artist are fetched should notify`() {
        val artist : Artist = Artist.ArtistData(
            "Artista","descripcion del artista","url",false
        )
        every { repository.getArtist("artistName") } returns artist
        val artistTester: (MoreDetailsUiState) -> Unit = mockk(relaxed = true)
        moreDetailsPresenter.artistObservable.subscribe {
            artistTester(it)
        }
        moreDetailsPresenter.moreDetails("artistName")
        verify { artistTester(any()) }

    }

    @Test
    fun `when more details of an EmptyArtist are fetched should notify`() {
        val artist : Artist = Artist.EmptyArtist
        every { repository.getArtist("artistName") } returns artist
        val artistTester: (MoreDetailsUiState) -> Unit = mockk(relaxed = true)
        moreDetailsPresenter.artistObservable.subscribe {
            artistTester(it)
        }
        moreDetailsPresenter.moreDetails("artistName")
        verify { artistTester(any()) }
    }
}