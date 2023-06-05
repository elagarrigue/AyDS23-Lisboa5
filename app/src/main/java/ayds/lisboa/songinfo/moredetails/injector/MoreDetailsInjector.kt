package ayds.lisboa.songinfo.moredetails.injector

import android.content.Context
import ayds.aknewyork.external.service.data.NYTimesService
import ayds.aknewyork.external.service.injector.NYTimesInjector
import ayds.lisboa.songinfo.moredetails.cards.CursorToCardLocalImpl
import ayds.lisboa.songinfo.moredetails.data.local.sqldb.CardLocalStorage
import ayds.lisboa.songinfo.moredetails.data.local.sqldb.CardLocalStorageImpl
import ayds.lisboa.songinfo.moredetails.data.proxy.LastFMProxy
import ayds.lisboa.songinfo.moredetails.data.proxy.NYTimesProxy
import ayds.lisboa.songinfo.moredetails.data.proxy.ProxyCard
import ayds.lisboa.songinfo.moredetails.data.proxy.WikipediaProxy

import lisboa5lastfm.artist.ArtistExternalService

import ayds.lisboa.songinfo.moredetails.domain.repository.CardRepository
import ayds.lisboa.songinfo.moredetails.data.CardRepositoryImpl
import ayds.lisboa.songinfo.moredetails.data.CardsBroker
import ayds.lisboa.songinfo.moredetails.data.CardsBrokerImpl
import ayds.lisboa.songinfo.moredetails.presentation.*
import ayds.lisboa.songinfo.moredetails.presentation.MoreDetailsPresenterImpl
import ayds.winchester3.wikiartist.artist.externalWikipedia.WikipediaInjector
import ayds.winchester3.wikiartist.artist.externalWikipedia.WikipediaService
import lisboa5lastfm.ExternalServiceInjector

object MoreDetailsInjector {
    private val cardDescriptionHelper: CardDescriptionHelper = CardDescriptionHelperImpl()
    private val cardSourceFactory: CardSourceFactory = CardSourceFactoryImpl()
    private val wikipediaService : WikipediaService =
        WikipediaInjector.wikipediaService
    private val proxyCard1 : ProxyCard = WikipediaProxy(wikipediaService)

    private val lastFMService : ArtistExternalService =
        ExternalServiceInjector.getLastFMService()
    private val proxyCard2 : ProxyCard = LastFMProxy(lastFMService)

    private val nyTimesService : NYTimesService = NYTimesInjector.nyTimesService
    private val proxyCard3 : ProxyCard = NYTimesProxy(nyTimesService)

    private val broker: CardsBroker = CardsBrokerImpl(
        proxyCard1, proxyCard2, proxyCard3
    )

    lateinit var moreDetailsPresenter: MoreDetailsPresenter

    fun init(moreDetailsView: MoreDetailsView){
        val repository = initMoreDetailsRepository(moreDetailsView)
        moreDetailsPresenter = MoreDetailsPresenterImpl(cardDescriptionHelper,repository,cardSourceFactory)
    }

    private fun initMoreDetailsRepository(moreDetailsView: MoreDetailsView): CardRepository {
        val cardLocalStorage: CardLocalStorage = CardLocalStorageImpl(
            moreDetailsView as Context, CursorToCardLocalImpl()
        )

        return CardRepositoryImpl(cardLocalStorage, broker)
    }

}