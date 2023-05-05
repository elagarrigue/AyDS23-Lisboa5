package ayds.lisboa.songinfo.moredetails.fulllogic.model.entities.repository.externalArtist


import ayds.lisboa.songinfo.moredetails.fulllogic.model.entities.Artist

interface ArtistExternalService {

    fun getArtistFromLastFMAPI(artistName: String): Artist.ArtistData?
}