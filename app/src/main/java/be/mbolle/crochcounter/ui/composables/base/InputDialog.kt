package be.mbolle.crochcounter.ui.composables.base

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

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
