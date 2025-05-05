package be.mbolle.crochcounter.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LocalMinimumInteractiveComponentSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import be.mbolle.crochcounter.R
import be.mbolle.crochcounter.model.CrochCounter
import be.mbolle.crochcounter.ui.composables.Button
import be.mbolle.crochcounter.ui.composables.Counter


@Composable
fun CrochCounterApp(modifier: Modifier = Modifier, crochCounterViewModel: CrochCounterViewModel) {
    Scaffold(
        modifier = Modifier
            .background(color = Color(0XFFFFD6E0))
            .statusBarsPadding()
            .background(color = Color(0XFFFEE2E9))
            .systemBarsPadding()
            .fillMaxSize(),
        containerColor = Color(0XFFFEE2E9),
        topBar = {
            TopCrocherBar(
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

        if (!crochCounterViewModel.crochCounterState.list.isEmpty()) {
            if (crochCounterViewModel.crochCounterState.createProjectState.isVisible) {
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

            if (crochCounterViewModel.crochCounterState.editProjectState.isVisible) {
                Log.d(
                    "CrochCounterApp",
                    crochCounterViewModel.crochCounterState.editProjectState.isVisible.toString()
                )
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

            CrosherContent(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                increaseValue = { crochCounterViewModel.addCounterByOne() },
                value = crochCounterViewModel.crochCounterState.counter.toString(),
                project = crochCounterViewModel.crochCounterState.name!!
            )
        } else {
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
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopCrocherBar(
    modifier: Modifier = Modifier,
    currentProject: String,
    projects: List<CrochCounter>,
    enableServiceValue: Boolean,
    switchProject: (oldProject: String, newProject: String) -> Unit,
    onCheckboxValueChange: (Boolean) -> Unit,
    makeProjectVisible: () -> Unit,
    decreaseValue: () -> Unit,
    resetValue: () -> Unit,
    editProjectName: () -> Unit,
    deleteCurrentProject: () -> Unit
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0XFFFFD6E0),
            titleContentColor = Color(0XFFFF8CA7),
        ),
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                ProjectDropdownMenu(
                    projects = projects,
                    makeProjectVisible = { makeProjectVisible() },
                    activeProject = currentProject,
                    editProjectName = { editProjectName() },
                    deleteCurrentProject = { deleteCurrentProject() },
                    switchProject = { oldProject, newProject ->
                        switchProject(
                            oldProject,
                            newProject
                        )
                    })
                Text(stringResource(R.string.project_title))
            }
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

            Checkbox(
                checked = enableServiceValue,
                onCheckedChange = { isChecked -> onCheckboxValueChange(isChecked) },
                colors = CheckboxDefaults.colors(
                    checkedColor = Color(0XFFFF8CA7),
                    uncheckedColor = Color(0XFFFF8CA7)
                )
            )
        }
    )
}

@Composable
fun CrochCounterProjectDialog(
    text: String,
    openAlertDialog: Boolean,
    makeProjectInvisible: () -> Unit,
    setTextOfDialog: (String) -> Unit,
    modifier: Modifier = Modifier,
    createProject: (project: String) -> Unit
) {

    Log.d("CrochCounterApp", openAlertDialog.toString())

    when {
        openAlertDialog -> {
            InputDialog(
                text = text,
                title = "Create a new project",
                inputLabel = "Name",
                openAlertDialog = openAlertDialog,
                makeDialogInvisible = { makeProjectInvisible() },
                setTextOfDialog = { value -> setTextOfDialog(value) },
                confirmationAction = { createProject(text) }
            )
        }
    }
}

@Composable
fun CrochCounterEditProjectDialog(
    text: String,
    openAlertDialog: Boolean,
    makeProjectInvisible: () -> Unit,
    setTextOfDialog: (String) -> Unit,
    modifier: Modifier = Modifier,
    editProjectName: (project: String) -> Unit

) {
    when {
        openAlertDialog -> {
            InputDialog(
                text = text,
                title = "Edit the name",
                inputLabel = "Name",
                openAlertDialog = openAlertDialog,
                makeDialogInvisible = { makeProjectInvisible() },
                setTextOfDialog = { value -> setTextOfDialog(value) },
                confirmationAction = { editProjectName(text) }
            )
        }
    }
}


@Composable
fun CrosherContent(
    modifier: Modifier = Modifier,
    increaseValue: () -> Unit,
    value: String,
    project: String
) {
    Box(modifier = Modifier.fillMaxSize()) {
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

@Composable
fun ProjectDropdownMenu(
    modifier: Modifier = Modifier,
    activeProject: String,
    editProjectName: () -> Unit,
    deleteCurrentProject: () -> Unit,
    switchProject: (oldProject: String, newProject: String) -> Unit,
    projects: List<CrochCounter>,
    makeProjectVisible: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = modifier) {
        IconButton(
            onClick = { expanded = !expanded },
            colors = IconButtonDefaults.iconButtonColors(contentColor = Color(0XFFFF8CA7))
        ) {
            Icon(
                Icons.Default.KeyboardArrowDown,
                contentDescription = "show projects"
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
                projects.forEach { project ->
                    DropdownMenuItem(
                        text = {
                            CrochProject(
                                project = project,
                                deleteCurrentProject = { deleteCurrentProject() },
                                editProjectName = { editProjectName() }
                            )
                        },
                        onClick = { switchProject(activeProject, project.name!!) }
                    )
                }
                DropdownMenuItem(
                    text = { Text("New Project") },
                    onClick = {
                        makeProjectVisible()
                        expanded = false
                    }
                )


        }
    }
}

@Composable
fun InputDialog(
    title: String,
    inputLabel: String,
    text: String,
    openAlertDialog: Boolean,
    makeDialogInvisible: () -> Unit,
    setTextOfDialog: (String) -> Unit,
    modifier: Modifier = Modifier,
    confirmationAction: () -> Unit
) {

    Log.d("CrochCounterApp", openAlertDialog.toString())

    when {
        openAlertDialog -> {
            AlertDialog(
                icon = {
                    Icon(Icons.Default.Info, contentDescription = "Example Icon")
                },
                title = {
                    Text(text = title)
                },
                text = {

                    TextField(
                        value = text,
                        onValueChange = { setTextOfDialog(it) },
                        label = { Text(inputLabel) }
                    )
                },
                onDismissRequest = {
                    makeDialogInvisible()
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            confirmationAction()
                            makeDialogInvisible()
                        }
                    ) {
                        Text("Confirm")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            makeDialogInvisible()
                        }
                    ) {
                        Text("Dismiss")
                    }
                }
            )
        }
    }
}

@Composable
fun CrochProject(
    modifier: Modifier = Modifier, project: CrochCounter,
    editProjectName: () -> Unit, // enable a state variable in the VM that shows a dialog.
    deleteCurrentProject: () -> Unit,
) {
    Row(modifier = modifier, horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
        Column(modifier = Modifier.fillMaxHeight(), verticalArrangement = Arrangement.Center) {
            Text(project.name!!)
        }


        Spacer(modifier = Modifier.sizeIn(minWidth = 30.dp))

        Row {
            CompositionLocalProvider(LocalMinimumInteractiveComponentSize provides Dp.Unspecified) {

                IconButton(
                    onClick = { editProjectName() },
                    colors = IconButtonDefaults.iconButtonColors(contentColor = Color(0XFFFF8CA7))
                ) {
                    Icon(Icons.Default.Edit, contentDescription = "edit")
                }

                IconButton(
                    modifier = Modifier.defaultMinSize(minWidth = 1.dp, minHeight = 1.dp),
                    onClick = { deleteCurrentProject() },
                    colors = IconButtonDefaults.iconButtonColors(contentColor = Color(0XFFFF8CA7))
                ) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete")
                }

            }

        }
    }
}