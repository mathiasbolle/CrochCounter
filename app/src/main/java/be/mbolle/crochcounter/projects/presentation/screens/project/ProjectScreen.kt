package be.mbolle.crochcounter.projects.presentation.screens.project

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import be.mbolle.crochcounter.R
import be.mbolle.crochcounter.projects.model.Pattern
import be.mbolle.crochcounter.projects.model.PatternItem
import be.mbolle.crochcounter.projects.model.SubPattern
import be.mbolle.crochcounter.projects.presentation.composables.Counter
import be.mbolle.crochcounter.projects.presentation.composables.CrochCounterEditProjectDialog
import be.mbolle.crochcounter.projects.presentation.composables.CrochCounterProjectDialog
import be.mbolle.crochcounter.projects.presentation.composables.PatternTimeline
import be.mbolle.crochcounter.projects.presentation.composables.base.Button
import be.mbolle.crochcounter.ui.CrochCounterViewModel
import be.mbolle.crochcounter.ui.theme.CrochCounterTheme

@Composable
fun ProjectScreen(
    crochCounterViewModel: CrochCounterViewModel,
    innerPadding: PaddingValues = PaddingValues()
) {
    val previewPattern = Pattern(
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
            counter = crochCounterViewModel.crochCounterState.counter.toString(),
            projectName = crochCounterViewModel.crochCounterState.name!!,
            pattern = previewPattern
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
                onClick = { increaseValue() },
                label = stringResource(R.string.add_row_btn)
            )
        }
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

fun isProjectAdaptionVisible(crochCounterViewModel: CrochCounterViewModel): Boolean {
    return crochCounterViewModel.crochCounterState.editProjectState.isVisible
}