package ayds.lisboa.songinfo.moredetails.fulllogic.presentation

import ayds.lisboa.songinfo.moredetails.fulllogic.domain.Artist

private const val PREFIX = "[*]"

interface ArtistDescriptionHelper{
    fun getArtistDescription(artist: Artist): String
}

internal class ArtistDescriptionHelperImpl(): ArtistDescriptionHelper{
    override fun getArtistDescription (artist: Artist): String {
        if (artist is Artist.ArtistData){
            return if (artist.isLocallyStored) {
                "$PREFIX${artist.artistBioContent}"
            } else artist.artistBioContent
        }
        return "No Results"
    }
}

