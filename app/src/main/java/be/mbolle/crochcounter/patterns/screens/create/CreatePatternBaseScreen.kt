package be.mbolle.crochcounter.patterns.screens.create

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun CreatePatternBaseScreen(
    modifier: Modifier = Modifier,
    actions: @Composable() () -> Unit,
    contentAlignment: Alignment,
    progressIndicator: Float = 0f,
    content: @Composable() () -> Unit,
) {
    Box(modifier.fillMaxSize()) {
        Column(modifier = Modifier.align(contentAlignment)) {
            content()
        }
        Column(modifier = Modifier.align(Alignment.BottomStart)) {
            actions()
            LinearProgressIndicator(progress = { progressIndicator },
                trackColor = Color(0XFFffd6e0),
                color = Color(0XFFfe6689))
        }
    }
}

@Preview
@Composable
fun CreatePatternBaseScreenPreview() {

}