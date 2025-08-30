package be.mbolle.crochcounter.core.presentation.composables

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import be.mbolle.crochcounter.ui.theme.CrochCounterTheme

@Composable
fun SearchCrochCounter(
    modifier: Modifier = Modifier,
    initialText: String,
    wordState: TextFieldState
) {
    val interactionSource = remember { MutableInteractionSource()  }
    val focussed = interactionSource.collectIsFocusedAsState()

    BasicTextField(
        state = wordState,
        modifier = modifier,
        interactionSource = interactionSource,


        decorator = { innerTextField ->
            Row(
                Modifier
                    .padding(16.dp)
                    .bottomBorder(Border(1.dp, Color(0XFFfe6689)))
            ) {
                Icon(
                    Icons.Default.Search,
                    contentDescription = "Search icon",
                    tint = Color(0XFFffa2b8)
                )
                Spacer(Modifier.width(7.dp))


                if ((!focussed.value) && wordState.text == "") {
                    Text(
                        text = initialText,
                        color = Color(0XFFfe6689),
                        fontWeight = FontWeight.Light
                    )
                }
                innerTextField()
            }
        }
    )
}

@Preview
@Composable
fun PreviewSearchCrochCounter() {
    CrochCounterTheme {
        SearchCrochCounter(initialText = "Name of the project", wordState = TextFieldState())
    }
}


data class Border(val strokeWidth: Dp, val color: Color)


@Stable
fun Modifier.bottomBorder(border: Border) = drawBehind {
    border.let {

        drawBottomBorder(border = it, shareStart = false, shareEnd = false)
    }
}

private fun DrawScope.drawBottomBorder(
    border: Border,
    shareStart: Boolean,
    shareEnd: Boolean
) {
    val strokeWidthPx = border.strokeWidth.toPx()
    if (strokeWidthPx == 0f) return
    drawPath(
        Path().apply {
            val width = size.width
            val height = size.height
            moveTo(0f, height)
            lineTo(if (shareStart) strokeWidthPx else 0f, height - strokeWidthPx)
            lineTo(if (shareEnd) width - strokeWidthPx else width, height - strokeWidthPx)
            lineTo(width, height)
            close()
        },
        color = border.color
    )
}