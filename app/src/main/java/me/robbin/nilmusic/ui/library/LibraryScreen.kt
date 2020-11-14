package me.robbin.nilmusic.ui.library

import androidx.compose.foundation.ScrollableRow
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.chrisbanes.accompanist.coil.CoilImage
import me.robbin.nilmusic.MusicScreen
import me.robbin.nilmusic.R
import me.robbin.nilmusic.data.Song
import me.robbin.nilmusic.data.songList
import me.robbin.utillibrary.toast.toast

@Composable
fun LibraryScreen(onScreenChange: (MusicScreen) -> Unit = {}) {
    Column {
        Text(text = stringResource(id = R.string.strRecent))
        ScrollableRow {
            songList.forEach { song ->
                SongItem(item = song, onClick = {
                    toast(message = song.name)
                })
            }
        }
        Text(text = stringResource(id = R.string.strPopularSongs))
    }
}

@Composable
fun SongItem(
    item: Song,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .clickable(onClick = onClick)
            .padding(top = 5.dp, bottom = 5.dp, start = 10.dp, end = 10.dp)
    ) {
        CoilImage(
            data = item.picture,
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(10.dp))
        )
        Text(
            text = item.name,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}