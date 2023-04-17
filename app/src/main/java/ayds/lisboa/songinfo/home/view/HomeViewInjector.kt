package ayds.lisboa.songinfo.home.view

import DateFormatFactory
import DateFormatFactoryImpl
import ayds.lisboa.songinfo.home.controller.HomeControllerInjector
import ayds.lisboa.songinfo.home.model.HomeModelInjector

object HomeViewInjector {
    val dateFormatFactory: DateFormatFactory = DateFormatFactoryImpl
    val songDescriptionHelper: SongDescriptionHelper = SongDescriptionHelperImpl(dateFormatFactory)

    fun init(homeView: HomeView) {
        HomeModelInjector.initHomeModel(homeView)
        HomeControllerInjector.onViewStarted(homeView)
    }
}