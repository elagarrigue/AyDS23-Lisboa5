package ayds.lisboa.songinfo.moredetails.fulllogic

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.util.Log
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

class OtherInfoWindow : AppCompatActivity() {
    private var textPane2: TextView? = null

    //private JPanel imagePanel;
    // private JLabel posterImageLabel;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_other_info)
        textPane2 = findViewById(R.id.textPane2)
        open(intent.getStringExtra("artistName"))
    }

    private fun getArtistInfo(artistName: String?) {

        // create
        val retrofit = createRetrofit()
        val lastFMAPI = retrofit.create(LastFMAPI::class.java)

        Thread {
            var artistInfoText = DataBase.getInfo(dataBase, artistName)
            if (artistInfoText != null) { // exists in db
                artistInfoText = "[*]$artistInfoText"
            } else { // get from service
                artistInfoText=getTextFromService(lastFMAPI,artistName)
            }
            val imageUrl =
                "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d4/Lastfm_logo.svg/320px-Lastfm_logo.svg.png"

            val finalText = artistInfoText
            runOnUiThread {
                Picasso.get().load(imageUrl).into(findViewById<View>(R.id.imageView) as ImageView)
                textPane2!!.text = Html.fromHtml(finalText)
            }
        }.start()
    }

    private fun createRetrofit(): Retrofit{
        return Retrofit.Builder()
                .baseUrl("https://ws.audioscrobbler.com/2.0/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .build()
    }

    private var dataBase: DataBase? = null
    private fun open(artist: String?) {
        dataBase = DataBase(this)
        DataBase.saveArtist(dataBase, "test", "sarasa")
        Log.e("TAG", "" + DataBase.getInfo(dataBase, "test"))
        Log.e("TAG", "" + DataBase.getInfo(dataBase, "nada"))
        getArtistInfo(artist)
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

    private fun getTextFromService(lastFMAPI: LastFMAPI, artistName: String?): String {
        val callResponse: Response<String>
        var textFromService= "No Results"
        try {
            callResponse = lastFMAPI.getArtistInfo(artistName).execute()

            val gsonObject = Gson()
            val jObjFromGson = gsonObject.fromJson(callResponse.body(), JsonObject::class.java)
            val artist = jObjFromGson["artist"].asJsonObject
            val artistBio = artist["bio"].asJsonObject
            val artistBioContent = artistBio["content"]
            val artistUrl = artist["url"]

            if (artistBioContent == null) {
                textFromService = "No Results"
            } else {
                val artistBioContentReformatted = artistBioContent.asString.replace("\\n", "\n")
                textFromService = textToHtml(artistBioContentReformatted, artistName)

                DataBase.saveArtist(dataBase, artistName, textFromService)
            }
            setOpenUrlButton(artistUrl)
        } catch (exception: IOException) {
            exception.printStackTrace()
        }
        return textFromService
    }

    private fun setOpenUrlButton(artistUrl: JsonElement) {
        val urlString = artistUrl.asString
        findViewById<View>(R.id.openUrlButton).setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(urlString)
            startActivity(intent)
        }
    }
}