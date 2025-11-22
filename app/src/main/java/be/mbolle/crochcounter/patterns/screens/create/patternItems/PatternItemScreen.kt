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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
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
    createPatternViewModel: CreatePatternViewModel
) {
    CreatePatternBaseScreen(
        modifier = modifier.fillMaxSize(),
        progressIndicator = 0.7f,
        actions = {
            Actions()
        }, contentAlignment = Alignment.TopCenter
    ) {
        Column {
            Text(
                "What are the parts of ${createPatternViewModel.searchName.text}",
                modifier = Modifier
                    .padding(top = 20.dp, bottom = 20.dp)
                    .semantics(properties = { contentDescription = "Project name counter" }),
                fontSize = 25.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                color = Color(0XFFFF8CA7)
            )
            PatternItemPart(createPatternViewModel.subpatterns) {
                createPatternViewModel.addNewSubPattern()
            }
        }
    }
}

@Composable
fun Actions(modifier: Modifier = Modifier) {
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
            }, onClick = {}
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
            }, onClick = {})
    }
}

@Composable
fun PatternItemHeader() {
    Column {
        Row(modifier = Modifier.fillMaxWidth()) {
            InputSearchCrochCounter(
                initialText = "Type the content",
                wordState = TextFieldState(),
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
    patternItems: SnapshotStateList<PatternItem?>,
    onClick: () -> Unit,
) {
    Column {
        PatternItemHeader()
        Column(
            modifier = Modifier.padding(start = 40.dp)
        ) {
            PatternItemList(patternItems)
            AddButtonCircular(modifier = Modifier.padding(vertical = 10.dp)) {
                onClick()
            }
        }
    }
}

@Preview
@Composable
fun PatternItemPartPreview() {
    PatternItemPart(listOf<PatternItem?>() as SnapshotStateList<PatternItem?>) {

    }
}

@Composable
fun PatternItemList(patternItemList: List<PatternItem?>) {

    LazyColumn() {
        items(patternItemList) {
            PatternItem2(it, modifier = Modifier.padding(vertical = 20.dp))
        }
    }
}

@Composable
fun PatternItem2(patternItem: PatternItem? = null, modifier: Modifier = Modifier) {

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
                wordState = TextFieldState(initialText = patternItem?.content ?: ""),
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
    val patternItem = PatternItem(content = "R1: qosjdfioqsdjfoijq", comment = "hmmm")
    PatternItem2(patternItem)
}

@Preview
@Composable
fun PatternItemSSectionPreview() {
    PatternItemHeader()
}