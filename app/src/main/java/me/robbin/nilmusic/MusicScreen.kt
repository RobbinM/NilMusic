package me.robbin.nilmusic

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.VectorAsset
import me.robbin.nilmusic.ui.albums.AlbumsScreen
import me.robbin.nilmusic.ui.artists.ArtistsScreen
import me.robbin.nilmusic.ui.library.LibraryScreen
import me.robbin.nilmusic.ui.playlists.PlaylistsScreen
import me.robbin.nilmusic.ui.songs.SongsScreen

enum class MusicScreen(
    val icon: VectorAsset,
    private val body: @Composable ((MusicScreen) -> Unit) -> Unit
) {

    Library(
        icon = Icons.Filled.LibraryMusic,
        body = { onScreenChange -> LibraryScreen(onScreenChange = onScreenChange) }
    ),
    Songs(
        icon = Icons.Filled.MusicNote,
        body = { onScreenChange -> SongsScreen(onScreenChange = onScreenChange) }
    ),
    Albums(
        icon = Icons.Filled.Album,
        body = { onScreenChange -> AlbumsScreen(onScreenChange = onScreenChange) }
    ),
    Artists(
        icon = Icons.Filled.Person,
        body = { onScreenChange -> ArtistsScreen(onScreenChange = onScreenChange) }
    ),
    Playlists(
        icon = Icons.Filled.PlaylistPlay,
        body = { onScreenChange -> PlaylistsScreen(onScreenChange = onScreenChange) }
    );

    @Composable
    fun content(onScreenChange: (MusicScreen) -> Unit) {
        body(onScreenChange)
    }
}