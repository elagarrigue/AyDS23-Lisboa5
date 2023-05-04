package ayds.lisboa.songinfo.moredetails.fulllogic.model.entities.repository


import ayds.lisboa.songinfo.moredetails.fulllogic.model.entities.Artist

interface LastFMAPIService {

    fun getArtistFromLastFMAPI(artistName: String): Artist
}