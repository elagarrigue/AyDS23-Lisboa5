package ayds.lisboa.songinfo.home.view

import ayds.lisboa.songinfo.home.model.entities.Song.EmptySong
import ayds.lisboa.songinfo.home.model.entities.Song
import ayds.lisboa.songinfo.home.model.entities.Song.SpotifySong
import ayds.lisboa.songinfo.home.view.HomeViewInjector.releaseDateCreator

interface SongDescriptionHelper {
    fun getSongDescriptionText(song: Song = EmptySong): String
}

internal class SongDescriptionHelperImpl : SongDescriptionHelper {

    override fun getSongDescriptionText(song: Song): String {
        return when (song) {
            is SpotifySong ->
                "${
                    "Song: ${song.songName} " +
                            if (song.isLocallyStored) "[*]" else ""
                }\n" +
                        "Artist: ${song.artistName}\n" +
                        "Album: ${song.albumName}\n" +
                        "Release Date: ${createDate(song)}"
            else -> "Song not found"
        }
    }

    private fun createDate(song: SpotifySong): String{
        val releaseDatePrecision = song.releaseDatePrecision
        val releaseDate = song.releaseDate
        return releaseDateCreator.createDate(releaseDatePrecision,releaseDate)
    }
}