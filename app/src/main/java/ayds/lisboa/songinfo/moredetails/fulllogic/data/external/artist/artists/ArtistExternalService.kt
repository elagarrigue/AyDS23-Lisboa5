package ayds.lisboa.songinfo.moredetails.fulllogic.data.external.artist.artists


import ayds.lisboa.songinfo.moredetails.fulllogic.domain.Artist

interface ArtistExternalService {

    fun getArtistFromLastFMAPI(artistName: String): Artist.ArtistData?
}