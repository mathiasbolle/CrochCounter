package be.mbolle.crochcounter.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import be.mbolle.crochcounter.projects.presentation.composables.TopCrochBar
import be.mbolle.crochcounter.projects.presentation.screens.project.ProjectScreen


@Composable
fun CrochCounterApp(modifier: Modifier = Modifier, crochCounterViewModel: CrochCounterViewModel) {
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

        ProjectScreen(
            crochCounterViewModel = crochCounterViewModel,
            innerPadding = innerPadding
        )
    }
}
