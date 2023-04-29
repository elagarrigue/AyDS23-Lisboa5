package ayds.lisboa.songinfo.moredetails.fulllogic

sealed class Artist {
    data class ArtistData(
        val artistName: String,
        val artistBioContent: String,
        val artistHTML: String,
        var isLocallyStored: Boolean = false
    ) : Artist()
    object EmptyArtist : Artist()
}