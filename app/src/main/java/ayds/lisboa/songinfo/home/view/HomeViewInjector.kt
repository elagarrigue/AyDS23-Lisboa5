package ayds.lisboa.songinfo.home.view

import ayds.lisboa.songinfo.home.controller.HomeControllerInjector
import ayds.lisboa.songinfo.home.model.HomeModelInjector

object HomeViewInjector {
    private val releaseDateCreator: ReleaseDateCreator = ReleaseDateCreatorImpl()
    val songDescriptionHelper: SongDescriptionHelper = SongDescriptionHelperImpl(releaseDateCreator)

    fun init(homeView: HomeView) {
        HomeModelInjector.initHomeModel(homeView)
        HomeControllerInjector.onViewStarted(homeView)
    }
}