package be.mbolle.crochcounter.projects.presentation.composables.base

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Add
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import be.mbolle.crochcounter.R
import be.mbolle.crochcounter.ui.theme.CrochCounterTheme

@Composable
fun AddButtonCircular(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    OutlinedButton(
        modifier = modifier
            .size(40.dp),
        onClick = onClick,
        shape = CircleShape,
        contentPadding = PaddingValues(0.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0XFFffd6e0))
    ) {
        Icon(
            imageVector = Icons.Sharp.Add,
            contentDescription = stringResource(R.string.add_btn),
                    tint = Color (0XFFffa2b8),
        )
    }
}

@Composable
fun Addbutton(
    modifier: Modifier = Modifier,
    label: String,
    onClick: () -> Unit,
) {
    Button(
        modifier, label, onClick,
    ) {
        Icon(
            imageVector = Icons.Sharp.Add,
            contentDescription = stringResource(R.string.add_btn),
            modifier = Modifier
                .weight(0.2f)
                .fillMaxHeight()
                .requiredWidth(40.dp),
            tint = Color.White
        )
    }
}

@Composable
fun Button(
    modifier: Modifier = Modifier,
    label: String,
    onClick: () -> Unit,
    fontSize: TextUnit = 30.sp,
    icon: @Composable RowScope.() -> Unit
) {
    Row(
        modifier = modifier
            .padding(25.dp)
            .clip(shape = RoundedCornerShape(5.dp))
            .background(color = Color(0XFFFE6689))
            .height(IntrinsicSize.Min)
            .clickable { onClick() }
            .padding(10.dp)
    ) {
        icon()

        Text(
            text = label, modifier = Modifier.weight(0.8F), textAlign = TextAlign.Center,
            color = Color.White, fontWeight = FontWeight.Bold, fontSize = fontSize
        )
    }
}



@Preview
@Composable
fun AddRowButtonPreview() {
    val buttonText = "Add row"
    CrochCounterTheme {
        Addbutton(label = buttonText) {
            /* no implementation */
        }
    }
}