package ayds.lisboa.songinfo.moredetails.fulllogic.presentation


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import ayds.lisboa.songinfo.R
import ayds.lisboa.songinfo.moredetails.fulllogic.domain.Artist
import ayds.lisboa.songinfo.moredetails.fulllogic.domain.MoreDetailsModel
import ayds.lisboa.songinfo.moredetails.fulllogic.domain.MoreDetailsModelInjector
import ayds.observer.Observable
import ayds.observer.Subject
import com.squareup.picasso.Picasso
import java.util.*

interface MoreDetailsView {
    val uiEventObservable: Observable<MoreDetailsUiEvent>
    val uiState: MoreDetailsUiState

    fun openUrl(artistUrl: String)
}

internal class MoreDetailsViewActivity: MoreDetailsView, AppCompatActivity() {

    private val artistDescriptionHelper: ArtistDescriptionHelper = MoreDetailsViewInjector.artistDescriptionHelper
    private val onActionSubject = Subject<MoreDetailsUiEvent>()

    private lateinit var moreDetailsModel: MoreDetailsModel
    private lateinit var viewFullArticleButton: Button
    private lateinit var artistTextView: TextView
    private lateinit var imageView: ImageView
    private lateinit var openUrlButton: View

    override val uiEventObservable: Observable<MoreDetailsUiEvent> = onActionSubject
    override var uiState: MoreDetailsUiState = MoreDetailsUiState()


    override fun openUrl(artistUrl: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(artistUrl)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_other_info)

        initModule()
        initProperties()
        initListeners()
        initUiState()
        initObservers()
    }

    private fun initModule() {
        MoreDetailsViewInjector.init(this)
        moreDetailsModel = MoreDetailsModelInjector.getMoreDetailsModel()
    }

    private fun initProperties() {
        artistTextView = findViewById(R.id.artistInfoTextView)
        imageView = findViewById(R.id.imageView)
        openUrlButton = findViewById(R.id.openUrlButton)

    }

    private fun initListeners() {
        viewFullArticleButton.setOnClickListener {
            searchAction()
        }
    }

    private fun initUiState(){
        uiState=uiState.copy( artistName = intent.getStringExtra(ARTIST_NAME_EXTRA) ?: "")
    }

    private fun initObservers() {
        moreDetailsModel.artistObservable
            .subscribe { value -> updateArtistInfo(value) }
    }


    private fun updateArtistInfo(artist: Artist){
        updateArtistUiState(artist)
        updateView(artistDescriptionHelper.getArtistDescription(artist))
        setURLButton()
    }

    private fun updateView(artistInfoText: String) {
        runOnUiThread {
            loadImageIntoView()
            setArtistViewText(artistInfoText)
        }
    }

    private fun setURLButton() {
        val artistUrl = uiState.artistURL
        artistUrl.setOpenUrlButton()
    }

    private fun String.setOpenUrlButton() {
        openUrlButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(this)
            startActivity(intent)
        }
    }

    private fun loadImageIntoView(){
        Picasso.get().load(uiState.songImageUrl).into(imageView)
    }

    @Suppress("DEPRECATION")
    private fun setArtistViewText(artistInfoText: String){
        artistTextView.text = Html.fromHtml(artistInfoText)
    }

    private fun updateArtistUiState(artist: Artist){
        when (artist) {
            is Artist.ArtistData -> updateUiState(artist)
            Artist.EmptyArtist -> updateNoResultsUiState()
        }
    }

    private fun updateUiState(artist: Artist.ArtistData){
        uiState = uiState.copy(
            artistName = artist.artistName,
            artistBioContent= artist.artistBioContent,
            artistURL= artist.artistURL,
        )
    }

    private fun updateNoResultsUiState(){
        uiState = uiState.copy(
            artistName = "",
            artistBioContent= "No Results",
            artistURL= "",
        )
    }

    private fun searchAction() {
        notifyMoreDetailsAction()
    }

    private fun notifyMoreDetailsAction() {
        onActionSubject.notify(MoreDetailsUiEvent.OpenArtistUrl)
    }

    companion object {
        const val ARTIST_NAME_EXTRA = "artistName"
    }
}
