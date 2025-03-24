package be.mbolle.crochcounter.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import be.mbolle.crochcounter.R

@Composable
fun Button(
    modifier: Modifier = Modifier,
    label: String,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier.padding(25.dp)
            .background(color = Color(0XFFFE6689), shape = RoundedCornerShape(5.dp))
            .clickable { onClick() }
            .padding(10.dp)
            ) {

        Icon(
            imageVector = Icons.Sharp.Add,
            contentDescription = stringResource(R.string.add_btn),
            modifier = Modifier.weight(0.2f),
            tint = Color.White
        )

        Text(text = label, modifier = Modifier.weight(0.8F), textAlign = TextAlign.Center, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 30.sp
        )
    }
}

