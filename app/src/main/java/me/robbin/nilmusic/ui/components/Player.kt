package me.robbin.nilmusic.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Pause
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material.icons.rounded.SkipNext
import androidx.compose.material.icons.rounded.SkipPrevious
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.chrisbanes.accompanist.coil.CoilImage
import dev.chrisbanes.accompanist.insets.statusBarsHeight
import me.robbin.nilmusic.R

@Composable
fun MiniPlayer(
    mPic: String,
    mName: String,
    mAuthor: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    onPreClick: () -> Unit = {},
    onPauseClick: () -> Unit = {},
    onNextClick: () -> Unit = {}
) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .drawShadow(elevation = 0.1.dp)
            .selectable(selected = false, onClick = onClick)
    ) {
        val (img, name, author, pre, pause, next) = createRefs()
        CoilImage(
            data = mPic,
            modifier = Modifier
                .height(40.dp)
                .width(40.dp)
                .clip(MaterialTheme.shapes.medium)
                .constrainAs(img) {
                    start.linkTo(parent.start, margin = 16.dp)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
        )
        Text(
            text = mName,
            modifier = Modifier.constrainAs(name) {
                start.linkTo(img.end, margin = 8.dp)
                top.linkTo(img.top)
            },
            style = MaterialTheme.typography.body1
        )
        Text(
            text = mAuthor,
            modifier = Modifier.constrainAs(author) {
                start.linkTo(img.end, margin = 8.dp)
                bottom.linkTo(img.bottom)
            },
            style = MaterialTheme.typography.subtitle2
        )
        IconButton(
            onClick = onNextClick,
            modifier = Modifier.constrainAs(next) {
                end.linkTo(parent.end, margin = 16.dp)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            }) {
            Icon(asset = Icons.Rounded.SkipNext)
        }
        IconButton(
            onClick = onPauseClick,
            modifier = Modifier.constrainAs(pause) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                end.linkTo(next.start, margin = 10.dp)
            }) {
            Icon(asset = Icons.Rounded.Pause)
        }
        IconButton(
            onClick = onPreClick,
            modifier = Modifier.constrainAs(pre) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                end.linkTo(pause.start, margin = 10.dp)
            }) {
            Icon(asset = Icons.Rounded.SkipPrevious)
        }
    }
}

@Composable
fun Player(
    mPic: String,
    mName: String,
    mAuthor: String
) {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (title, spacer, pic, name, author, pre, pause, next, progress) = createRefs()
        var sliderPosition by remember { mutableStateOf(0f) }
        var playState by remember { mutableStateOf(false) }
        Spacer(modifier = Modifier
            .statusBarsHeight()
            .fillMaxWidth()
            .constrainAs(spacer) {
                top.linkTo(parent.top)
            }
        )
        Text(text = stringResource(id = R.string.strNowPlaying),
            modifier = Modifier.constrainAs(title) {
                top.linkTo(spacer.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )
        CoilImage(data = mPic,
            modifier = Modifier
                .size(370.dp)
                .constrainAs(pic) {
                    top.linkTo(title.bottom, margin = 24.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .drawShadow(
                    elevation = 1.dp,
                    shape = RoundedCornerShape(8.dp)
                )
                .clip(RoundedCornerShape(8.dp))
        )
        Text(
            text = mName,
            modifier = Modifier.constrainAs(name) {
                top.linkTo(pic.bottom)
                start.linkTo(parent.start)
            },
            maxLines = 1
        )
        Text(
            text = mAuthor,
            modifier = Modifier.constrainAs(author) {
                top.linkTo(name.bottom)
                start.linkTo(parent.start)
            },
            maxLines = 1
        )
        IconButton(onClick = {},
            modifier = Modifier.constrainAs(pre) {
                bottom.linkTo(pause.bottom)
                end.linkTo(pause.start, margin = 24.dp)
            }
                .size(32.dp)
        ) {
            Icon(asset = Icons.Rounded.SkipPrevious)
        }
        IconButton(onClick = {
            playState = !playState
        },
            modifier = Modifier.constrainAs(pause) {
                bottom.linkTo(parent.bottom, margin = 32.dp)
                centerHorizontallyTo(parent)
            }
                .size(64.dp)
                .clip(CircleShape)
                .drawShadow(elevation = 5.dp, shape = CircleShape)
                .background(Color.Magenta)
        ) {
            Image(
                asset = if (playState) Icons.Rounded.Pause
                else Icons.Rounded.PlayArrow
            )
        }
        IconButton(onClick = {},
            modifier = Modifier.constrainAs(next) {
                bottom.linkTo(pause.bottom)
                start.linkTo(pause.end, margin = 24.dp)
            }
                .size(32.dp)
        ) {
            Icon(asset = Icons.Rounded.SkipNext)
        }
        Slider(
            value = sliderPosition,
            onValueChange = { sliderPosition = it },
            valueRange = 0f..100f,
            onValueChangeEnd = {
                // launch some business logic update with the state you hold
                // viewModel.updateSelectedSliderValue(sliderPosition)
            },
            thumbColor = MaterialTheme.colors.secondary,
            activeTrackColor = MaterialTheme.colors.secondary,
            modifier = Modifier.constrainAs(progress) {
                bottom.linkTo(parent.bottom)
            }
        )
    }
}