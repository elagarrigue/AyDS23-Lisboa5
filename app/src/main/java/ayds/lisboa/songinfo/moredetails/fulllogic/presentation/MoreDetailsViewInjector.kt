package ayds.lisboa.songinfo.moredetails.fulllogic.presentation

import ayds.lisboa.songinfo.moredetails.fulllogic.domain.MoreDetailsModelInjector

object MoreDetailsViewInjector {
    val artistDescriptionHelper: ArtistDescriptionHelper = ArtistDescriptionHelperImpl()

    fun init(moreDetailsView: MoreDetailsView){
        MoreDetailsModelInjector.initMoreDetailsModel(moreDetailsView)
        //Falta inicializar el Presenter
    }
}