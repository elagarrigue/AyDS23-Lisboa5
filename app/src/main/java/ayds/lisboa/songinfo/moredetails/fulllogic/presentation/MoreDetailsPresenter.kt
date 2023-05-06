package ayds.lisboa.songinfo.moredetails.fulllogic.presentation


import ayds.observer.Observer


public interface MoreDetailsPresenter{

}

internal class OtherInfoPresenterImpl: MoreDetailsPresenter{

    private lateinit var moreDetailsView: MoreDetailsView

    private val observer: Observer<MoreDetailsUiEvent> =
        Observer {
            openUrl()
        }

    private fun openUrl(){
        val artistUrl = moreDetailsView.uiState.artistURL
        moreDetailsView.openUrl(artistUrl)
    }



}