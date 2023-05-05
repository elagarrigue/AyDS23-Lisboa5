package ayds.lisboa.songinfo.moredetails.fulllogic.model.entities.repository.externalArtist.artists

import ayds.lisboa.songinfo.moredetails.fulllogic.model.entities.Artist
import com.google.gson.Gson
import com.google.gson.JsonObject


private const val ARTIST = "artist"
private const val BIO = "bio"
private const val CONTENT = "content"
private const val URL = "url"

interface LastFMToArtistResolver {
    fun getArtistFromExternalData(serviceData: String?) : Artist.ArtistData?  //Este va a ser el equivalente a getArtistFromLastFMAPI de otherinfowindows
}

internal class JsonArtistResolver: LastFMToArtistResolver {

    override fun getArtistFromExternalData(serviceData: String?): Artist.ArtistData? =
        try {
            serviceData.getArtistFromCallResponse().let { item ->
                Artist.ArtistData(
                    item.getArtistName(), item.getArtistBioContent(), item.getArtistUrl()
                )
            }
        } catch (e: Exception) {
            null
        }


    private fun String?.getArtistFromCallResponse(): JsonObject {
        val gsonObject = Gson()
        val jObjFromGson = gsonObject.fromJson(this, JsonObject::class.java)
        return jObjFromGson[ARTIST].asJsonObject
    }

    private fun JsonObject.getArtistName() = this[ARTIST].asString

    private fun JsonObject.getArtistBioContent() = this[BIO].asJsonObject[CONTENT].asString

    private fun JsonObject.getArtistUrl() = this[URL].asString


}