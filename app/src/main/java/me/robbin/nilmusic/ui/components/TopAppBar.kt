package me.robbin.nilmusic.ui.components

import androidx.compose.animation.animate
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Text
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ripple.RippleIndication
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.VectorAsset
import androidx.compose.ui.unit.dp
import dev.chrisbanes.accompanist.insets.statusBarsHeight
import me.robbin.nilmusic.MusicScreen
import java.util.*

@Composable
fun MusicTabAppBar(
    allScreens: List<MusicScreen>,
    onTabSelected: (MusicScreen) -> Unit,
    currentScreen: MusicScreen,
    modifier: Modifier = Modifier
) {
    Column(modifier = Modifier.background(Color.Cyan)) {
        Spacer(modifier = Modifier.fillMaxWidth().statusBarsHeight())
        Row(modifier = modifier.preferredHeight(TabHeight).fillMaxWidth()) {
            allScreens.forEach { screen ->
                MusicTab(
                    text = screen.name.toUpperCase(Locale.ROOT),
                    icon = screen.icon,
                    onSelected = { onTabSelected(screen) },
                    selected = currentScreen == screen
                )
            }
        }
    }
}

@Composable
private fun MusicTab(
    text: String,
    icon: VectorAsset,
    onSelected: () -> Unit,
    selected: Boolean
) {
    val color = MaterialTheme.colors.onSurface
    val durationMillis = if (selected) TabFadeInAnimationDuration else TabFadeOutAnimationDuration
    val animSpec = remember {
        tween<Color>(
            durationMillis = durationMillis,
            easing = LinearEasing,
            delayMillis = TabFadeInAnimationDelay
        )
    }
    val tabTintColor = animate(
        target = if (selected) color else color.copy(alpha = InactiveTabOpacity),
        animSpec = animSpec
    )
    Row(
        modifier = Modifier
            .animateContentSize()
            .padding(16.dp)
            .preferredHeight(TabHeight)
            .selectable(
                selected = selected,
                onClick = onSelected,
                indication = RippleIndication(bounded = false)
            )
    ) {
        Icon(asset = icon, tint = tabTintColor)
        if (selected) {
            Spacer(modifier = Modifier.padding(12.dp))
            Text(text = text, color = tabTintColor)
        }
    }
}

private val TabHeight = 56.dp
private const val InactiveTabOpacity = 0.60f

private const val TabFadeInAnimationDuration = 150
private const val TabFadeInAnimationDelay = 100
private const val TabFadeOutAnimationDuration = 100