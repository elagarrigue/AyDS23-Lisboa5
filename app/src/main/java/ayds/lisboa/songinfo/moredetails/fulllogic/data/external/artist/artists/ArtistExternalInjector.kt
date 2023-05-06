package ayds.lisboa.songinfo.moredetails.fulllogic.data.external.artist.artists
import ayds.lisboa.songinfo.moredetails.fulllogic.data.external.artist.LastFMToArtistResolver
import ayds.lisboa.songinfo.moredetails.fulllogic.data.external.artist.LastFMToArtistResolverImpl
import ayds.lisboa.songinfo.moredetails.fulllogic.data.external.artist.LastFMAPI
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

object ArtistExternalInjector {

    private const val RETROFIT_URL = "https://ws.audioscrobbler.com/2.0/"

    private val retrofit = createRetrofit()
    private val lastFMAPI = retrofit.create(LastFMAPI::class.java)


    private fun createRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(RETROFIT_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
    }

    private val lastFMtoArtistResolver: LastFMToArtistResolver = LastFMToArtistResolverImpl()
     val artistExternalService : ArtistExternalService = ArtistExternalServiceImpl(lastFMAPI,
         lastFMtoArtistResolver)



}