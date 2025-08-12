package be.mbolle.crochcounter.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import be.mbolle.crochcounter.R

@Composable
fun Counter(modifier: Modifier = Modifier, value: String, project: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Box(modifier.semantics(properties = {contentDescription = "counter"})) {

            Image(
                painter = painterResource(R.drawable.yarn),
                contentDescription = "background yarn",
                contentScale = ContentScale.Fit,
                alpha = 0.2f,
                modifier = Modifier.aspectRatio(1f),
            )
            Text(
                text = value,
                fontSize = 70.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0XFFFE6689),
                modifier = Modifier.align(alignment = Alignment.Center)
            )
        }

        Text(

            modifier = Modifier.padding(top = 20.dp).semantics(properties = {contentDescription = "Project name counter"}),
            text = project,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0XFFFF8CA7)
        )
    }
}

@Composable
@Preview
fun CounterPreview(counter: String = "1", project: String = "something") {
    Counter(value = counter, project = project)
}