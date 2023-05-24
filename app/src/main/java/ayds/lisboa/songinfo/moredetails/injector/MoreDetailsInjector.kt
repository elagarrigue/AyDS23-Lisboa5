package ayds.lisboa.songinfo.moredetails.injector

import android.content.Context
import com.example.lisboa5lastfm.lastfm.external.artist.LastFMAPI
import com.example.lisboa5lastfm.lastfm.external.artist.LastFMToArtistResolver
import com.example.lisboa5lastfm.lastfm.external.artist.LastFMToArtistResolverImpl
import com.example.lisboa5lastfm.lastfm.external.artist.ArtistExternalService
import com.example.lisboa5lastfm.lastfm.external.artist.ArtistExternalServiceImpl
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
    private val lastFMAPI = retrofit.create(com.example.lisboa5lastfm.lastfm.external.artist.LastFMAPI::class.java)
    private val lastFMtoArtistResolver: com.example.lisboa5lastfm.lastfm.external.artist.LastFMToArtistResolver =
        com.example.lisboa5lastfm.lastfm.external.artist.LastFMToArtistResolverImpl()
    private val artistExternalService : com.example.lisboa5lastfm.lastfm.external.artist.ArtistExternalService =
        com.example.lisboa5lastfm.lastfm.external.artist.ArtistExternalServiceImpl(
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