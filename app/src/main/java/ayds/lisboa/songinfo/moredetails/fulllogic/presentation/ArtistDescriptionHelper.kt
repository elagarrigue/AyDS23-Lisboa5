package ayds.lisboa.songinfo.moredetails.fulllogic.presentation

import ayds.lisboa.songinfo.moredetails.fulllogic.domain.Artist
import java.util.*

private const val PREFIX = "[*]"
private const val HTML_OPENING_TAG = "<html><div width=400><font face=\"arial\">"
private const val HTML_CLOSING_TAG = "</font></div></html>"

interface ArtistDescriptionHelper{
    fun getArtistDescription(artist: Artist): String

}

internal class ArtistDescriptionHelperImpl(): ArtistDescriptionHelper{

    override fun getArtistDescription (artist: Artist): String {
        val artistDesc = getArtistDescriptionContent(artist)
        val artistDescAsHTML = artistDescAsHTML(artistDesc)
        return textToHtml(artistDescAsHTML, artist as Artist.ArtistData)
    }

    private fun getArtistDescriptionContent (artist: Artist): String {
        if (artist is Artist.ArtistData){
            return if (artist.isLocallyStored) {
                "$PREFIX${artist.artistBioContent}"
            } else artist.artistBioContent
        }
        return "No Results"
    }

    private fun artistDescAsHTML(artistBioContent: String) = artistBioContent.reformatArtistBio()

    private fun String.reformatArtistBio() = this.replace("\\n", "\n")

    private fun textToHtml(text: String, artist: Artist.ArtistData): String {
        val builder = StringBuilder()
        val textWithBold = textAsBold(text, artist)

        builder.append(HTML_OPENING_TAG)
        builder.append(textWithBold)
        builder.append(HTML_CLOSING_TAG)

        return builder.toString()
    }

    private fun textAsBold(text: String, artist: Artist.ArtistData) : String {
        return text
            .replace("'", " ")
            .replace("\n", "<br>")
            .replace(
                "(?i)${artist.artistName}".toRegex(),
                "<b>" + artist.artistName.uppercase(Locale.getDefault()) + "</b>"
            )
    }
}

