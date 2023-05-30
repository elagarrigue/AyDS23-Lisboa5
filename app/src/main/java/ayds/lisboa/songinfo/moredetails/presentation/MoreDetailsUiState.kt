package ayds.lisboa.songinfo.moredetails.presentation


data class MoreDetailsUiState (
    val source: String = "",
    val artistBioContent: String = "",
    val infoURL: String = "",
    val logoUrl: String = "",
) {
    companion object {
        const val DEFAULT_IMAGE =
            "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d4/Lastfm_logo.svg/320px-Lastfm_logo.svg.png"
    }

}