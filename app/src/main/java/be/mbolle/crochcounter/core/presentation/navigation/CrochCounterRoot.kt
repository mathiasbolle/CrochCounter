package be.mbolle.crochcounter.core.presentation.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import be.mbolle.crochcounter.projects.presentation.screens.project.ProjectScreen
import be.mbolle.crochcounter.ui.CrochCounterViewModel
import be.mbolle.crochcounter.ui.CrochCounterViewModelFactory
import be.mbolle.crochcounter.core.presentation.util.ScreenWithTopBar
import be.mbolle.crochcounter.projects.presentation.screens.search.SearchProjectScreen
import be.mbolle.crochcounter.projects.presentation.screens.search.SearchProjectViewModel
import be.mbolle.crochcounter.projects.presentation.screens.search.SearchProjectViewModelFactory
import kotlinx.serialization.Serializable


@Serializable
object ProjectScreenNav

@Serializable
object ProjectSearchScreen

@Composable
fun CrochCounterRoot(
    modifier: Modifier = Modifier
) {
    val navControl = rememberNavController()

    NavHost(
        navController = navControl,
        startDestination = ProjectScreenNav, // lastest project plz
        modifier = modifier
    ) {
        composable<ProjectScreenNav> {
            val crochCounterViewModel: CrochCounterViewModel = viewModel(
                factory = CrochCounterViewModelFactory(
                    LocalContext.current
                )
            )
            ScreenWithTopBar(
                enableActions = true,
                crochCounterViewModel = crochCounterViewModel,
                navigateToProject = {
                    navControl.navigate(ProjectSearchScreen)
                }
            ) {
                ProjectScreen(crochCounterViewModel = crochCounterViewModel)
            }

        }
        composable<ProjectSearchScreen>(
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Down,
                    tween(1000)
                )
            }
        ) {
            val crochCounterViewModel: CrochCounterViewModel = viewModel(
                factory = CrochCounterViewModelFactory(
                    LocalContext.current
                )
            )

            val searchProjectViewModel = viewModel<SearchProjectViewModel>(
                factory = SearchProjectViewModelFactory(
                    LocalContext.current
                )
            )
            ScreenWithTopBar(
                enableActions = false,
                crochCounterViewModel = crochCounterViewModel,
                navigateToProject = {
                    navControl.navigate(ProjectSearchScreen)
                }
            ) {
                SearchProjectScreen(
                    paddingValues = PaddingValues(),
                    searchProjectViewModel = searchProjectViewModel,
                ) { projectItem ->
                    crochCounterViewModel.makeProjectVisible(projectItem)
                    navControl.navigate(ProjectScreenNav)
                }
            }
        }
    }
}