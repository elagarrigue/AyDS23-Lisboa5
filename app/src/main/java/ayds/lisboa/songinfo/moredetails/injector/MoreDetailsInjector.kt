package ayds.lisboa.songinfo.moredetails.injector

import android.content.Context

import lisboa5lastfm.artist.ArtistExternalService
import ayds.lisboa.songinfo.moredetails.data.local.sqldb.CursorToArtistLocalImpl
import ayds.lisboa.songinfo.moredetails.presentation.*
import ayds.lisboa.songinfo.moredetails.presentation.ArtistDescriptionHelperImpl
import ayds.lisboa.songinfo.moredetails.presentation.MoreDetailsPresenterImpl
import lisboa5lastfm.ExternalServiceInjector

object MoreDetailsInjector {

    private val artistExternalService : ArtistExternalService =
        ExternalServiceInjector.getLastFMService()

    private val artistDescriptionHelper: ArtistDescriptionHelper = ArtistDescriptionHelperImpl()

    lateinit var moreDetailsPresenter: MoreDetailsPresenter

    fun init(moreDetailsView: MoreDetailsView){
        val repository = initMoreDetailsRepository(moreDetailsView)
        moreDetailsPresenter = MoreDetailsPresenterImpl(artistDescriptionHelper,repository)
    }

    private fun initMoreDetailsRepository(moreDetailsView: MoreDetailsView): ArtistRepository {
        val artistLocalStorage: ArtistLocalStorage = ArtistLocalStorageImpl(
            moreDetailsView as Context, CursorToArtistLocalImpl()
        )

        return ArtistRepositoryImpl(artistLocalStorage, artistExternalService)
    }

}