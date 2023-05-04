package ayds.lisboa.songinfo.moredetails.fulllogic.model.entities

sealed class Artist {
    data class ArtistData(
        val artistName: String,
        val artistBioContent: String,
        val artistURL: String,
        var isLocallyStored: Boolean = false
    ) : Artist()
    object EmptyArtist : Artist()
}