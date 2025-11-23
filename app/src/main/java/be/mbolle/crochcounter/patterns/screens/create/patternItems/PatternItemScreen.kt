package be.mbolle.crochcounter.patterns.screens.create.patternItems

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import be.mbolle.crochcounter.R
import be.mbolle.crochcounter.core.presentation.composables.InputSearchCrochCounter
import be.mbolle.crochcounter.patterns.screens.create.CreatePatternBaseScreen
import be.mbolle.crochcounter.patterns.screens.create.CreatePatternViewModel
import be.mbolle.crochcounter.patterns.screens.create.patternItems.model.PatternItem
import be.mbolle.crochcounter.projects.presentation.composables.base.AddButtonCircular
import be.mbolle.crochcounter.projects.presentation.composables.base.Button

@Composable
fun PatternItemScreen(
    modifier: Modifier = Modifier,
    createPatternViewModel: CreatePatternViewModel,
    navigateToCurrentScreen: () -> Unit,
) {
    CreatePatternBaseScreen(
        modifier = modifier.fillMaxSize(),
        progressIndicator = 0.7f,
        actions = {
            Actions(
                onClickNextSection = {
                    createPatternViewModel.addEmptyPatternItem()
                    navigateToCurrentScreen()
                },
                onConfirm = {
                    createPatternViewModel.confirm()
                }
            )
        }, contentAlignment = Alignment.TopCenter
    ) {
        Column {
            Text(
                "What are the parts of ${createPatternViewModel.patternName.text}",
                modifier = Modifier
                    .padding(top = 20.dp, bottom = 20.dp)
                    .semantics(properties = { contentDescription = "Project name counter" }),
                fontSize = 25.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                color = Color(0XFFFF8CA7)
            )
            PatternItemPart(
                createPatternViewModel.subpatterns?.subTitle ?: TextFieldState(),
                createPatternViewModel.subpatterns?.patternItemList ?: emptyList(),
                { index, content ->
                    createPatternViewModel.addSubPatternContent(index, content)
                }
            ) {
                createPatternViewModel.addEmptySubPattern()
            }
        }
    }
}

@Composable
fun Actions(modifier: Modifier = Modifier, onClickNextSection: () -> Unit, onConfirm: () -> Unit) {
    Row(modifier = modifier) {
        Button(
            fontSize = 20.sp,
            modifier = Modifier.weight(0.5f),
            label = "Next section",
            icon = {
                Icon(
                    painter = painterResource(R.drawable.next_section),
                    contentDescription = "Next section",
                    modifier = Modifier
                        .weight(0.2f)
                        .fillMaxHeight()
                        .requiredWidth(40.dp),
                )
            }, onClick = {
                onClickNextSection()
            }
        )
        Button(
            fontSize = 20.sp,
            modifier = Modifier.weight(0.5f),
            label = "Overview",
            icon = {
                Icon(
                    painter = painterResource(R.drawable.overview),
                    contentDescription = "Next section",
                    modifier = Modifier
                        .weight(0.2f)
                        .fillMaxHeight()
                        .requiredWidth(40.dp),
                )
            }, onClick = {
                onConfirm()
            })
    }
}

@Composable
fun PatternItemHeader(subtitle: TextFieldState) {
    Column {
        Row(modifier = Modifier.fillMaxWidth()) {
            InputSearchCrochCounter(
                initialText = "Type the content",
                wordState = subtitle,
                modifier = Modifier
                    .alignByBaseline()
                    .weight(90f)
            )
            val commentPainter = painterResource(R.drawable.comment)
            Image(
                painter = commentPainter,
                contentDescription = "a nice comment",
                modifier = Modifier
                    .weight(10f)
                    .size(24.dp)
                    .clickable {
                    }
            )
        }
    }
}

@Composable
fun PatternItemPart(
    subTitle: TextFieldState,
    patternItems: List<PatternItem?>,
    addSubpatternContent: (index: Int, content: String) -> Unit,
    onClick: () -> Unit,
) {
    Column {
        PatternItemHeader(
            subtitle = subTitle
        )
        Column(
            modifier = Modifier.padding(start = 40.dp)
        ) {
            PatternItemList(patternItems) { index, content ->
                addSubpatternContent(index, content)
            }
            AddButtonCircular(modifier = Modifier.padding(vertical = 10.dp)) {
                onClick()
            }
        }
    }
}

@Preview
@Composable
fun PatternItemPartPreview() {
    PatternItemPart(
        subTitle = TextFieldState(),
        listOf<PatternItem?>() as SnapshotStateList<PatternItem?>,
        onClick = {},
        addSubpatternContent = { index, content ->
        })
}

@Composable
fun PatternItemList(
    patternItemList: List<PatternItem?>,
    addSubpatternContent: (index: Int, content: String) -> Unit,
) {
    LazyColumn() {
        itemsIndexed(patternItemList) { index, key ->
            PatternItem2(
                key,
                modifier = Modifier.padding(vertical = 20.dp),
                addSubpatternContent = { _, content ->
                    addSubpatternContent(index, content)
                })
        }
    }
}

@Composable
fun PatternItem2(
    patternItem: PatternItem? = null,
    addSubpatternContent: (index: Int, content: String) -> Unit,
    modifier: Modifier = Modifier
) {


    Column(
        modifier = modifier
            .border(
                1.dp, Color(0XFFfe6689),
                shape = RoundedCornerShape(20)
            )
            .padding(5.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Text("1", modifier = Modifier.alignByBaseline())
            InputSearchCrochCounter(
                initialText = "Type the content",
                wordState = patternItem?.content ?: TextFieldState(),
                modifier = Modifier
                    .alignByBaseline()
                    .weight(1f)
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            val commentPainter = painterResource(R.drawable.comment)
            Image(
                painter = commentPainter,
                contentDescription = "a nice comment",
                modifier = Modifier
                    .weight(1f)
                    .size(24.dp)
                    .clickable {

                    }
            )
            InputSearchCrochCounter(
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                initialText = "Range",
                wordState = TextFieldState(initialText = patternItem?.comment ?: ""),
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Preview
@Composable
fun PatternItem2Preview() {
    val patternItem = PatternItem(content = TextFieldState("R1: qosjdfioqsdjfoijq"), comment = "hmmm")
    PatternItem2(
        patternItem,
        addSubpatternContent = { index, content -> {

        }  }
    )
}

@Preview
@Composable
fun PatternItemSSectionPreview() {
    PatternItemHeader(TextFieldState())
}