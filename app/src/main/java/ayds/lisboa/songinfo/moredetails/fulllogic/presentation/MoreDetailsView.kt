package ayds.lisboa.songinfo.moredetails.fulllogic.presentation


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import ayds.lisboa.songinfo.R
import ayds.lisboa.songinfo.moredetails.fulllogic.MoreDetailsInjector
import com.squareup.picasso.Picasso
import java.util.*

interface MoreDetailsView

internal class MoreDetailsViewActivity: MoreDetailsView, AppCompatActivity() {

    private lateinit var moreDetailsPresenter: MoreDetailsPresenter
    private lateinit var artistTextView: TextView
    private lateinit var imageView: ImageView
    private lateinit var openUrlButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_other_info)

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
        artistTextView = findViewById(R.id.artistInfoTextView)
        imageView = findViewById(R.id.imageView)
        openUrlButton = findViewById(R.id.openUrlButton)
    }

    private fun initObservers() {
        moreDetailsPresenter.artistObservable
            .subscribe { value -> updateArtistInfo(value) }
    }

    private fun initMoreDetails(){
        val artistName = intent.getStringExtra(ARTIST_NAME_EXTRA) ?: ""
        moreDetailsPresenter.moreDetails(artistName)
    }

    private fun updateArtistInfo(artistUiState: MoreDetailsUiState){
        updateView(artistUiState)
        setURLButton(artistUiState)
    }

    private fun updateView(artistUiState: MoreDetailsUiState) {
        runOnUiThread {
            loadImageIntoView(artistUiState.lastFMImageUrl)
            setArtistViewText(artistUiState.artistBioContent)
        }
    }

    private fun setURLButton(artistUiState: MoreDetailsUiState) {
        val artistUrl = artistUiState.artistURL
        setOpenUrlButton(artistUrl)
    }

    private fun setOpenUrlButton(artistUrl: String) {
        openUrlButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(artistUrl)
            startActivity(intent)
        }
    }

    private fun loadImageIntoView(lastFMImageUrl: String){
        Picasso.get().load(lastFMImageUrl).into(imageView)
    }

    @Suppress("DEPRECATION")
    private fun setArtistViewText(artistInfoText: String){
        artistTextView.text = Html.fromHtml(artistInfoText)
    }

    companion object {
        const val ARTIST_NAME_EXTRA = "artistName"
    }
}
