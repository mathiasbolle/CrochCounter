package be.mbolle.crochcounter.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import be.mbolle.crochcounter.R
import be.mbolle.crochcounter.ui.composables.Button
import be.mbolle.crochcounter.ui.composables.Counter
import be.mbolle.crochcounter.ui.theme.CrochCounterViewModel


@Composable
fun CrochCounterApp(modifier: Modifier = Modifier, crochCounterViewModel: CrochCounterViewModel) {
    Scaffold(
        modifier = Modifier.background(color = Color(0XFFFFD6E0)).
        statusBarsPadding().
        background(color = Color(0XFFFEE2E9)).systemBarsPadding()
            .fillMaxSize(),
        containerColor = Color(0XFFFEE2E9),
        topBar = {
            TopCrocherBar(
                decreaseValue = { crochCounterViewModel.subtractCounterByOne() },
                resetValue = { crochCounterViewModel.resetCounter() }
            )
        }
    ) { innerPadding ->

        val lifecycleOwner = LocalLifecycleOwner.current
        val lifecycleState by lifecycleOwner.lifecycle.currentStateFlow.collectAsState()

        LaunchedEffect(lifecycleState) {
            if (lifecycleState == Lifecycle.State.RESUMED) {
                crochCounterViewModel.initValue()
            }
        }

        CrosherContent(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            increaseValue = { crochCounterViewModel.addCounterByOne() },
            value = crochCounterViewModel.counter.toString()
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopCrocherBar(
    modifier: Modifier = Modifier,
    decreaseValue: () -> Unit,
    resetValue: () -> Unit
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0XFFFFD6E0),
            titleContentColor = Color(0XFFFF8CA7),
        ),
        title = {
            Text(stringResource(R.string.app_name))
        },

        actions = {
            IconButton(
                onClick = { decreaseValue() },
                colors = IconButtonDefaults.iconButtonColors(contentColor = Color(0XFFFF8CA7))
            ) {
                Icon(
                    painter = painterResource(R.drawable.remove_icon),
                    contentDescription = stringResource(R.string.subtract_btn)
                )
            }
            IconButton(
                onClick = { resetValue() },
                colors = IconButtonDefaults.iconButtonColors(contentColor = Color(0XFFFF8CA7))
            ) {
                Icon(
                    painter = painterResource(R.drawable.restart_icon),
                    contentDescription = stringResource(R.string.reset_btn)
                )
            }
        }
    )
}

@Composable
fun CrosherContent(modifier: Modifier = Modifier, increaseValue: () -> Unit, value: String) {
    Box(modifier = Modifier.fillMaxSize()) {
        Counter(value = value, modifier = Modifier
            .height(200.dp)
            .align(Alignment.Center))
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter), onClick = { increaseValue() }, label = "Add row"
        )
    }
}