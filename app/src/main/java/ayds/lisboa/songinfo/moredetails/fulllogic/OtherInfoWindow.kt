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
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.IOException
import java.util.*


private const val IMAGE_URL = "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d4/Lastfm_logo.svg/320px-Lastfm_logo.svg.png"
private const val RETROFIT_URL = "https://ws.audioscrobbler.com/2.0/"
private const val ARTIST = "artist"
private const val BIO = "bio"
private const val CONTENT = "content"
private const val URL = "url"

class OtherInfoWindow : AppCompatActivity() {
    private lateinit var artistTextView: TextView
    private lateinit var dataBase: DataBase
    private lateinit var retrofit: Retrofit
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
        artistTextView = findViewById(R.id.artistInfoPane)
        imageView = findViewById<View>(R.id.imageView) as ImageView
        openUrlButton = findViewById(R.id.openUrlButton)
    }

    private fun initIntentData(){
        artistName = intent.getStringExtra("artistName") ?: ""
    }

    private fun initAPI() {
        retrofit = createRetrofit()
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

    private fun updateArtistInfo() {
        val artistInfoText = getArtistInfoText()
        saveArtistInfo(artistInfoText)
        val artistInfoHTML = artistBioAsHTML(artistInfoText)
        setTextPane(artistInfoHTML)
        setURLButton()
    }

    private fun getArtistInfoText():String {
        val artistInfo = obtainArtistInfo()
        return if (artistInfo != "") {
            "[*]$artistInfo"
        }
        else{
            getTextFromService()
        }
    }

    private fun saveArtistInfo(textFromService: String) = dataBase.saveArtist(artistName, textFromService)

    private fun obtainArtistInfo():String? {
        return dataBase.getInfo(artistName)
    }

    @Suppress("DEPRECATION")
    private fun setTextPane(artistInfoText: String) {
        runOnUiThread {
            Picasso.get().load(IMAGE_URL).into(imageView)
            artistTextView.text = Html.fromHtml(artistInfoText)
        }
    }

    private fun getTextFromService(): String {
        var textFromService = "No Results"
        try {
            val artist = getArtistAsJsonObject()
            val artistBioContent = artist.getArtistBioContent()

            if (artistBioContent != null) {
                textFromService = artistBioContent.asString
            }
        } catch (exception: IOException) {
            exception.printStackTrace()
        }
        return textFromService
    }

    private fun setURLButton() {
        val artist = getArtistAsJsonObject()
        val artistUrl = artist.getArtistUrl()
        artistUrl.setOpenUrlButton()
    }

    private fun getArtistAsJsonObject(): JsonObject {
        val callResponse = lastFMAPI.getArtistInfo(artistName).execute()
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
        builder.append("<html><div width=400>")
        builder.append("<font face=\"arial\">")
        val textWithBold = textAsBold(text)
        builder.append(textWithBold)
        builder.append("</font></div></html>")
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
}