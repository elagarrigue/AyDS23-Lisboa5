package ayds.lisboa.songinfo.moredetails.injector

import android.content.Context
import ayds.lisboa.songinfo.moredetails.data.external.artist.LastFMAPI
import ayds.lisboa.songinfo.moredetails.data.external.artist.LastFMToArtistResolver
import ayds.lisboa.songinfo.moredetails.data.external.artist.LastFMToArtistResolverImpl
import ayds.lisboa.songinfo.moredetails.data.external.artist.ArtistExternalService
import ayds.lisboa.songinfo.moredetails.data.external.artist.ArtistExternalServiceImpl
import ayds.lisboa.songinfo.moredetails.data.local.sqldb.ArtistLocalStorage
import ayds.lisboa.songinfo.moredetails.data.local.sqldb.ArtistLocalStorageImpl
import ayds.lisboa.songinfo.moredetails.data.local.sqldb.CursorToArtistLocalImpl
import ayds.lisboa.songinfo.moredetails.data.ArtistRepositoryImpl
import ayds.lisboa.songinfo.moredetails.domain.repository.ArtistRepository
import ayds.lisboa.songinfo.moredetails.presentation.*
import ayds.lisboa.songinfo.moredetails.presentation.ArtistDescriptionHelperImpl
import ayds.lisboa.songinfo.moredetails.presentation.MoreDetailsPresenterImpl
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

object MoreDetailsInjector {

    private const val RETROFIT_URL = "https://ws.audioscrobbler.com/2.0/"

    private val retrofit = createRetrofit()
    private val lastFMAPI = retrofit.create(LastFMAPI::class.java)
    private val lastFMtoArtistResolver: LastFMToArtistResolver = LastFMToArtistResolverImpl()
    private val artistExternalService : ArtistExternalService = ArtistExternalServiceImpl(
        lastFMAPI,
        lastFMtoArtistResolver
    )

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

    private fun createRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(RETROFIT_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
    }
}