package be.mbolle.crochcounter.projects.presentation.composables

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StampedPathEffectStyle
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import be.mbolle.crochcounter.R
import be.mbolle.crochcounter.projects.model.Pattern
import be.mbolle.crochcounter.patterns.model.PatternItem
import be.mbolle.crochcounter.projects.model.SubPattern
import be.mbolle.crochcounter.ui.theme.CrochCounterTheme

val sourceSansProFont = FontFamily(
    Font(
        R.font.source_sans_pro, FontWeight.Normal
    ),
    Font(
        R.font.source_sans_pro_black_italic, FontWeight.Bold, FontStyle.Italic,
    ),
    Font(
        R.font.source_sans_pro_black, FontWeight.Bold
    )
)

@Composable
fun PatternTimeline(modifier: Modifier = Modifier, patterns: Pattern) {
    val patternItems = patterns.patterns()

    Column(modifier = modifier) {
        Text(
            patterns.getSubtitle().uppercase(),
            color = Color(0XFFfe6689),
            fontFamily = sourceSansProFont,
            fontWeight = FontWeight.Bold,
            fontSize = 25.sp
        )
        //(modifier = Modifier.height(IntrinsicSize.Min
        Row() {
            DecorationTimeline(modifier = Modifier.fillMaxHeight())

            LazyColumn(modifier = modifier, userScrollEnabled = false) {
                itemsIndexed(patternItems) { index, item ->
                    var paddingValues =
                        if (index == 0)
                            PaddingValues(top = 10.dp, bottom = 40.dp)
                        else
                            PaddingValues(vertical = 5.dp)

                    PatternLineItem(
                        number = index,
                        patternItem = item,
                        modifier = Modifier
                            .animateItem(
                                fadeInSpec = tween(durationMillis = 250),
                                fadeOutSpec = tween(durationMillis = 100),
                                placementSpec = spring(stiffness = Spring.StiffnessLow, dampingRatio = Spring.DampingRatioMediumBouncy)
                            )

                            .padding(paddingValues),
                        isActive = patterns.activeLine - 1 == index
                    )
                }
            }
            // this worked:
//            Column {
//
//                for ((index, patternItem) in pattern.patterns().withIndex()) {
//                    var paddingValues =
//                        if (index == 0)
//                            PaddingValues(top = 10.dp, bottom = 40.dp)
//                        else
//                            PaddingValues(vertical = 5.dp)
//
//
//                    PatternLineItem(
//                        number = index,
//                        patternItem = patternItem,
//                        modifier = Modifier.padding(paddingValues),
//                        isActive = pattern.activeLine - 1 == index
//                    )
//                }
//            }
        }
    }
}

@Composable
fun DecorationTimeline(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier.width(15.dp)) {
        val canvasWidth = size.width
        val canvasHeight = size.height

        val dotRadius = 15.dp / 3
        val dotSpacing = dotRadius

        val circle = Path()
        circle.addOval(
            Rect(
                offset = Offset(0f, 0f),
                size = Size(4f, 4f),
            )
        )
        val pathEffect = PathEffect.stampedPathEffect(
            shape = circle,
            advance = dotSpacing.toPx(),
            phase = 0f,
            style = StampedPathEffectStyle.Translate
        )

        val centerX = size.width / 2

        drawOval(
            size = Size(15f, 15f),
            color = Color(0XFFfea4b9),
            topLeft = Offset(centerX - (centerX / 2) + 3, 2f)
        )
        drawLine(
            start = Offset((canvasWidth / 2) - 2, 15f / 2),
            end = Offset((canvasWidth / 2) - 2, canvasHeight),
            pathEffect = pathEffect,
            color = Color(0XFFfea4b9),
            cap = StrokeCap.Round
        )
    }
}

@Preview
@Composable
private fun DecorationTimelinePreview() {
    DecorationTimeline(
        modifier = Modifier.size(10.dp, 30.dp)
    )
}

@Composable
fun PatternLineItem(
    modifier: Modifier = Modifier,
    patternItem: PatternItem,
    number: Int,
    isActive: Boolean = false
) {
    // should be extracted outside this
    val padding =
        if (isActive)
            PaddingValues(start = 3.dp, top = 10.dp, end = 3.dp, bottom = 15.dp)
        else
            PaddingValues(all = 3.dp)

    val textSize =
        if (isActive)
            40.sp
        else
            20.sp

    val capacity = if (!isActive) modifier.alpha(0.5F) else modifier

    Box(
        modifier = capacity
            .background(Color(0XFFfbc8d8))
            .border(1.dp, Color(0XFFffd6e0))
            .fillMaxWidth()
            .padding(padding)
    ) {
        Row(verticalAlignment = Alignment.Top) {
            Text(
                "${number + 1}",
                color = Color(0XFFfe6689),
                fontSize = textSize, // more bold?
                fontFamily = sourceSansProFont,
                fontStyle = FontStyle.Italic,
                style = TextStyle(
                    platformStyle = PlatformTextStyle(
                        includeFontPadding = false
                    ),
                    baselineShift = BaselineShift(0F),
                    lineHeightStyle = LineHeightStyle(
                        alignment = LineHeightStyle.Alignment.Proportional,
                        trim = LineHeightStyle.Trim.None
                    )
                ),
            )
            Column(modifier = Modifier.padding(start = 15.dp)) {
                Text(
                    text = patternItem.description,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 15.sp,
                    fontFamily = sourceSansProFont,
                    fontWeight = FontWeight.Bold,
                    color = Color(0XFFfe6689),
                )

                patternItem.comment?.also {
                    Text(
                        text = it,
                        overflow = TextOverflow.Ellipsis,
                        textDecoration = TextDecoration.Underline
                    )
                }
            }
        }
    }
}

@Composable
@Preview
fun FirstPatternLinePreview() {
    PatternLineItem(
        number = 1,
        patternItem =
            PatternItem(
                description = "R1: 6 SC in a MR (6)",
                comment = "cool"
            )
    )
}

@Preview
@Composable
fun OtherPatternLinePreview() {
    PatternLineItem(
        number = 2,
        isActive = true,
        patternItem =
            PatternItem(
                description = "R1: 6 SC in a MR (6)",
                comment = "cool"
            )
    )
}

@Composable
@Preview
fun TextNoLineHeight() {

    Text(
        text = "App",
        fontFamily = sourceSansProFont,
        style = TextStyle(
            baselineShift = BaselineShift(0F),
            lineHeightStyle = LineHeightStyle(
                alignment = LineHeightStyle.Alignment.Proportional,
                trim = LineHeightStyle.Trim.None
            )
        ),
        modifier = Modifier
            .padding(bottom = 0.dp)
    )


}

@Preview(showBackground = true, backgroundColor = 0XFFfee2e9)
@Composable
fun PatternTimelinePreview() {
    val customPattern = Pattern(
        1,
        title = "Bee",
        subPatterns = listOf(
            SubPattern(
                "EARS",
                lines = listOf(
                    PatternItem(
                        description = "R1: 6 SC in a MR (6)",
                        comment = "cool"
                    ),
                    PatternItem(
                        description = "R2: [SC, INC]x3 (9)"
                    ),
                    PatternItem(
                        description = "R3: [2 SC, INC]x3 (12)"
                    ),
                    PatternItem(
                        description = "R5- 8:(4 Rounds) 16 SC (16)"
                    )
                )
            )
        )
    )

    print(customPattern)
    CrochCounterTheme {
        PatternTimeline(
            patterns = customPattern
        )
    }
}
