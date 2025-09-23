package be.mbolle.crochcounter.projects.presentation.screens.project

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import be.mbolle.crochcounter.R
import be.mbolle.crochcounter.projects.model.Pattern
import be.mbolle.crochcounter.projects.model.PatternItem
import be.mbolle.crochcounter.projects.model.SubPattern
import be.mbolle.crochcounter.projects.presentation.composables.Counter
import be.mbolle.crochcounter.projects.presentation.composables.PatternTimeline
import be.mbolle.crochcounter.projects.presentation.composables.base.Button
import be.mbolle.crochcounter.ui.CrochCounterProjectState
import be.mbolle.crochcounter.ui.CrochCounterViewModel
import be.mbolle.crochcounter.ui.theme.CrochCounterTheme

@Composable
fun ProjectScreen(
    crochCounterViewModel: CrochCounterViewModel,
    innerPadding: PaddingValues = PaddingValues()
) {
    val state = crochCounterViewModel.crochCounterState
    val previewPattern = Pattern( // this is basically mocked!!
        1,
        title = "Bee",
        subPatterns = listOf(
            SubPattern(
                "EARS",
                lines = listOf(
                    PatternItem(
                        description = "R1: 6 SC in a MR (6)",
                        comment = "cR5- 8:(4 Rounds) 16 SC (16)jdfoiqjsdfiojqdsiodfjo ijqsdoifjqsdoif joqisjfoi jsqdoifj qoissjdfsdiqjfoiqsdjfoiqjdsojool"
                    ),
                    PatternItem(
                        description = "R2: [SC, INC]x3 (9)"
                    ),
                    PatternItem(
                        description = "R3: [2 SC, INC]x3 (12)"
                    ),
                    PatternItem(
                        description = "R5- 8:(4 Rounds) 16 SC (16) ijqsdofi jqsojf oqsdifj oisjdof joqsdj foij"
                    ),
                    PatternItem(
                        description = "R5- 8:(4 Rounds) 16 SC (16)jdfoiqjsdfiojqdsiodfjo ijqsdoifjqsdoif joqisjfoi jsqdoifj qoisj"
                    ),
                )
            )
        )
    )

    when (state) {
        is CrochCounterProjectState.Succes -> {
            CrochContent(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                increaseValue = { crochCounterViewModel.addCounterByOne() },
                counter = state.counter,
                projectName = state.projectTitle,
                pattern = previewPattern
            )
        }

        is CrochCounterProjectState.Error -> {
            CrochError(
                errorMessage = state.errorMessage
            ) {

            }
        }

        is CrochCounterProjectState.Loading -> {
            CrochLoading(isLoading = true)
        }
    }

//    val hasNoEmptyCounterList = !crochCounterViewModel.crochCounterState.list.isEmpty()
//    val hasVisibleProject = crochCounterViewModel.crochCounterState.createProjectState.isVisible
//    if (hasNoEmptyCounterList) {
//        if (hasVisibleProject) {
//            CrochCounterProjectDialog(
//                text = crochCounterViewModel.crochCounterState.createProjectState.text ?: "",
//                openAlertDialog = crochCounterViewModel.crochCounterState.createProjectState.isVisible,
//                makeProjectInvisible = { crochCounterViewModel.makeCreateProjectDialogInvisible() },
//                setTextOfDialog = { text -> crochCounterViewModel.setTitleProjectDialog(text) },
//                createProject = { project ->
//                    crochCounterViewModel.addProject(
//                        project
//                    )
//                })
//
//            if (isProjectAdaptionVisible(crochCounterViewModel)) {
//                CrochCounterEditProjectDialog(
//                    text = crochCounterViewModel.crochCounterState.editProjectState.text ?: "",
//                    openAlertDialog = crochCounterViewModel.crochCounterState.editProjectState.isVisible,
//                    makeProjectInvisible = { crochCounterViewModel.makeEditProjectInvisible() },
//                    setTextOfDialog = { text -> crochCounterViewModel.setTitleEditDialog(text) },
//                    editProjectName = { projectName ->
//                        crochCounterViewModel.renameProject(
//                            projectName
//                        )
//                    }
//                )
//            }
//        }


//    } else {
//        CrochCounterProjectDialog(
//            text = crochCounterViewModel.crochCounterState.createProjectState.text ?: "",
//            openAlertDialog = crochCounterViewModel.crochCounterState.createProjectState.isVisible,
//            makeProjectInvisible = { crochCounterViewModel.makeCreateProjectDialogInvisible() },
//            setTextOfDialog = { text -> crochCounterViewModel.setTitleProjectDialog(text) },
//            createProject = { project ->
//                crochCounterViewModel.addProject(
//                    project
//                )
//            })
//    }
}


@Composable
fun CrochContent(
    modifier: Modifier = Modifier,
    increaseValue: () -> Unit,
    counter: String,
    pattern: Pattern,
    projectName: String
) {
    Column(modifier = modifier.fillMaxSize()) {
        Counter(
            value = counter, project = projectName, modifier = Modifier
                .padding(horizontal = 70.dp)
                .padding(20.dp)
                .weight(0.50f)
        )

        Column(
            modifier = Modifier
                .weight(0.60f)
                .padding(20.dp),
            verticalArrangement = Arrangement.Bottom
        ) {
            PatternTimeline(
                modifier = Modifier.requiredHeight(280.dp),
                patterns = pattern
            )
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    increaseValue()
                          },
                label = stringResource(R.string.add_row_btn)
            )
        }
    }
}

@Composable
fun CrochError(
    modifier: Modifier = Modifier,
    errorMessage: String,
    onRetry: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(errorMessage)
        /*
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { onRetry() },
            label = "Retry"
        )
         */
    }
}

@Composable
fun CrochLoading(modifier: Modifier = Modifier, isLoading: Boolean) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (!isLoading) return
        CircularProgressIndicator(
            modifier = Modifier.width(64.dp),
            color = MaterialTheme.colorScheme.secondary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant,
        )
    }

}


@Preview(backgroundColor = 0XFFFEE2E9, showBackground = true)
@Composable
fun CrochContentWithoutTopBarPreview() {
    val customPattern = Pattern(
        1,
        title = "Bee",
        subPatterns = listOf(
            SubPattern(
                "EARS",
                lines = listOf(
                    PatternItem(
                        description = "R1: 6 SC in a MR (6)",
                        comment = "cool"
                    ),
                    PatternItem(
                        description = "R2: [SC, INC]x3 (9)"
                    ),
                    PatternItem(
                        description = "R3: [2 SC, INC]x3 (12)"
                    ),
                    PatternItem(
                        description = "R5- 8:(4 Rounds) 16 SC (16)"
                    )
                )
            )
        )
    )
    CrochCounterTheme {
        CrochContent(
            increaseValue = {},
            counter = "5",
            projectName = "Cool project yk",
            pattern = customPattern
        )
    }
}


@Preview(backgroundColor = 0XFFFEE2E9, showBackground = true)
@Composable
fun CrochErrorWithoutTopBarPreview() {
    CrochCounterTheme {
        CrochError(errorMessage = "this is the error message!") { }
    }
}

@Preview(backgroundColor = 0XFFFEE2E9, showBackground = true)
@Composable
fun CrochLoadingWithoutTopBarPreview() {
    CrochCounterTheme {
        CrochLoading(isLoading = true)
    }
}
//fun isProjectAdaptionVisible(crochCounterViewModel: CrochCounterViewModel): Boolean {
//    return crochCounterViewModel.crochCounterState.editProjectState.isVisible
//}