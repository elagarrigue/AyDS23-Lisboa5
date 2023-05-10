package ayds.lisboa.songinfo.moredetails.fulllogic.presentation

import android.content.Context
import ayds.lisboa.songinfo.moredetails.fulllogic.data.external.artist.LastFMAPI
import ayds.lisboa.songinfo.moredetails.fulllogic.data.external.artist.LastFMToArtistResolver
import ayds.lisboa.songinfo.moredetails.fulllogic.data.external.artist.LastFMToArtistResolverImpl
import ayds.lisboa.songinfo.moredetails.fulllogic.data.external.artist.artists.ArtistExternalService
import ayds.lisboa.songinfo.moredetails.fulllogic.data.external.artist.artists.ArtistExternalServiceImpl
import ayds.lisboa.songinfo.moredetails.fulllogic.data.local.sqldb.ArtistLocalStorage
import ayds.lisboa.songinfo.moredetails.fulllogic.data.local.sqldb.ArtistLocalStorageImpl
import ayds.lisboa.songinfo.moredetails.fulllogic.data.local.sqldb.CursorToArtistLocalImpl
import ayds.lisboa.songinfo.moredetails.fulllogic.domain.*
import ayds.lisboa.songinfo.moredetails.fulllogic.domain.ArtistRepositoryImpl
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

object MoreDetailsInjector {

    private const val RETROFIT_URL = "https://ws.audioscrobbler.com/2.0/"

    private val retrofit = createRetrofit()
    private val lastFMAPI = retrofit.create(LastFMAPI::class.java)
    private val lastFMtoArtistResolver: LastFMToArtistResolver = LastFMToArtistResolverImpl()
    private val artistExternalService : ArtistExternalService = ArtistExternalServiceImpl(lastFMAPI,lastFMtoArtistResolver)

    val artistDescriptionHelper: ArtistDescriptionHelper = ArtistDescriptionHelperImpl()

    lateinit var moreDetailsPresenter: MoreDetailsPresenter

    fun init(moreDetailsView: MoreDetailsView){
        val repository = initMoreDetailsRepository(moreDetailsView)
        moreDetailsPresenter = MoreDetailsPresenterImpl().apply {
            setMoreDetailsView(moreDetailsView)
            setRepository(repository)
        }
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