package com.buildreams.media3

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import com.buildreams.media3.ui.theme.Media3Theme


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Media3Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val uri = Uri.parse("asset:///deku.mp4")
                    VideoPlayer(uri = uri)
                }
            }
        }
    }
}

@Composable
fun VideoPlayer(uri: Uri) {
    // Create the player
    val context = LocalContext.current
    val exoPlayer = ExoPlayer.Builder(context).build().apply {
        // Build the media item.
        val mediaItem =
            MediaItem.fromUri(uri)
        // Set the media item to be played.
        setMediaItem(mediaItem)
        // Prepare the player.
        prepare()
        // Start the playback.
        playWhenReady = true
        repeatMode = Player.REPEAT_MODE_ONE
    }
    // Bind the player to the view.
    Box(
        Modifier
            .background(color = Color.Black)
    ) {
        DisposableEffect(
            AndroidView(factory = {
                PlayerView(context).apply {
                    player = exoPlayer
                    resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM
                }
            })
        ) {
            onDispose { exoPlayer.release() }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun VideoPlayerPreview() {
    Media3Theme {
        val uri = Uri.parse("asset:///deku.mp4")
        VideoPlayer(uri = uri)
    }
}