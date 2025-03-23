package be.mbolle.crochcounter.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.sharp.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import be.mbolle.crochcounter.R
import be.mbolle.crochcounter.ui.composables.Button
import be.mbolle.crochcounter.ui.composables.Counter

@Preview
@Composable
fun CrochCounterApp(modifier: Modifier = Modifier) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color(0XFFFEE2E9),
        topBar = {
            TopCrocherBar()
        }
    ) { innerPadding ->
        CrosherContent(modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize())
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopCrocherBar(modifier: Modifier = Modifier) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0XFFFFD6E0),
            titleContentColor = Color(0XFFFF8CA7),
        ),
        title = {
            Text(stringResource(R.string.app_name))
        },

        actions = {
            IconButton(onClick = { /* do something! */ }, colors = IconButtonDefaults.iconButtonColors(contentColor = Color(0XFFFF8CA7))) {
                Icon(
                    imageVector = Icons.Sharp.Add,
                    contentDescription = stringResource(R.string.subtract_btn)
                )
            }
            IconButton(onClick = { /* do something! */ },  colors = IconButtonDefaults.iconButtonColors(contentColor = Color(0XFFFF8CA7))) {
                Icon(
                    imageVector = Icons.Filled.Notifications,
                    contentDescription = stringResource(R.string.reset_btn)
                )
            }
        }
    )
}

@Composable
fun CrosherContent(modifier: Modifier = Modifier) {
    Box(modifier = Modifier.fillMaxSize()) {
        Counter(value = "1", modifier = Modifier.height(200.dp).align(Alignment.Center))
        Button(modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.BottomCenter)
            , onClick = {}, label = "Add row")
    }
}