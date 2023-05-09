package ayds.lisboa.songinfo.moredetails.fulllogic.presentation


import ayds.lisboa.songinfo.moredetails.fulllogic.domain.MoreDetailsModel
import ayds.observer.Observer


interface MoreDetailsPresenter{
    fun setMoreDetailsView(moreDetailsView: MoreDetailsView)
}

internal class MoreDetailsPresenterImpl(
    private val moreDetailsModel: MoreDetailsModel
): MoreDetailsPresenter{

    private lateinit var moreDetailsView: MoreDetailsView

    override fun setMoreDetailsView(moreDetailsView: MoreDetailsView) {
        this.moreDetailsView = moreDetailsView
        moreDetailsView.uiEventObservable.subscribe(observer)
    }

    //Va a tener otro para iniciarlizar el panel, ya que esta va a ser llamado de homeview(para detectar la actividad esa)?
    private val observer: Observer<MoreDetailsUiEvent> =
        Observer { value ->
            when(value){
               MoreDetailsUiEvent.OpenArtistUrl -> openUrl()
            }
        }

    private fun openUrl(){
        val artistUrl = moreDetailsView.uiState.artistURL
        moreDetailsView.openUrl(artistUrl)
    }





}