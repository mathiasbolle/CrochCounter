package be.mbolle.crochcounter.core.presentation.screens.search

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import be.mbolle.crochcounter.R
import be.mbolle.crochcounter.core.presentation.composables.CreateFabButton
import be.mbolle.crochcounter.core.presentation.composables.SearchCrochCounter
import be.mbolle.crochcounter.ui.theme.CrochCounterTheme

// convert this to a composable that creates a project and a composable that just reads the project LIKE A GET
@Composable
fun CreateProjectSearchableScreen(
    modifier: Modifier = Modifier,
    searchbarLabel: String = "Name of the project",
    paddingValues: PaddingValues,
    searchableViewModel: SearchableViewModel,
    navigateToCreateProject: () -> Unit,
) {

}

@Composable
fun SearchableScreen(
    modifier: Modifier = Modifier,
    searchbarLabel: String = "Name of the project",
    createProject: Boolean = true,
    paddingValues: PaddingValues,
    searchableViewModel: SearchableViewModel,
    clickPatternItem: (projectItem: String) -> Unit,
    navigateToCreate: () -> Unit, // refactor
) {

    val items = searchableViewModel.items.collectAsState()

    LaunchedEffect(searchableViewModel.searchName) {
        searchableViewModel.run()
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
            initialText = searchbarLabel,
            wordState = searchableViewModel.searchName
        )

        SearchableList(
            modifier = modifier,
            paddingValues = PaddingValues(horizontal = 5.dp),
            items = items.value
        ) { item ->
                clickPatternItem(item)
        }

        if (createProject) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.Bottom
            ) {
                CreateFabButton {
                    navigateToCreate()
                }
            }
        }
    }
}

@Composable
fun SearchableList(
    items: List<String>,
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues = PaddingValues(),
    onClick: (item: String) -> Unit,
) {
    LazyColumn(modifier = modifier, contentPadding = paddingValues) {
        items(items) { item ->
            SearchableItem(item) {
                onClick(item)
            }
        }
    }
}

@Composable
fun SearchableItem(
    name: String,
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
            Text(text = name, color = Color(0XFFfe6689))
        }
        Row {
            IconButton(onClick = {
                /* event */
            }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = "Go to $name",
                    tint = Color(0XFFec407a)
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun SearchableScreenPreview() {
    val searchableViewModel = viewModel<SearchableViewModel>(
        factory = GenericViewModelFactory(
            LocalContext.current,
            searchType = SearchType.PROJECT
        )
    )

    SearchableScreen(
        paddingValues = PaddingValues(),
        searchableViewModel = searchableViewModel,
        clickPatternItem = {}
    ) {
        /** no navigation in this preview. */
    }
}

@Preview(showBackground = true)
@Composable
fun SearchableItemPreview() {
    val projects = listOf("Cow 1", "Cow 2")

    CrochCounterTheme {
        SearchableList(
            items = projects
        ) {
            // TODO emulate an active project
        }
    }
}