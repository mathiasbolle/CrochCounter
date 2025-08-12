package be.mbolle.crochcounter.projects.presentation.composables.base

import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import be.mbolle.crochcounter.projects.model.CrochCounter
import kotlin.collections.forEach


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
            modifier = Modifier.semantics(properties = {contentDescription = "projects"}),
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            projects.forEach { project ->
                DropdownMenuItem(
                    text = {
                        CrochProjectItem (
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

