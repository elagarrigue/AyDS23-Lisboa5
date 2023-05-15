package ayds.lisboa.songinfo.moredetails.data.external.artist

import ayds.lisboa.songinfo.moredetails.domain.entities.Artist
import io.mockk.every
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import org.junit.Test

class ArtistExternalServiceTest {
    private val lastFMAPI:LastFMAPI = mockk(relaxUnitFun = true);
    private val lastFMToArtistResolver : LastFMToArtistResolver = mockk(relaxUnitFun = true);
    private val artistExternalService : ArtistExternalService by lazy {
        ArtistExternalServiceImpl(lastFMAPI,lastFMToArtistResolver)
    }

    @Test
    fun  `given existing artist name should return artist`(){
        val artist:Artist.ArtistData = mockk();
        every { artistExternalService.getArtistFromLastFMAPI("artistName")} returns artist
        val result = artistExternalService.getArtistFromLastFMAPI("artistName");
        assertEquals(artist,result)
    }
}