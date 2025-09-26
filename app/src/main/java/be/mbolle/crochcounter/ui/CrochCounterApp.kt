package be.mbolle.crochcounter.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import be.mbolle.crochcounter.core.presentation.navigation.CrochCounterRoot


@Composable
fun CrochCounterApp(
    modifier: Modifier = Modifier,
) {
    CrochCounterRoot(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color(0XFFFFD6E0))
    )
    /*
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
                currentProject = crochCounterViewModel.crochCounterState.name ?: "",
                projects = crochCounterViewModel.crochCounterState.list,
                enableServiceValue = crochCounterViewModel.isOverlayEnabled,
                switchProject = { oldProject, newProject ->
                    crochCounterViewModel.switchProject(
                        oldProject,
                        newProject
                    )
                },
                onCheckboxValueChange = { crochCounterViewModel.enableOverlayService(!crochCounterViewModel.isOverlayEnabled) },
                makeProjectVisible = { crochCounterViewModel.makeCreateProjectDialogVisible() },
                decreaseValue = { crochCounterViewModel.subtractCounterByOne() },
                resetValue = { crochCounterViewModel.resetCounter() },
                editProjectName = { crochCounterViewModel.makeEditProjectDialogVisible() },
                deleteCurrentProject = { crochCounterViewModel.removeProject() }
            )
        }
    ) { innerPadding ->
        val lifecycleOwner = LocalLifecycleOwner.current
        val lifecycleState by lifecycleOwner.lifecycle.currentStateFlow.collectAsState()

        LaunchedEffect(lifecycleState) {
            if (lifecycleState == Lifecycle.State.RESUMED) {
                crochCounterViewModel.refreshCache()
            }
        }

        screen()

        ProjectScreen(
            crochCounterViewModel = crochCounterViewModel,
            innerPadding = innerPadding
        )
    }
     */
}

