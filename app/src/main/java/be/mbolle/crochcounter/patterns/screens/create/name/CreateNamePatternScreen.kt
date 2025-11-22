package be.mbolle.crochcounter.patterns.screens.create.name

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import be.mbolle.crochcounter.core.presentation.composables.InputSearchCrochCounter
import be.mbolle.crochcounter.patterns.screens.create.CreatePatternBaseScreen
import be.mbolle.crochcounter.patterns.screens.create.CreatePatternViewModel

@Composable
fun CreateNameOfPattern(
    modifier: Modifier = Modifier,
    createPatternViewModel: CreatePatternViewModel,
    onClick: () -> Unit
) {
    CreatePatternBaseScreen(
        modifier = modifier.fillMaxSize(),
        progressIndicator = 0.2f,
        actions = {
            NextButton {
                /* go to next screen */
                onClick()
            }
        }, contentAlignment = Alignment.Center
    ) {
        Content(textFieldState = createPatternViewModel.searchName)

    }
}

@Composable
private fun Content(modifier: Modifier = Modifier, textFieldState: TextFieldState) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "What is the name your new creation?✨",
            modifier = Modifier
                .padding(top = 20.dp)
                .semantics(properties = { contentDescription = "Project name counter" }),
            fontSize = 25.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            color = Color(0XFFFF8CA7)
        )
        Spacer(modifier.height(50.dp))
        InputSearchCrochCounter(
            initialText = "Type the name",
            wordState = textFieldState,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun NextButton(onClick: () -> Unit) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
        IconButton(onClick = {
            onClick()
        }) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = ""
            )
        }
    }
}


@SuppressLint("ViewModelConstructorInComposable")
@Preview
@Composable
fun CreateNameOfPatternPreview() {

}