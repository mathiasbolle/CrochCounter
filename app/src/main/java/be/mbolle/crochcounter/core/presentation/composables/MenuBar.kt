package be.mbolle.crochcounter.core.presentation.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import be.mbolle.crochcounter.R
import be.mbolle.crochcounter.projects.presentation.composables.base.ProjectDropdownMenu

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopCrochBar(
    isProjectEnabled: Boolean = true,
    enableServiceValue: Boolean,
    onCheckboxValueChange: (Boolean) -> Unit,
    decreaseValue: () -> Unit,
    resetValue: () -> Unit,
    navigateToProjects: () -> Unit,
    navigateToPatterns: () -> Unit,
    name: String = stringResource(R.string.project_title)
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0XFFFFD6E0),
            titleContentColor = Color(0XFFFF8CA7),
        ),
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (!isProjectEnabled) {
                    Text(name)
                    return@TopAppBar
                }

                ProjectDropdownMenu(
//                    projects = projects,
//                    makeProjectVisible = { makeProjectVisible() },
//                    activeProject = currentProject,
//                    editProjectName = { editProjectName() },
//                    deleteCurrentProject = { deleteCurrentProject() },
//                    switchProject = { oldProject, newProject ->
//                        switchProject(
//                            oldProject,
//                            newProject
//                        )
//                    }
                    navigateToProjects = { navigateToProjects() }
                )
                Text(name)
            }
        },

        actions = {
            if (!isProjectEnabled) return@TopAppBar

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

            IconButton(
                onClick = { navigateToPatterns() },
                colors = IconButtonDefaults.iconButtonColors(contentColor = Color(0XFFFF8CA7))
            ) {
                Icon(
                    painter = painterResource(R.drawable.grid),
                    contentDescription = "Go to crocher templates."
                )
            }
        }
    )
}

