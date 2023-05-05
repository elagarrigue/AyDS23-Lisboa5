package ayds.lisboa.songinfo.moredetails.fulllogic.model.entities.repository.externalArtist

import ayds.lisboa.songinfo.moredetails.fulllogic.model.entities.repository.externalArtist.artists.ArtistExternalInjector

object ArtistInjector {
    val artistExternalService : ArtistExternalService = ArtistExternalInjector.artistExternalService
}