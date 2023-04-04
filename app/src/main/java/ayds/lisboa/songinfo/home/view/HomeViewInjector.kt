package ayds.lisboa.songinfo.home.view

import ayds.lisboa.songinfo.utils.navigation.controller.HomeControllerInjector
import ayds.lisboa.songinfo.home.model.HomeModelInjector

object HomeViewInjector {
    val releaseDateCreator: ReleaseDateCreator = ReleaseDateCreatorIml()
    val songDescriptionHelper: SongDescriptionHelper = SongDescriptionHelperImpl(releaseDateCreator)

    fun init(homeView: HomeView) {
        HomeModelInjector.initHomeModel(homeView)
        HomeControllerInjector.onViewStarted(homeView)
    }
}