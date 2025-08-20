package be.mbolle.crochcounter.projects.presentation.composables

import androidx.compose.runtime.Composable
import be.mbolle.crochcounter.projects.presentation.composables.base.InputDialog

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
