package be.mbolle.crochcounter.core.presentation.util

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import be.mbolle.crochcounter.R
import be.mbolle.crochcounter.core.presentation.composables.TopCrochBar
import be.mbolle.crochcounter.ui.CrochCounterViewModel

@Composable
fun ScreenWithTopBar(
    modifier: Modifier = Modifier,
    enableActions: Boolean,
    crochCounterViewModel: CrochCounterViewModel,
    navigateToProject: () -> Unit,
    navigateToPatterns: () -> Unit,
    name: String = stringResource(R.string.project_title),

    screen: @Composable () -> Unit
) {
    Scaffold(
        modifier = modifier
            .background(color = Color(0XFFFFD6E0))
            .statusBarsPadding()
            .background(color = Color(0XFFFEE2E9))
            .systemBarsPadding()
            .fillMaxSize(),
        containerColor = Color(0XFFFEE2E9),
        topBar = {
            TopCrochBar(
                name = name,
                navigateToProjects = {
                    navigateToProject()
                },
                enableServiceValue = crochCounterViewModel.isOverlayEnabled,
                onCheckboxValueChange = { crochCounterViewModel.enableOverlayService(!crochCounterViewModel.isOverlayEnabled) },
                decreaseValue = { crochCounterViewModel.subtractCounterByOne() },
                resetValue = { crochCounterViewModel.resetCounter() },
                navigateToPatterns = { navigateToPatterns() },
                isProjectEnabled = enableActions
            )
        }
    ) { innerPadding ->
        val lifecycleOwner = LocalLifecycleOwner.current
        val lifecycleState by lifecycleOwner.lifecycle.currentStateFlow.collectAsState()

        LaunchedEffect(lifecycleState) {
            if (lifecycleState == Lifecycle.State.RESUMED) {
                //crochCounterViewModel.refreshCache()
            }
        }

        Box(modifier = modifier.padding(innerPadding)) {
            screen()
        }
    }
}

@Composable
fun ScreenWithoutTopBar(
    modifier: Modifier = Modifier,
    screen: @Composable () -> Unit
) {
    Scaffold(
        modifier = modifier
            .background(color = Color(0XFFFFD6E0))
            .statusBarsPadding()
            .background(color = Color(0XFFFEE2E9))
            .systemBarsPadding()
            .fillMaxSize(),
        containerColor = Color(0XFFFEE2E9),
    ) { innerPadding ->
        val lifecycleOwner = LocalLifecycleOwner.current
        val lifecycleState by lifecycleOwner.lifecycle.currentStateFlow.collectAsState()

        LaunchedEffect(lifecycleState) {
            if (lifecycleState == Lifecycle.State.RESUMED) {
                //crochCounterViewModel.refreshCache()
            }
        }
        Box(modifier = modifier.padding(innerPadding)) {
            screen()
        }
    }
}
