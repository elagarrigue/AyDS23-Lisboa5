package ayds.lisboa.songinfo.moredetails.domain.repository

import com.example.lisboa5lastfm.lastfm.external.Artist

interface ArtistRepository {
    fun getArtist(artistName: String): Artist

}


