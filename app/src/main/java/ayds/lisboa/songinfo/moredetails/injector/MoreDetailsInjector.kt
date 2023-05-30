package ayds.lisboa.songinfo.moredetails.injector

import android.content.Context
import ayds.aknewyork.external.service.data.NYTimesService
import ayds.aknewyork.external.service.injector.NYTimesInjector
import ayds.lisboa.songinfo.moredetails.cards.CursorToCardLocalImpl
import ayds.lisboa.songinfo.moredetails.data.CardRepositoryImpl
import ayds.lisboa.songinfo.moredetails.data.local.sqldb.CardLocalStorage
import ayds.lisboa.songinfo.moredetails.data.local.sqldb.CardLocalStorageImpl

import lisboa5lastfm.artist.ArtistExternalService
import ayds.lisboa.songinfo.moredetails.domain.Broker
import ayds.lisboa.songinfo.moredetails.domain.proxy.ProxyLastFM
import ayds.lisboa.songinfo.moredetails.domain.proxy.ProxyNYTimes
import ayds.lisboa.songinfo.moredetails.domain.proxy.ProxyWikipedia
import ayds.lisboa.songinfo.moredetails.domain.repository.CardRepository
import ayds.lisboa.songinfo.moredetails.presentation.*
import ayds.lisboa.songinfo.moredetails.presentation.MoreDetailsPresenterImpl
import ayds.winchester3.wikiartist.artist.externalWikipedia.WikipediaInjector
import ayds.winchester3.wikiartist.artist.externalWikipedia.WikipediaService
import lisboa5lastfm.ExternalServiceInjector

object MoreDetailsInjector {
    private val cardDescriptionHelper: CardDescriptionHelper = CardDescriptionHelperImpl()

    private val wikipediaService : WikipediaService =
        WikipediaInjector.wikipediaService
    private val proxyWikipedia : ProxyWikipedia = ProxyWikipedia(wikipediaService)

    private val lastFMService : ArtistExternalService =
        ExternalServiceInjector.getLastFMService()
    private val proxyLastFM : ProxyLastFM = ProxyLastFM(lastFMService)

    private val nyTimesService : NYTimesService = NYTimesInjector.nyTimesService
    private val proxyNYTimes : ProxyNYTimes = ProxyNYTimes(nyTimesService)

    private val broker: Broker = Broker(
        proxyWikipedia, proxyLastFM, proxyNYTimes
    )

    lateinit var moreDetailsPresenter: MoreDetailsPresenter

    fun init(moreDetailsView: MoreDetailsView){
        val repository = initMoreDetailsRepository(moreDetailsView)
        moreDetailsPresenter = MoreDetailsPresenterImpl(cardDescriptionHelper,repository)
    }

    private fun initMoreDetailsRepository(moreDetailsView: MoreDetailsView): CardRepository {
        val cardLocalStorage: CardLocalStorage = CardLocalStorageImpl(
            moreDetailsView as Context, CursorToCardLocalImpl()
        )

        return CardRepositoryImpl(cardLocalStorage, broker)
    }

}