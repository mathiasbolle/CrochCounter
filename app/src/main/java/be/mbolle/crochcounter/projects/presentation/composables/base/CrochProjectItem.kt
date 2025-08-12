package be.mbolle.crochcounter.projects.presentation.composables.base

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LocalMinimumInteractiveComponentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import be.mbolle.crochcounter.projects.model.CrochCounter

@Composable
fun CrochProjectItem(
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