package ayds.lisboa.songinfo.moredetails.fulllogic

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import ayds.lisboa.songinfo.R
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.squareup.picasso.Picasso
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.*


private const val IMAGE_URL = "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d4/Lastfm_logo.svg/320px-Lastfm_logo.svg.png"
private const val RETROFIT_URL = "https://ws.audioscrobbler.com/2.0/"
private const val ARTIST = "artist"
private const val ARTIST_NAME = "artistName"
private const val BIO = "bio"
private const val CONTENT = "content"
private const val URL = "url"
private const val HTML_OPENING_TAG = "<html><div width=400><font face=\"arial\">"
private const val HTML_CLOSING_TAG = "</font></div></html>"
private const val PREFIX = "[*]"

class OtherInfoWindow : AppCompatActivity() {
    private lateinit var artistTextView: TextView
    private lateinit var dataBase: DataBase
    private lateinit var lastFMAPI: LastFMAPI
    private lateinit var artistName: String
    private lateinit var imageView: ImageView
    private lateinit var openUrlButton: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_other_info)

        initProperties()
        initAPI()
        initDataBase()
        initIntentData()

        updateArtistInfoAsync()
    }

    private fun initProperties(){
        artistTextView = findViewById(R.id.artistInfoTextView)
        imageView = findViewById(R.id.imageView)
        openUrlButton = findViewById(R.id.openUrlButton)
    }

    private fun initIntentData(){
        artistName = intent.getStringExtra(ARTIST_NAME) ?: ""
    }

    private fun initAPI() {
        val retrofit = createRetrofit()
        lastFMAPI = retrofit.create(LastFMAPI::class.java)
    }

    private fun initDataBase() {
        dataBase = DataBase(this)
    }

    private fun updateArtistInfoAsync() {
        Thread { updateArtistInfo() }.start()
    }

    private fun createRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(RETROFIT_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
    }

    @Suppress("DEPRECATION")
    private fun updateView(artistInfoText: String) {
        runOnUiThread {
            Picasso.get().load(IMAGE_URL).into(imageView)
            artistTextView.text = Html.fromHtml(artistInfoText)
        }
    }

    private fun setURLButton() {
        val artist = getArtistAsJsonObject()
        val artistUrl = artist.getArtistUrl()
        artistUrl.setOpenUrlButton()
    }

    private fun getArtistAsJsonObject(): JsonObject {
        val callResponse = lastFMAPI.getArtistInfo(artistName).execute()
        return getArtistFromCallResponse(callResponse)
    }

    private fun getArtistFromCallResponse(callResponse: Response<String>): JsonObject{
        val gsonObject = Gson()
        val jObjFromGson = gsonObject.fromJson(callResponse.body(), JsonObject::class.java)
        return jObjFromGson[ARTIST].asJsonObject
    }

    private fun JsonObject.getArtistBioContent() = this[BIO].asJsonObject[CONTENT]

    private fun JsonObject.getArtistUrl() = this[URL]

    private fun artistBioAsHTML(artistBioContent: String): String {
        val artistBioContentReformatted = artistBioContent.reformatArtistBio()
        return textToHtml(artistBioContentReformatted)
    }

    private fun String.reformatArtistBio() = this.replace("\\n", "\n")

    private fun JsonElement.setOpenUrlButton() {
        val urlString = this.asString
        openUrlButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(urlString)
            startActivity(intent)
        }
    }

    private fun textToHtml(text: String): String {
        val builder = StringBuilder()
        val textWithBold = textAsBold(text)

        builder.append(HTML_OPENING_TAG)
        builder.append(textWithBold)
        builder.append(HTML_CLOSING_TAG)

        return builder.toString()
    }

    private fun textAsBold(text: String) : String {
        return text
            .replace("'", " ")
            .replace("\n", "<br>")
            .replace(
                "(?i)$artistName".toRegex(),
                "<b>" + artistName.uppercase(Locale.getDefault()) + "</b>"
            )
    }

    companion object {
        const val ARTIST_NAME_EXTRA = "artistName"
    }

    private fun updateArtistInfo() {
        val artist = getArtist()
        val bioContent = obtainBioContent(artist)
        val artistInfoHTML = artistBioAsHTML(bioContent)
        updateView(artistInfoHTML)
        setURLButton()
    }

    private fun obtainBioContent(artist: Artist): String {
        if (artist is Artist.ArtistData){
            return if (artist.isLocallyStored) {
                "$PREFIX${artist.artistBioContent}"
            } else artist.artistBioContent
        }
        return "No Results"
    }

    private fun getArtist(): Artist {
        var artist: Artist?
        artist = dataBase.getArtist(artistName)
        when (artist) {
            Artist.EmptyArtist -> try {
                artist = getArtistFromLastFMAPI()
                saveArtistInfo(artist)
            } catch (e: Exception) {
                artist = null
            }
            is  Artist.ArtistData -> markArtistAsLocal(artist)
        }
        return artist ?: Artist.EmptyArtist
    }

    private fun getArtistFromLastFMAPI(): Artist.ArtistData {
        val artistJsonObj=getArtistAsJsonObject()
        val artistInfo=artistJsonObj.getArtistBioContent()
        val artistURL=artistJsonObj.getArtistUrl()
        return Artist.ArtistData(artistName,artistInfo.asString,artistURL.asString)
    }

    private fun saveArtistInfo(artist: Artist.ArtistData) = dataBase.saveArtist(artistName, artist.artistBioContent,artist.artistURL)

    private fun markArtistAsLocal(artist: Artist.ArtistData) {
        artist.isLocallyStored = true
    }
}