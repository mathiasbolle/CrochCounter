package be.mbolle.crochcounter.projects.presentation.screens.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import be.mbolle.crochcounter.R
import be.mbolle.crochcounter.core.presentation.composables.SearchCrochCounter
import be.mbolle.crochcounter.ui.theme.CrochCounterTheme

@Composable
fun SearchProjectScreen(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    searchProjectViewModel: SearchProjectViewModel,
    navigateToMainMenu: (projectItem: String) -> Unit,
) {

    val projectItems = searchProjectViewModel.projectItems.collectAsState()

    LaunchedEffect(searchProjectViewModel.searchProjectName) {
        searchProjectViewModel.run()
    }

    Column(
        modifier = modifier
            .padding(paddingValues)
            .fillMaxSize()
            .padding(20.dp)
    ) {

        SearchCrochCounter(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp),
            initialText = "Name of the project",
            wordState = searchProjectViewModel.searchProjectName
        )

        SearchableProjectList(
            modifier = modifier,
            paddingValues = PaddingValues(horizontal = 5.dp),
            projects = projectItems.value
        ) { projectItem ->
            navigateToMainMenu(projectItem)
        }
    }
}

@Composable
fun SearchableProjectList(
    projects: List<String>,
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues = PaddingValues(),
    onClick: (projectItem: String) -> Unit,
) {
    LazyColumn(modifier = modifier, contentPadding = paddingValues) {
        items(projects) { projectItem ->
            SearchableProject(projectItem) {
                onClick(projectItem)
            }
        }
    }
}

@Composable
fun SearchableProject(
    projectName: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .padding(5.dp)
            .background(Color(0XFFffd6e0))
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Row {
            Image(
                painter = painterResource(R.drawable.imagesearchable_logo),
                contentDescription = "Project logo"
            )
            Spacer(modifier = Modifier.width(15.dp))
            Text(text = projectName, color = Color(0XFFfe6689))
        }
        Row {
            IconButton(onClick = {
                /* event */
            }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = "Go to project $projectName",
                    tint = Color(0XFFec407a)
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun SearchProjectScreenPreview() {
    val searchProjectViewModel = viewModel<SearchProjectViewModel>(
        factory = SearchProjectViewModelFactory(
            LocalContext.current
        )
    )

    SearchProjectScreen(
        paddingValues = PaddingValues(),
        searchProjectViewModel = searchProjectViewModel
    ) {
        /** no navigation in this preview. */
    }
}

@Preview(showBackground = true)
@Composable
fun SearchableProjectPreview() {
    val projects = listOf("Cow 1", "Cow 2")

    CrochCounterTheme {
        SearchableProjectList(
            projects = projects
        ) {
            // TODO emulate an active project
        }
    }
}