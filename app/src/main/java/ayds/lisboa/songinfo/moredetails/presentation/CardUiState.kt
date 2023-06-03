package ayds.lisboa.songinfo.moredetails.presentation

import ayds.lisboa.songinfo.moredetails.domain.entities.Source


data class CardUiState(
    val source: Source = Source.EMPTY_SOURCE,
    val artistBioContent: String = "",
    val infoURL: String = "",
    val logoUrl: String = "",
    val sourceDescription: String = ""
)