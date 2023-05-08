package ayds.lisboa.songinfo.moredetails.fulllogic.domain

import ayds.lisboa.songinfo.moredetails.fulllogic.model.entities.repository.ArtistRepository
import ayds.observer.Observable
import ayds.observer.Subject

interface MoreDetailsModel{
    val artistObservable: Observable<Artist>
    fun getArtist(artist: String)
}

internal class MoreDetailsModelImpl(private val repository: ArtistRepository) : MoreDetailsModel{

    override val artistObservable= Subject<Artist>()

    override fun getArtist(artist: String) {
        repository.getArtist(artist).let {
            artistObservable.notify(it)
        }
    }


}