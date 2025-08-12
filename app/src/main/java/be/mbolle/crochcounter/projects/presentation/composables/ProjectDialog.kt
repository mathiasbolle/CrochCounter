package be.mbolle.crochcounter.projects.presentation.composables

import android.util.Log
import androidx.compose.runtime.Composable
import be.mbolle.crochcounter.projects.presentation.composables.base.InputDialog

@Composable
fun CrochCounterProjectDialog(
    text: String,
    openAlertDialog: Boolean,
    makeProjectInvisible: () -> Unit,
    setTextOfDialog: (String) -> Unit,
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
