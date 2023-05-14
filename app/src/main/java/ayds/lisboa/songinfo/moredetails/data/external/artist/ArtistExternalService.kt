package ayds.lisboa.songinfo.moredetails.data.external.artist


import ayds.lisboa.songinfo.moredetails.domain.entities.Artist

interface ArtistExternalService {

    fun getArtistFromLastFMAPI(artistName: String): Artist.ArtistData?
}