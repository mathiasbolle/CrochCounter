package be.mbolle.crochcounter.projects.presentation.screens.create

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import be.mbolle.crochcounter.MainApplication
import be.mbolle.crochcounter.core.presentation.screens.search.SearchableScreen

@Composable
fun CreateProjectScreen(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    createProjectScreenViewModel: CreateProjectScreenViewModel,
    navigateToMainMenu: () -> Unit,
    navigateToCreateProject: () -> Unit,
) {
    Column(modifier = modifier) {
        Text(
            modifier = Modifier.padding(top = 20.dp)
                .semantics(properties = {contentDescription = "Project name counter"}),
            text = "Choose the pattern of your new Project ✨",
            fontSize = 25.sp,
            fontWeight = FontWeight.Normal,
            color = Color(0XFFFF8CA7),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(30.dp))
        SearchableScreen(
            createProject = false,
            paddingValues = paddingValues,
            searchableViewModel = createProjectScreenViewModel,
            searchbarLabel = "Name of the pattern",
            clickPatternItem = {
                createProjectScreenViewModel.choseSelectedPattern(it)
            },
            navigateToCreate = {
            }
        )
    }
}

@Preview
@Composable
fun CreateProjectScreenPreview() {
    val patternMainViewModel: CreateProjectScreenViewModel =
        viewModel(factory = MainApplication.container.createProjectViewModelFactory)

   CreateProjectScreen(
       paddingValues = PaddingValues(),
       navigateToCreateProject = {},
       navigateToMainMenu = {},
       createProjectScreenViewModel = patternMainViewModel
   )
}