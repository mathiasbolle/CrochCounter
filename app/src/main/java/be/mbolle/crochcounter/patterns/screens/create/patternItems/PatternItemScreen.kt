package be.mbolle.crochcounter.patterns.screens.create.patternItems

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsEndWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
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
fun PatternItemScreen(modifier: Modifier = Modifier, createPatternViewModel: CreatePatternViewModel) {
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
            Column(horizontalAlignment = Alignment.Start) {
                PatternItemPreview()
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
fun PatternItem(patternItem: PatternItem) {
}

@Composable
fun PatternItemList(patternItems: List<PatternItem>) {
}

@Composable
@Preview
fun PatternItemPreview() {
    Column(
        modifier = Modifier
            .width(IntrinsicSize.Max)
            .defaultMinSize(minWidth = 300.dp)
    ) {
        Row(modifier = Modifier.height(IntrinsicSize.Max)) {
            InputSearchCrochCounter(
                initialText = "Type the section",
                wordState = TextFieldState(),
                modifier = Modifier.weight(1f).fillMaxWidth()
            )
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    painterResource(R.drawable.comment),
                    contentDescription = "comment",
                    modifier = Modifier.size(24.dp)
                )
            }
        }
        Column(horizontalAlignment = Alignment.End, modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.width(IntrinsicSize.Min)) {

                Column(
                    modifier = Modifier
                        .border(
                            BorderStroke(1.dp, Color(0XFFffa2b8)),
                            shape = RoundedCornerShape(15.dp)
                        )
                        .padding(5.dp)
                        .width(IntrinsicSize.Max)
                        .defaultMinSize(minWidth = 200.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Row(modifier = Modifier.height(IntrinsicSize.Min)) {
                        Column(
                            modifier = Modifier.fillMaxHeight(),
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                "1",
                                color = Color(0XFFfe6689),
                                fontWeight = FontWeight.Bold,
                                fontSize = 17.sp
                            )

                        }
                        InputSearchCrochCounter(
                            initialText = "Type the content",
                            wordState = TextFieldState(),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    Row(modifier = Modifier.height(IntrinsicSize.Min)) {
                        Column(
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxHeight()
                        ) {
                            Icon(
                                painterResource(R.drawable.comment),
                                contentDescription = "comment",
                                Modifier
                                    .alpha(0.2f)
                                    .size(24.dp)
                            )
                        }
                        InputSearchCrochCounter(
                            initialText = "Range",
                            wordState = TextFieldState(),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
                AddButtonCircular(modifier = Modifier.padding(vertical = 10.dp)) {
                    /*add a new subsection*/
                }
            }
        }
    }
}