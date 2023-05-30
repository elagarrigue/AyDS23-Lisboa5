package ayds.lisboa.songinfo.moredetails.presentation


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import ayds.lisboa.songinfo.R
import ayds.lisboa.songinfo.moredetails.injector.MoreDetailsInjector
import com.squareup.picasso.Picasso
import java.util.*

interface MoreDetailsView

internal class MoreDetailsViewActivity : MoreDetailsView, AppCompatActivity() {

    private lateinit var moreDetailsPresenter: MoreDetailsPresenter

    private lateinit var lastFMArtistTextView: TextView
    private lateinit var lastFMImageView: ImageView
    private lateinit var lastFMOpenUrlButton: Button

    private lateinit var wikipediaArtistTextView: TextView
    private lateinit var wikipediaImageView: ImageView
    private lateinit var wikipediaOpenUrlButton: Button

    private lateinit var newYorkTimesArtistTextView: TextView
    private lateinit var newYorkTimesImageView: ImageView
    private lateinit var newYorkTimesOpenUrlButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_more_details)

        initModule()
        initProperties()
        initObservers()
        initMoreDetails()
    }

    private fun initModule() {
        MoreDetailsInjector.init(this)
        moreDetailsPresenter = MoreDetailsInjector.moreDetailsPresenter
    }

    private fun initProperties() {
        lastFMImageView = findViewById(R.id.lastFMImageView)
        lastFMArtistTextView = findViewById(R.id.lastFMArtistTextView)
        lastFMOpenUrlButton = findViewById(R.id.lastFMOpenUrlButton)

        wikipediaImageView = findViewById(R.id.wikipediaImageView)
        wikipediaArtistTextView = findViewById(R.id.wikipediaArtistTextView)
        wikipediaOpenUrlButton = findViewById(R.id.wikipediaOpenUrlButton)

        newYorkTimesImageView = findViewById(R.id.newYorkTimesImageView)
        newYorkTimesArtistTextView = findViewById(R.id.newYorkTimesArtistTextView)
        newYorkTimesOpenUrlButton = findViewById(R.id.newYorkTimesOpenUrlButton)
    }

    private fun initObservers() {
        moreDetailsPresenter.artistObservable
            .subscribe { value -> updateArtistInfo(value) }
    }

    private fun initMoreDetails() {
        val artistName = intent.getStringExtra(ARTIST_NAME_EXTRA) ?: ""
        moreDetailsPresenter.moreDetails(artistName)
    }

    private fun updateArtistInfo(artistUiState: MoreDetailsUiState) {
        when (artistUiState.source) {
            "Wikipedia" -> updateUIWikipedia(artistUiState)
            "LastFM" -> updateUILastFM(artistUiState)
            "New York Times" -> updateUINYTimes(artistUiState)
        }
    }

    private fun updateUIWikipedia(artistUiState: MoreDetailsUiState) {
        updateViewWikipedia(artistUiState)
        setURLButtonWikipedia(artistUiState)
    }

    private fun updateUILastFM(artistUiState: MoreDetailsUiState) {
        updateViewLastFM(artistUiState)
        setURLButtonLastFM(artistUiState)
    }

    private fun updateUINYTimes(artistUiState: MoreDetailsUiState) {
        updateViewNYTimes(artistUiState)
        setURLButtonNYTimes(artistUiState)
    }

    private fun updateViewWikipedia(artistUiState: MoreDetailsUiState) {
        runOnUiThread {
            loadImageIntoViewWikipedia(artistUiState.logoUrl)
            setArtistViewTextWikipedia(artistUiState.artistBioContent)
        }
    }

    private fun setURLButtonWikipedia(artistUiState: MoreDetailsUiState) {
        val artistUrl = artistUiState.infoURL
        setOpenUrlButtonWikipedia(artistUrl)
    }

    private fun setOpenUrlButtonWikipedia(artistUrl: String) {
        wikipediaOpenUrlButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(artistUrl)
            startActivity(intent)
        }
    }

    private fun loadImageIntoViewWikipedia(logoUrl: String) {
        Picasso.get().load(logoUrl).into(wikipediaImageView)
    }

    @Suppress("DEPRECATION")
    private fun setArtistViewTextWikipedia(artistInfoText: String) {
        wikipediaArtistTextView.text = Html.fromHtml(artistInfoText)
    }

    private fun updateViewLastFM(artistUiState: MoreDetailsUiState) {
        runOnUiThread {
            loadImageIntoViewLastFM(artistUiState.logoUrl)
            setArtistViewTextLastFM(artistUiState.artistBioContent)
        }
    }

    private fun setURLButtonLastFM(artistUiState: MoreDetailsUiState) {
        val artistUrl = artistUiState.infoURL
        setOpenUrlButtonLastFM(artistUrl)
    }

    private fun setOpenUrlButtonLastFM(artistUrl: String) {
        lastFMOpenUrlButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(artistUrl)
            startActivity(intent)
        }
    }

    private fun loadImageIntoViewLastFM(logoUrl: String) {
        Picasso.get().load(logoUrl).into(lastFMImageView)
    }

    @Suppress("DEPRECATION")
    private fun setArtistViewTextLastFM(artistInfoText: String) {
        lastFMArtistTextView.text = Html.fromHtml(artistInfoText)
    }

    private fun updateViewNYTimes(artistUiState: MoreDetailsUiState) {
        runOnUiThread {
            loadImageIntoViewNYTimes(artistUiState.logoUrl)
            setArtistViewTextNYTimes(artistUiState.artistBioContent)
        }
    }

    private fun setURLButtonNYTimes(artistUiState: MoreDetailsUiState) {
        val artistUrl = artistUiState.infoURL
        setOpenUrlButtonNYTimes(artistUrl)
    }

    private fun setOpenUrlButtonNYTimes(artistUrl: String) {
        newYorkTimesOpenUrlButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(artistUrl)
            startActivity(intent)
        }
    }

    private fun loadImageIntoViewNYTimes(logoUrl: String) {
        Picasso.get().load(logoUrl).into(newYorkTimesImageView)
    }

    @Suppress("DEPRECATION")
    private fun setArtistViewTextNYTimes(artistInfoText: String) {
        newYorkTimesArtistTextView.text = Html.fromHtml(artistInfoText)
    }


    companion object {
        const val ARTIST_NAME_EXTRA = "artistName"
    }
}
