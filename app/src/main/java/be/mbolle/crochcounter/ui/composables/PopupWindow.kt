package be.mbolle.crochcounter.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun PopupWindow(modifier: Modifier = Modifier, counter: Int) {
    Box(modifier = Modifier
        .width(300.dp)
        .height(200.dp)
        .background(color = Color(0XFFFFD6E0), shape = RoundedCornerShape(5.dp))) {

        Column(modifier = Modifier.align(alignment = Alignment.Center)) {
            Counter(value = "$counter", modifier = Modifier
                .height(200.dp))
        }
    }
}