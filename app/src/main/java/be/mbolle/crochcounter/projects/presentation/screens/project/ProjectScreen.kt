package be.mbolle.crochcounter.projects.presentation.screens.project

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import be.mbolle.crochcounter.projects.presentation.composables.Counter
import be.mbolle.crochcounter.projects.presentation.composables.CrochCounterProjectDialog
import be.mbolle.crochcounter.projects.presentation.composables.base.Button
import be.mbolle.crochcounter.projects.presentation.composables.base.InputDialog
import be.mbolle.crochcounter.ui.CrochCounterViewModel

@Composable
fun ProjectScreen(
    crochCounterViewModel: CrochCounterViewModel,
    innerPadding: PaddingValues
) {
    val hasNoEmptyCounterList = !crochCounterViewModel.crochCounterState.list.isEmpty()
    val hasVisibleProject = crochCounterViewModel.crochCounterState.createProjectState.isVisible
    if (hasNoEmptyCounterList) {
        if (hasVisibleProject) {

            CrochCounterProjectDialog(
                text = crochCounterViewModel.crochCounterState.createProjectState.text ?: "",
                openAlertDialog = crochCounterViewModel.crochCounterState.createProjectState.isVisible,
                makeProjectInvisible = { crochCounterViewModel.makeCreateProjectDialogInvisible() },
                setTextOfDialog = { text -> crochCounterViewModel.setTitleProjectDialog(text) },
                createProject = { project ->
                    crochCounterViewModel.addProject(
                        project
                    )
                })

            if (isProjectAdaptionVisible(crochCounterViewModel)) {
                CrochCounterEditProjectDialog(
                    text = crochCounterViewModel.crochCounterState.editProjectState.text ?: "",
                    openAlertDialog = crochCounterViewModel.crochCounterState.editProjectState.isVisible,
                    makeProjectInvisible = { crochCounterViewModel.makeEditProjectInvisible() },
                    setTextOfDialog = { text -> crochCounterViewModel.setTitleEditDialog(text) },
                    editProjectName = { projectName ->
                        crochCounterViewModel.renameProject(
                            projectName
                        )
                    }
                )
            }
        }
        CrochContent(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            increaseValue = { crochCounterViewModel.addCounterByOne() },
            value = crochCounterViewModel.crochCounterState.counter.toString(),
            project = crochCounterViewModel.crochCounterState.name!!
        )



    }else {
        CrochCounterProjectDialog(
            text = crochCounterViewModel.crochCounterState.createProjectState.text ?: "",
            openAlertDialog = crochCounterViewModel.crochCounterState.createProjectState.isVisible,
            makeProjectInvisible = { crochCounterViewModel.makeCreateProjectDialogInvisible() },
            setTextOfDialog = { text -> crochCounterViewModel.setTitleProjectDialog(text) },
            createProject = { project ->
                crochCounterViewModel.addProject(
                    project
                )
            })
    }
}

@Composable
fun CrochCounterEditProjectDialog(
    text: String,
    openAlertDialog: Boolean,
    makeProjectInvisible: () -> Unit,
    setTextOfDialog: (String) -> Unit,
    editProjectName: (project: String) -> Unit

) {
    when {
        openAlertDialog -> {
            InputDialog(
                text = text,
                title = "Edit the name",
                inputLabel = "Name",
                openAlertDialog = true,
                makeDialogInvisible = { makeProjectInvisible() },
                setTextOfDialog = { value -> setTextOfDialog(value) },
                confirmationAction = { editProjectName(text) }
            )
        }
    }
}


@Composable
fun CrochContent(
    modifier: Modifier = Modifier,
    increaseValue: () -> Unit,
    value: String,
    project: String
) {
    Box(modifier = modifier.fillMaxSize()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Counter(value = value, project = project, modifier = Modifier.height(200.dp))
        }
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter), onClick = { increaseValue() }, label = "Add row"
        )
    }
}

fun isProjectAdaptionVisible(crochCounterViewModel: CrochCounterViewModel): Boolean {
    return crochCounterViewModel.crochCounterState.editProjectState.isVisible
}