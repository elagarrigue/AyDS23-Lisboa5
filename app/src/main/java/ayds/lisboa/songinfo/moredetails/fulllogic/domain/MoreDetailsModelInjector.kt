package ayds.lisboa.songinfo.moredetails.fulllogic.domain

import android.content.Context
import ayds.lisboa.songinfo.moredetails.fulllogic.data.external.artist.ArtistInjector
import ayds.lisboa.songinfo.moredetails.fulllogic.data.external.artist.artists.ArtistExternalService
import ayds.lisboa.songinfo.moredetails.fulllogic.data.local.sqldb.ArtistLocalStorage
import ayds.lisboa.songinfo.moredetails.fulllogic.data.local.sqldb.ArtistLocalStorageImpl
import ayds.lisboa.songinfo.moredetails.fulllogic.data.local.sqldb.CursorToArtistLocalImpl

import ayds.lisboa.songinfo.moredetails.fulllogic.presentation.MoreDetailsView

object MoreDetailsModelInjector {
    private lateinit var moreDetailsModel: MoreDetailsModel

    fun getMoreDetailsModel(): MoreDetailsModel = moreDetailsModel

    fun initMoreDetailsModel(moreDetailsView: MoreDetailsView){
        val artistLocalStorage : ArtistLocalStorage = ArtistLocalStorageImpl(
            moreDetailsView as Context, CursorToArtistLocalImpl()
        )
        val artistExternalService: ArtistExternalService = ArtistInjector.artistExternalService

        val repository: ArtistRepository = ArtistRepositoryImpl(artistLocalStorage,artistExternalService)

        moreDetailsModel = MoreDetailsModelImpl(repository)
    }
}