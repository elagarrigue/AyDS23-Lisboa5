package ayds.lisboa.songinfo.moredetails.fulllogic.data.external.artist

import ayds.lisboa.songinfo.moredetails.fulllogic.data.external.artist.artists.ArtistExternalInjector
import ayds.lisboa.songinfo.moredetails.fulllogic.data.external.artist.artists.ArtistExternalService

object ArtistInjector {
    val artistExternalService : ArtistExternalService = ArtistExternalInjector.artistExternalService
}