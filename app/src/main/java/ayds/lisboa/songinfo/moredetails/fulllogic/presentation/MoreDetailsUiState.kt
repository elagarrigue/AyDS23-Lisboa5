package ayds.lisboa.songinfo.moredetails.fulllogic.presentation


data class MoreDetailsUiState (
    val artistName: String = "",
    val artistBioContent: String = "",
    val artistURL: String = "",
    val lastFMImageUrl: String = DEFAULT_IMAGE,
) {
    companion object {
        const val DEFAULT_IMAGE =
            "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d4/Lastfm_logo.svg/320px-Lastfm_logo.svg.png"
    }

}