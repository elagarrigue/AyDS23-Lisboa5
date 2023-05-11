package ayds.lisboa.songinfo.moredetails.fulllogic.data.external.artist.artists

import ayds.lisboa.songinfo.moredetails.fulllogic.data.external.artist.LastFMAPI
import ayds.lisboa.songinfo.moredetails.fulllogic.data.external.artist.LastFMToArtistResolver
import ayds.lisboa.songinfo.moredetails.fulllogic.domain.entities.Artist


internal class ArtistExternalServiceImpl (
    private val lastFMAPI: LastFMAPI,
    private val lastFMtoArtistResolver: LastFMToArtistResolver
        ): ArtistExternalService {

     override fun  getArtistFromLastFMAPI(artistName: String): Artist.ArtistData? {
        val callResponse = lastFMAPI.getArtistInfo(artistName).execute()
        return lastFMtoArtistResolver.getArtistFromExternalData(callResponse.body())
    }

}