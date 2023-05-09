package ayds.lisboa.songinfo.moredetails.fulllogic.presentation



import ayds.lisboa.songinfo.moredetails.fulllogic.domain.MoreDetailsModel
import ayds.observer.Observer

interface MoreDetailsPresenter{
    fun setMoreDetailsView(moreDetailsView: MoreDetailsView)
    fun moreDetails()
}

internal class MoreDetailsPresenterImpl(
    private val moreDetailsModel: MoreDetailsModel
): MoreDetailsPresenter{

    private lateinit var moreDetailsView: MoreDetailsView

    override fun setMoreDetailsView(moreDetailsView: MoreDetailsView) {
        this.moreDetailsView = moreDetailsView
        moreDetailsView.uiEventObservable.subscribe(observer)
    }

    private val observer: Observer<MoreDetailsUiEvent> =
        Observer { value ->
            when(value){
                MoreDetailsUiEvent.OpenArtistUrl -> openUrl()
                MoreDetailsUiEvent.MoreDetails -> moreDetails()
            }
        }

    private fun openUrl(){
        val artistUrl = moreDetailsView.uiState.artistURL
        moreDetailsView.openUrl(artistUrl)
    }

    override fun moreDetails(){
        Thread {
            moreDetailsModel.getArtist(moreDetailsView.uiState.artistName)
        }.start()
    }
}