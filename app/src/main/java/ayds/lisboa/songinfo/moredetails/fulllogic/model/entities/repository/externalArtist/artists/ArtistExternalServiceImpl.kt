package ayds.lisboa.songinfo.moredetails.fulllogic.model.entities.repository.externalArtist.artists

import ayds.lisboa.songinfo.moredetails.fulllogic.LastFMAPI
import ayds.lisboa.songinfo.moredetails.fulllogic.model.entities.Artist
import ayds.lisboa.songinfo.moredetails.fulllogic.model.entities.repository.externalArtist.ArtistExternalService


internal class ArtistExternalServiceImpl (
    private val lastFMAPI: LastFMAPI,
    private val lastFMtoArtistResolver: LastFMToArtistResolver
        ):ArtistExternalService{

     override fun  getArtistFromLastFMAPI(artistName: String): Artist.ArtistData? {
        val callResponse = lastFMAPI.getArtistInfo(artistName).execute()
        return lastFMtoArtistResolver.getArtistFromExternalData(callResponse.body())
    }

}