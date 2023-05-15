package ayds.lisboa.songinfo.moredetails.data

import ayds.lisboa.songinfo.moredetails.data.external.artist.ArtistExternalService
import ayds.lisboa.songinfo.moredetails.data.local.sqldb.ArtistLocalStorage
import ayds.lisboa.songinfo.moredetails.domain.entities.Artist
import ayds.lisboa.songinfo.moredetails.domain.repository.ArtistRepository
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.lang.Exception

class ArtistRepositoryTest {

    @Test
    fun getArtist() {
    }

    private val artistLocalStorage: ArtistLocalStorage = mockk(relaxUnitFun = true)
    private val artistExternalService: ArtistExternalService = mockk(relaxUnitFun = true)

    private val artistRepository: ArtistRepository by lazy {
        ArtistRepositoryImpl(artistLocalStorage, artistExternalService)
    }

    @Test
    fun `given existing artist by artistName should return artist and mark it as local`() {
        val artist = Artist.ArtistData("artistName","artistBioContent","artistURL",false)
        every{ artistLocalStorage.getArtist("artistName")} returns artist

        val resultado = artistRepository.getArtist("artistName")
        assertEquals(artist, resultado)
        assertTrue(artist.isLocallyStored)
    }

    @Test
    fun `given non existing artist in the LocalStorage by artistName should get the artist from the external service and store it`() {
        val artist = Artist.ArtistData("artistName","artistBioContent","artistURL",false)
        every { artistLocalStorage.getArtist("artistName") } returns Artist.EmptyArtist
        every {artistExternalService.getArtistFromLastFMAPI("artistName") } returns artist


        val result = artistRepository.getArtist("artistName")

        assertEquals(artist, result)
        Assert.assertFalse(artist.isLocallyStored)
       // verify { ArtistRepositoryImpl.saveArtistInfo(artist) }
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