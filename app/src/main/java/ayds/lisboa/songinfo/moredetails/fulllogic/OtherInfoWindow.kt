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
import java.io.IOException
import java.util.*


private const val IMAGE_URL = "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d4/Lastfm_logo.svg/320px-Lastfm_logo.svg.png"
private const val RETROFIT_URL = "https://ws.audioscrobbler.com/2.0/"
private const val ARTIST = "artist"
private const val BIO = "bio"
private const val CONTENT = "content"
private const val URL = "url"

class OtherInfoWindow : AppCompatActivity() {
    private var artistInfoPanel: TextView? = null
    private var dataBase: DataBase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_other_info)
        artistInfoPanel = findViewById(R.id.textPane2)
        dataBase = dataBaseConnection()
        openArtistInfo(intent.getStringExtra("artistName"),dataBase!!)
    }

    private fun getArtistInfo(artistName: String?, dataBase: DataBase) {
        val retrofit = createRetrofit()
        val lastFMAPI = retrofit.create(LastFMAPI::class.java)

        Thread {
            setTextPaneWithArtistInfo(artistName,dataBase,lastFMAPI)
        }.start()
    }

    private fun setTextPaneWithArtistInfo(artistName: String?, dataBase: DataBase,lastFMAPI: LastFMAPI) {
        val artistInfoText = getArtistInfoText(artistName, dataBase,lastFMAPI)
        setTextPane(artistInfoText)
    }

    private fun getArtistInfoText(artistName: String?,dataBase: DataBase,lastFMAPI: LastFMAPI) =
        artistName?.let { dataBase.getInfo(it)?.let { "[*]$it" } } ?: getTextFromService(lastFMAPI, artistName, dataBase)

    private fun createRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(RETROFIT_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
    }

    @Suppress("DEPRECATION")
    private fun setTextPane(artistInfoText: String) {
        runOnUiThread {
            Picasso.get().load(IMAGE_URL).into(findViewById<View>(R.id.imageView) as ImageView)
            artistInfoPanel!!.text = Html.fromHtml(artistInfoText)
        }
    }

    private fun dataBaseConnection()= DataBase(this)

    private fun openArtistInfo(artist: String?, dataBase: DataBase) {
        getArtistInfo(artist,dataBase)
    }

    companion object {
        const val ARTIST_NAME_EXTRA = "artistName"
        fun textToHtml(text: String, term: String?): String {
            val builder = StringBuilder()
            builder.append("<html><div width=400>")
            builder.append("<font face=\"arial\">")
            val textWithBold = text
                .replace("'", " ")
                .replace("\n", "<br>")
                .replace(
                    "(?i)$term".toRegex(),
                    "<b>" + term!!.uppercase(Locale.getDefault()) + "</b>"
                )
            builder.append(textWithBold)
            builder.append("</font></div></html>")
            return builder.toString()
        }
    }

    private fun getTextFromService(lastFMAPI: LastFMAPI, artistName: String?, dataBase: DataBase): String {
        var textFromService = "No Results"
        try {
            val artist = getArtistAsJsonObject(lastFMAPI,artistName)
            val artistBioContent = artist.getArtistBioContent()
            val artistUrl = artist.getArtistUrl()

            if (artistBioContent != null) {
                val artistBioContentReformatted = artistBioContent.asString.replace("\\n", "\n")
                textFromService = textToHtml(artistBioContentReformatted, artistName)

                dataBase.saveArtist(artistName, textFromService)
            }
            artistUrl.setOpenUrlButton()

        } catch (exception: IOException) {
            exception.printStackTrace()
        }
        return textFromService
    }

    private fun getArtistAsJsonObject(lastFMAPI: LastFMAPI,artistName: String?): JsonObject {
        val callResponse = lastFMAPI.getArtistInfo(artistName).execute()
        val gsonObject = Gson()
        val jObjFromGson = gsonObject.fromJson(callResponse.body(), JsonObject::class.java)
        return jObjFromGson[ARTIST].asJsonObject
    }

    private fun JsonObject.getArtistBioContent() = this[BIO].asJsonObject[CONTENT]

    private fun JsonObject.getArtistUrl() = this[URL]

    private fun JsonElement.setOpenUrlButton() {
        val urlString = this.asString
        findViewById<View>(R.id.openUrlButton).setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(urlString)
            startActivity(intent)
        }
    }
}