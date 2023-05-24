package ayds.lisboa.songinfo.moredetails.data

import com.example.lisboa5lastfm.lastfm.external.artist.ArtistExternalService
import ayds.lisboa.songinfo.moredetails.data.local.sqldb.ArtistLocalStorage
import ayds.lisboa.songinfo.moredetails.domain.entities.Artist
import ayds.lisboa.songinfo.moredetails.domain.repository.ArtistRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.*
import org.junit.Test

class ArtistRepositoryTest {

    @Test
    fun getArtist() {
    }

    private val artistLocalStorage: ArtistLocalStorage = mockk(relaxUnitFun = true)
    private val artistExternalService: com.example.lisboa5lastfm.lastfm.external.artist.ArtistExternalService = mockk(relaxUnitFun = true)

    private val artistRepository: ArtistRepository by lazy {
        ArtistRepositoryImpl(artistLocalStorage, artistExternalService)
    }

    @Test
    fun `given existing artist by artistName should return artist and mark it as local`() {
        val artist = Artist.ArtistData("artistName","artistBioContent","artistURL",false)
        every{ artistLocalStorage.getArtist("artistName")} returns artist

        val result = artistRepository.getArtist("artistName")

        assertEquals(artist, result)
        assertTrue(artist.isLocallyStored)
    }

    @Test
    fun `given non existing artist in the LocalStorage by artistName should get the artist from the external service and store it`() {
        val artist = Artist.ArtistData("artistName","artistBioContent","artistURL",false)
        every { artistLocalStorage.getArtist("artistName") } returns Artist.EmptyArtist
        every {artistExternalService.getArtistFromLastFMAPI("artistName") } returns artist

        val result = artistRepository.getArtist("artistName")

        assertEquals(artist, result)
        assertFalse(artist.isLocallyStored)
        verify{artistLocalStorage.saveArtist("artistName","artistBioContent", "artistURL")}
    }

    @Test
    fun `given non existing artist in the local storage and the external service by artistName should return EmptyArtist `(){

        every { artistLocalStorage.getArtist("artistName") } returns Artist.EmptyArtist
        every {artistExternalService.getArtistFromLastFMAPI("artistName")} returns null

        val result = artistRepository.getArtist("artistName")

        assertEquals(Artist.EmptyArtist, result)
    }

    @Test
    fun `given service exception should return empty artist`(){
        every { artistLocalStorage.getArtist("artistName") } returns Artist.EmptyArtist
        every {artistExternalService.getArtistFromLastFMAPI("artistName")} throws mockk<Exception>()

        val result = artistRepository.getArtist("artistName")

        assertEquals(Artist.EmptyArtist, result)
    }
}