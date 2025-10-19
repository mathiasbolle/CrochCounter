package be.mbolle.crochcounter.patterns.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import be.mbolle.crochcounter.R
import be.mbolle.crochcounter.patterns.screens.model.Pattern
import be.mbolle.crochcounter.patterns.screens.model.PatternItem
import be.mbolle.crochcounter.patterns.screens.model.PatternItemSection
import be.mbolle.crochcounter.patterns.screens.model.SubtitlePatternItem
import be.mbolle.crochcounter.projects.presentation.composables.sourceSansProFont
import be.mbolle.crochcounter.ui.theme.CrochCounterTheme

@Composable
fun PatternMainScreen(modifier: Modifier = Modifier) {
    val patternMainViewModel = viewModel<PatternMainViewModel>()
    val patternState = patternMainViewModel.pattern.value
    val patternItemState = patternMainViewModel.patternItemSections.value

    Column(
        modifier = modifier
            .fillMaxHeight()
            .background(Color(0XFFfee2e9))
    ) {
        PatternHeader(pattern = patternState)
        PatternItemList(patternItemSections = patternItemState)
    }
}

@Composable
fun PatternHeader(
    modifier: Modifier = Modifier,
    topColor: Color = Color(0xffffd6e0),
    bottomColor: Color = Color(0XFFfee2e9),
    pattern: Pattern
) {
    val accentColor = Color(0xffffd6e0)
    val titleColor = Color(0xfffe6689)

    val insideColor = Color(0XFFfee2e9)

    val image = painterResource(R.drawable.imagesearchable_logo)
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Max)
            .background(topColor)
            .padding(top = 20.dp)
            .background(bottomColor)
            .padding(bottom = 20.dp)

    ) {
        Column(
            modifier
                .fillMaxHeight()
                .width(20.dp)
        ) {
            Column(
                modifier = Modifier
                    .weight(80f)
                    .fillMaxWidth()
                    .background(accentColor)
            ) {
            }
            Column(
                modifier = Modifier
                    .weight(20f)
                    .fillMaxWidth()
                    .background(insideColor)
            ) {
            }
        }
        Image(
            painter = image, contentDescription = "pattern example image.",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.size(100.dp)
        )
        Column(modifier.fillMaxHeight()) {
            Column(
                modifier = Modifier
                    .weight(80f)
                    .fillMaxWidth()
                    .background(accentColor)
                    .padding(start = 20.dp)
            ) {
                Text(
                    "Bee",
                    color = Color(0XFFfe6689),
                    fontFamily = sourceSansProFont,
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp
                )
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(5.dp))
                        .background(Color(0XFFfbc8d8))
                        .padding(3.dp)
                ) {
                    Text(
                        "0 Projects",
                        fontSize = 10.sp,
                        modifier = Modifier.height(IntrinsicSize.Min),
                        color = Color(0XFFfe6689),
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            Row(
                modifier = Modifier
                    .weight(20f)
                    .fillMaxWidth()
                    .background(insideColor)
            ) {
                /* empty row*/
            }
        }
    }
}


//let's thrive this through a model (only known to the view)
@Composable
fun PatternItemList(patternItemSections: List<PatternItemSection>, modifier: Modifier = Modifier) {
    for (section in patternItemSections) {
        PatternItem(section, modifier = Modifier.padding(vertical = 20.dp))
    }
}

@Composable
fun PatternItem(patternItemSections: PatternItemSection, modifier: Modifier = Modifier) {
    val title = patternItemSections.title
    val subtitles = patternItemSections.subtitles

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color(0XFFfbc8d8))
            .padding(start = 20.dp, top = 10.dp, bottom = 10.dp)
    ) {
        Text(title.content,
            color = Color(0XFFfe6689),
            fontFamily = sourceSansProFont,
            fontWeight = FontWeight.Bold,
            fontSize = 25.sp
        )

        for ((index, subtitle) in subtitles.withIndex()) {
            val number =
                if (subtitle.range != 1) "R${index + 1} - ${subtitle.range + index}:" else {
                    if (index == 0) {
                        "R1:"
                    } else {
                        "R${subtitles[index - 1].range + index}:"

                    }
                }
            Text("$number ${subtitle.content}",

                color = Color(0XFFfe6689),
                fontFamily = sourceSansProFont,
                fontWeight = FontWeight.Normal,
                lineHeight = 1.sp


                )
        }
    }
}


@Preview
@Composable
fun PatternItemPreview() {
    val patternItemSections = listOf(
        PatternItemSection(
            title = PatternItem("EARS (make 2)"),
            subtitles = listOf(
                SubtitlePatternItem(
                    content = "6 SC in MR (6)"
                ),
                SubtitlePatternItem(
                    content = "[SC, INC]x3 (9)"
                ),
                SubtitlePatternItem(
                    content = "[2 SC, INC]x3 (12)"
                ),
                SubtitlePatternItem(
                    content = "16 SC (16)",
                    range = 4
                ),
                SubtitlePatternItem(
                    content = "[2 SC, DEC]x4 (12)"
                )
            )
        ),


        PatternItemSection(
            title = PatternItem("EARS (make 2)"),
            subtitles = listOf(
                SubtitlePatternItem(
                    content = "6 SC in MR (6)"
                ),
                SubtitlePatternItem(
                    content = "[SC, INC]x3 (9)"
                ),
                SubtitlePatternItem(
                    content = "[2 SC, INC]x3 (12)"
                ),
                SubtitlePatternItem(
                    content = "16 SC (16)",
                    range = 4
                ),
                SubtitlePatternItem(
                    content = "[2 SC, DEC]x4 (12)"
                )
            )
        ),
    )

    CrochCounterTheme {
        PatternItemList(patternItemSections)
    }
}

@Preview(showBackground = true)
@Composable
fun PatternMainScreenPreview() {
    CrochCounterTheme {
        PatternMainScreen()
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PatternHeaderPreview() {
    CrochCounterTheme {
        PatternMainScreen()
    }
}