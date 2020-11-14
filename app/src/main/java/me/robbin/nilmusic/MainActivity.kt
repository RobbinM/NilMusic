package me.robbin.nilmusic

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.savedinstancestate.savedInstanceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.unit.dp
import dev.chrisbanes.accompanist.insets.ProvideWindowInsets
import kotlinx.coroutines.launch
import me.robbin.nilmusic.ui.NilMusicTheme
import me.robbin.nilmusic.ui.components.MiniPlayer
import me.robbin.nilmusic.ui.components.MusicTabAppBar
import me.robbin.nilmusic.ui.components.Player
import me.robbin.utillibrary.bar.setStatusBarLightMode
import me.robbin.utillibrary.bar.translucent

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NilMusicTheme {
                ProvideWindowInsets {
                    MusicApp()
                    setStatusBarLightMode(isLightMode = !isSystemInDarkTheme())
                }
            }
        }
        translucent()
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MusicApp() {
    NilMusicTheme {
        val allScreens = MusicScreen.values().toList()
        var currentScreen by savedInstanceState { MusicScreen.Library }
        val snackbarHostState = remember { SnackbarHostState() }
        val scope = rememberCoroutineScope()
        val scaffoldState = rememberBottomSheetScaffoldState()
        BottomSheetScaffold(
            sheetContent = {
                Player(
                    mPic = "https://images.nowcoder.com/images/20200920/114064009_1600575131412_672D396DD8F54C14F761000ADA7CEE1E?x-oss-process=image/resize,m_mfit,h_100,w_100",
                    mName = "Robbin",
                    mAuthor = "Test Album"
                )
            },
            scaffoldState = scaffoldState,
            topBar = {
                MusicTabAppBar(
                    allScreens = allScreens,
                    onTabSelected = { screen -> currentScreen = screen },
                    currentScreen = currentScreen
                )
            },
            sheetPeekHeight = 0.dp
        ) { innerPadding ->
            ConstraintLayout(modifier = Modifier.fillMaxSize()) {
                val (screen, mini, snack) = createRefs()
                Box(modifier = Modifier
                    .padding(innerPadding)
                    .padding(bottom = 56.dp)
                    .background(color = Color.Red)
                    .constrainAs(screen) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    }
                    .fillMaxSize()
                ) {
                    currentScreen.content(onScreenChange = { screen -> currentScreen = screen })
                }
                Box(modifier = Modifier.constrainAs(snack) {
                    bottom.linkTo(mini.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }) {
                    ErrorSnackbar(
                        snackbarHostState = snackbarHostState,
                        onDismiss = { snackbarHostState.currentSnackbarData?.dismiss() },
                        modifier = Modifier.align(Alignment.BottomCenter)
                    )
                }
                MiniPlayer(
                    mPic = "https://images.nowcoder.com/images/20200920/114064009_1600575131412_672D396DD8F54C14F761000ADA7CEE1E?x-oss-process=image/resize,m_mfit,h_100,w_100",
                    mName = "name",
                    mAuthor = "author album",
                    onClick = {
                        scope.launch {
                            scaffoldState.bottomSheetState.expand()
                        }
                    },
                    modifier = Modifier.constrainAs(mini) {
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    })
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ErrorSnackbar(
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit = { }
) {
    SnackbarHost(
        hostState = snackbarHostState,
        snackbar = { data ->
            Snackbar(
                modifier = Modifier.padding(16.dp),
                text = {
                    Text(
                        text = data.message,
                        style = MaterialTheme.typography.body2
                    )
                },
                action = {
                    data.actionLabel?.let {
                        TextButton(onClick = onDismiss) {
                            Text(
                                text = "dismiss"
                            )
                        }
                    }
                }
            )
        },
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(Alignment.Bottom)
    )
}