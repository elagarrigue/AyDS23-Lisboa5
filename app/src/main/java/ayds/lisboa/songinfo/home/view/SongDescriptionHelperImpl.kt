package ayds.lisboa.songinfo.home.view

import DateFormatFactory
import ayds.lisboa.songinfo.spotify.Song.EmptySong
import ayds.lisboa.songinfo.spotify.Song
import ayds.lisboa.songinfo.spotify.Song.SpotifySong

interface SongDescriptionHelper {
    fun getSongDescriptionText(song: Song = EmptySong): String
}

internal class SongDescriptionHelperImpl(private val dateFormatFactory: DateFormatFactory) : SongDescriptionHelper {

    override fun getSongDescriptionText(song: Song): String {
        return when (song) {
            is SpotifySong ->
                "${
                    "Song: ${song.songName} " +
                            if (song.isLocallyStored) "[*]" else ""
                }\n" +
                        "Artist: ${song.artistName}\n" +
                        "Album: ${song.albumName}\n" +
                        "Release Date: ${song.createDate()}"
            else -> "Song not found"
        }
    }

    private fun SpotifySong.createDate(): String{
        return dateFormatFactory.get(this.releaseDatePrecision,this.releaseDate).createDate()
    }
}