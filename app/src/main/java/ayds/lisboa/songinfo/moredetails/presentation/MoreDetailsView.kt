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

    private lateinit var card1TextView: TextView
    private lateinit var card1ImageView: ImageView
    private lateinit var card1OpenUrlButton: Button

    private lateinit var card2TextView: TextView
    private lateinit var card2ImageView: ImageView
    private lateinit var card2OpenUrlButton: Button

    private lateinit var card3TextView: TextView
    private lateinit var card3ImageView: ImageView
    private lateinit var card3OpenUrlButton: Button


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
        card1ImageView = findViewById(R.id.card1ImageView)
        card1TextView = findViewById(R.id.card1TextView)
        card1OpenUrlButton = findViewById(R.id.card1OpenUrlButton)

        card2ImageView = findViewById(R.id.card2ImageView)
        card2TextView = findViewById(R.id.card2TextView)
        card2OpenUrlButton = findViewById(R.id.card2OpenUrlButton)

        card3ImageView = findViewById(R.id.card3ImageView)
        card3TextView = findViewById(R.id.card3TextView)
        card3OpenUrlButton = findViewById(R.id.card3OpenUrlButton)
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
            "Wikipedia" -> updateUICard1(artistUiState)
            "Last FM" -> updateUICard2(artistUiState)
            "New York Times" -> updateUICard3(artistUiState)
        }
    }

    private fun updateUICard1(artistUiState: MoreDetailsUiState) {
        updateViewCard1(artistUiState)
        setURLButtonCard1(artistUiState)
    }

    private fun updateUICard2(artistUiState: MoreDetailsUiState) {
        updateViewCard2(artistUiState)
        setURLButtonCard2(artistUiState)
    }

    private fun updateUICard3(artistUiState: MoreDetailsUiState) {
        updateViewCard3(artistUiState)
        setURLButtonCard3(artistUiState)
    }

    private fun updateViewCard1(artistUiState: MoreDetailsUiState) {
        runOnUiThread {
            loadImageIntoViewCard1(artistUiState.logoUrl)
            setArtistViewTextCard1(artistUiState.artistBioContent)
        }
    }

    private fun setURLButtonCard1(artistUiState: MoreDetailsUiState) {
        val artistUrl = artistUiState.infoURL
        setOpenUrlButtonCard1(artistUrl)
    }

    private fun setOpenUrlButtonCard1(artistUrl: String) {
        card1OpenUrlButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(artistUrl)
            startActivity(intent)
        }
    }

    private fun loadImageIntoViewCard1(logoUrl: String) {
        Picasso.get().load(logoUrl).into(card1ImageView)
    }

    @Suppress("DEPRECATION")
    private fun setArtistViewTextCard1(artistInfoText: String) {
        card1TextView.text = Html.fromHtml(artistInfoText)
    }

    private fun updateViewCard2(artistUiState: MoreDetailsUiState) {
        runOnUiThread {
            loadImageIntoViewCard2(artistUiState.logoUrl)
            setArtistViewTextCard2(artistUiState.artistBioContent)
        }
    }

    private fun setURLButtonCard2(artistUiState: MoreDetailsUiState) {
        val artistUrl = artistUiState.infoURL
        setOpenUrlButtonCard2(artistUrl)
    }

    private fun setOpenUrlButtonCard2(artistUrl: String) {
        card2OpenUrlButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(artistUrl)
            startActivity(intent)
        }
    }

    private fun loadImageIntoViewCard2(logoUrl: String) {
        Picasso.get().load(logoUrl).into(card2ImageView)
    }

    @Suppress("DEPRECATION")
    private fun setArtistViewTextCard2(artistInfoText: String) {
        card2TextView.text = Html.fromHtml(artistInfoText)
    }

    private fun updateViewCard3(artistUiState: MoreDetailsUiState) {
        runOnUiThread {
            loadImageIntoViewCard3(artistUiState.logoUrl)
            setArtistViewTextCard3(artistUiState.artistBioContent)
        }
    }

    private fun setURLButtonCard3(artistUiState: MoreDetailsUiState) {
        val artistUrl = artistUiState.infoURL
        setOpenUrlButtonCard3(artistUrl)
    }

    private fun setOpenUrlButtonCard3(artistUrl: String) {
        card3OpenUrlButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(artistUrl)
            startActivity(intent)
        }
    }

    private fun loadImageIntoViewCard3(logoUrl: String) {
        Picasso.get().load(logoUrl).into(card3ImageView)
    }

    @Suppress("DEPRECATION")
    private fun setArtistViewTextCard3(artistInfoText: String) {
        card3TextView.text = Html.fromHtml(artistInfoText)
    }


    companion object {
        const val ARTIST_NAME_EXTRA = "artistName"
    }
}
