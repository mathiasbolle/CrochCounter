package be.mbolle.crochcounter.core.presentation.composables

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun CreateFabButton(
    modifier: Modifier = Modifier,
    contentDescription: String? = "Add item",
    onClick: () -> Unit,
) {
    FloatingActionButton(
        modifier = modifier,
        onClick = {
            onClick()
        },
        containerColor = Color(0Xffff80ab),
        contentColor = Color.White
    ) {
        Icon(Icons.Filled.Add, contentDescription)
    }
}

@Preview
@Composable
fun PreviewCreateFabButton() {
    CreateFabButton(
        onClick = {

        },
    )
}