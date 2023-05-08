package ayds.lisboa.songinfo.moredetails.fulllogic.domain

import android.content.Context
import ayds.lisboa.songinfo.moredetails.fulllogic.data.external.artist.ArtistInjector
import ayds.lisboa.songinfo.moredetails.fulllogic.data.external.artist.artists.ArtistExternalService
import ayds.lisboa.songinfo.moredetails.fulllogic.data.local.sqldb.CursorDataBaseImpl
import ayds.lisboa.songinfo.moredetails.fulllogic.data.local.sqldb.DataBase
import ayds.lisboa.songinfo.moredetails.fulllogic.data.local.sqldb.DataBaseImp
import ayds.lisboa.songinfo.moredetails.fulllogic.model.entities.repository.ArtistRepository
import ayds.lisboa.songinfo.moredetails.fulllogic.model.entities.repository.ArtistRepositoryImpl

import ayds.lisboa.songinfo.moredetails.fulllogic.presentation.MoreDetailsView

object MoreDetailsModelInjector {
    private lateinit var moreDetailsModel: MoreDetailsModel

    fun getMoreDetailsModel(): MoreDetailsModel = moreDetailsModel

    fun initMoreDetailsModel(moreDetailsView: MoreDetailsView){
        val artistLocalStorage : DataBase = DataBaseImp(
            moreDetailsView as Context, CursorDataBaseImpl()
        )
        val artistExternalService: ArtistExternalService = ArtistInjector.artistExternalService

        val repository: ArtistRepository = ArtistRepositoryImpl(artistLocalStorage,artistExternalService)

        moreDetailsModel = MoreDetailsModelImpl(repository)
    }
}