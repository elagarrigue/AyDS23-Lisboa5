package ayds.lisboa.songinfo.moredetails.domain.repository

import lisboa5lastfm.Artist

interface ArtistRepository {
    fun getArtist(artistName: String): Artist

}


