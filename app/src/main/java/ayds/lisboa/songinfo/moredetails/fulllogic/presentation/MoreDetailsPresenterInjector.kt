package ayds.lisboa.songinfo.moredetails.fulllogic.presentation

import ayds.lisboa.songinfo.moredetails.fulllogic.domain.MoreDetailsModelInjector

object MoreDetailsPresenterInjector {

    fun onViewStarted(moreDetailsView: MoreDetailsView){
        MoreDetailsPresenterImpl(MoreDetailsModelInjector.getMoreDetailsModel()).apply {
            setMoreDetailsView(moreDetailsView)
        }
    }
}