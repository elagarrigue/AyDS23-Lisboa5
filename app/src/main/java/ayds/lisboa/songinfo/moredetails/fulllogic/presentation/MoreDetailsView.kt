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
import ayds.observer.Observable
import ayds.observer.Subject
import com.squareup.picasso.Picasso


interface MoreDetailsView {
    val uiEventObservable: Observable<MoreDetailsUiEvent>
    val uiState: MoreDetailsUiState

    fun openUrl(artistUrl: String)

}

internal class MoreDetailsViewImpl: MoreDetailsView, AppCompatActivity(){

    private val onActionSubject = Subject<MoreDetailsUiEvent>()

    private lateinit var viewFullArticleButton: Button
    private lateinit var artistTextView: TextView
    private lateinit var imageView: ImageView
    private lateinit var openUrlButton: View

    override val uiEventObservable: Observable<MoreDetailsUiEvent> = onActionSubject
    override var uiState: MoreDetailsUiState = MoreDetailsUiState()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_other_info)

        initModule()
        initProperties()
        initListeners()
        initObservers()
        initIntentData()

        updateArtistInfoAsync()
    }

    private fun initModule() {
        MoreDetailsViewInjector.init(this)
        moreDetailsModel = MoreDetailsInjector.getMoreDetailsModel()
    }

    private fun initProperties(){
        artistTextView = findViewById(R.id.artistInfoTextView)
        imageView = findViewById(R.id.imageView)
        openUrlButton = findViewById(R.id.openUrlButton)
    }

    private fun initListeners() {
        viewFullArticleButton.setOnClickListener { //Esto es para cuando se aprieta OpenURl button
            searchAction()
        }
    }


    private fun searchAction() {
        //updateArtistNameState()
        //updateArtistUrlState()
        notifyMoreDetailsAction()
    }

    /*
    private fun updateArtistNameState() {
        uiState = uiState.copy(artistName = termEditText.text.toString())
    }
*/
    private fun notifyMoreDetailsAction() {
        onActionSubject.notify(MoreDetailsUiEvent.OpenArtistUrl)
    }

    private fun initIntentData(){
        artistName = intent.getStringExtra(ARTIST_NAME) ?: ""
    }


    override fun openUrl(artistUrl: String){
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(artistUrl)
        startActivity(intent)
    }


    /*
    private fun updateView(artistInfoText: String) {
        runOnUiThread {
            loadImageIntoView()
            setArtistViewText(artistInfoText)
        }
    }
    */
    private fun loadImageIntoView(){
        Picasso.get().load(IMAGE_URL).into(imageView)
    }

    @Suppress("DEPRECATION")
    private fun setArtistViewText(artistInfoText: String){
        artistTextView.text = Html.fromHtml(artistInfoText)
    }

/*
    private fun JsonElement.setOpenUrlButton() {
        val urlString = this.asString
        openUrlButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(urlString)
            startActivity(intent)
        }
    }
    */
}
