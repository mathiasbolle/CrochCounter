package be.mbolle.crochcounter.core.presentation.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import be.mbolle.crochcounter.MainApplication
import be.mbolle.crochcounter.core.presentation.screens.search.PatternViewModelFactory
import be.mbolle.crochcounter.core.presentation.screens.search.ProjectSearchableViewModel
import be.mbolle.crochcounter.core.presentation.screens.search.ProjectViewModelFactory
import be.mbolle.crochcounter.core.presentation.screens.search.SearchableScreen
import be.mbolle.crochcounter.core.presentation.screens.search.SearchableViewModel
import be.mbolle.crochcounter.core.presentation.util.ScreenWithTopBar
import be.mbolle.crochcounter.core.presentation.util.ScreenWithoutTopBar
import be.mbolle.crochcounter.patterns.screens.PatternMainScreen
import be.mbolle.crochcounter.patterns.screens.PatternMainViewModel
import be.mbolle.crochcounter.patterns.screens.create.CreatePatternViewModel
import be.mbolle.crochcounter.patterns.screens.create.name.CreateNameOfPattern
import be.mbolle.crochcounter.patterns.screens.create.patternItems.PatternItemScreen
import be.mbolle.crochcounter.projects.presentation.screens.create.CreateProjectScreen
import be.mbolle.crochcounter.projects.presentation.screens.create.CreateProjectScreenViewModel
import be.mbolle.crochcounter.projects.presentation.screens.project.ProjectScreen
import be.mbolle.crochcounter.projects.presentation.screens.search.SearchProjectViewModel
import be.mbolle.crochcounter.projects.presentation.screens.search.SearchProjectViewModelFactory
import be.mbolle.crochcounter.ui.CrochCounterViewModel
import be.mbolle.crochcounter.ui.CrochCounterViewModelFactory
import kotlinx.serialization.Serializable

@Serializable
object ProjectScreens {
    @Serializable
    object ProjectScreenNav

    @Serializable
    object ProjectSearchScreen

    @Serializable
    object ProjectCreateScreen
}

@Serializable
object PatternScreens {

    @Serializable
    data class PatternMainScreen(val name: String)

    @Serializable
    object PatternSearchScreen


    @Serializable
    object PatternCreateScreen {

        @Serializable
        object CreateNameSubscreen

        @Serializable
        object CreatePatternPartsSubscreen

        @Serializable
        object SummarySubScreen
    }
}

@Composable
fun CrochCounterRoot(
    modifier: Modifier = Modifier
) {
    val navControl = rememberNavController()
    val createPatternViewModel = viewModel(factory = MainApplication.container.createPatternFactory) as CreatePatternViewModel

    NavHost(
        navController = navControl,
        startDestination = ProjectScreens,
        modifier = modifier
    ) {
        projectNavGraph(navControl)
        patternNavGraph(navControl)
        patternCreationNavGraph(navControl, createPatternViewModel)
    }
}

fun NavGraphBuilder.patternCreationNavGraph(navControl: NavHostController, vm: CreatePatternViewModel) {
    navigation<PatternScreens.PatternCreateScreen>(
        startDestination = PatternScreens.PatternCreateScreen.CreateNameSubscreen
    ) {
        composable<PatternScreens.PatternCreateScreen.CreateNameSubscreen> {
            ScreenWithoutTopBar {
                CreateNameOfPattern(createPatternViewModel = vm) {
                    navControl.navigate(PatternScreens.PatternCreateScreen.CreatePatternPartsSubscreen)
                }
            }
        }

        composable<PatternScreens.PatternCreateScreen.CreatePatternPartsSubscreen> {
            ScreenWithoutTopBar {
                PatternItemScreen(createPatternViewModel = vm)
            }
        }
    }
}

fun NavGraphBuilder.patternNavGraph(navControl: NavHostController) {
    navigation<PatternScreens>(
        startDestination = PatternScreens.PatternSearchScreen,
    ) {
        composable<PatternScreens.PatternSearchScreen> {

            val crochCounterViewModel: CrochCounterViewModel = viewModel(
                factory = CrochCounterViewModelFactory(
                    LocalContext.current
                )
            )

            val patternViewModel = viewModel<SearchableViewModel>(
                factory = PatternViewModelFactory(
                    LocalContext.current,

                    )
            )
            ScreenWithTopBar(
                name = "Patterns",
                enableActions = false,
                crochCounterViewModel = crochCounterViewModel,
                navigateToProject = {
                    navControl.navigate(ProjectScreens.ProjectSearchScreen)
                },
                navigateToPatterns = {
                    navControl.navigate(PatternScreens)
                }
            ) {
                SearchableScreen(
                    paddingValues = PaddingValues(),
                    searchableViewModel = patternViewModel,
                    searchbarLabel = "Name of the pattern",
                    clickPatternItem = { patternItem ->
                        //crochCounterViewModel.makeProjectVisible(projectItem)
                        //now pass it as parameter, there is no reason to make a global crochCounterViewModel..
                        navControl.navigate(PatternScreens.PatternMainScreen(name = patternItem))
                    },
                    navigateToCreate = {
                        //TODO pattern issue
                        navControl.navigate(
                            PatternScreens.PatternCreateScreen

                        )
                    }
                )
            }
        }
        composable<PatternScreens.PatternMainScreen> {
            val patternMainViewModel: PatternMainViewModel =
                viewModel(factory = MainApplication.container.crochetFactory)

            val crochCounterViewModel: CrochCounterViewModel = viewModel(
                factory = CrochCounterViewModelFactory(
                    LocalContext.current
                )
            )
            ScreenWithTopBar(
                name = "Patterns",
                enableActions = false,
                crochCounterViewModel = crochCounterViewModel,
                navigateToProject = {
                    navControl.navigate(ProjectScreens.ProjectSearchScreen)
                },
                navigateToPatterns = {
                    navControl.navigate(PatternScreens)
                }
            ) {
                PatternMainScreen(patternMainViewModel = patternMainViewModel)
            }
        }
    }
}

fun NavGraphBuilder.projectNavGraph(navController: NavHostController) {
    navigation<ProjectScreens>(
        startDestination = ProjectScreens.ProjectScreenNav,
    ) {
        composable<ProjectScreens.ProjectScreenNav> {
            val crochCounterViewModel: CrochCounterViewModel = viewModel(
                factory = CrochCounterViewModelFactory(
                    LocalContext.current
                )
            )
            ScreenWithTopBar(
                enableActions = true,
                crochCounterViewModel = crochCounterViewModel,
                navigateToProject = {
                    navController.navigate(ProjectScreens.ProjectSearchScreen)
                },

                navigateToPatterns = {
                    navController.navigate(PatternScreens)
                }
            ) {
                ProjectScreen(crochCounterViewModel = crochCounterViewModel)
            }
        }

        composable<ProjectScreens.ProjectSearchScreen>(
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

            viewModel<SearchProjectViewModel>(
                factory = SearchProjectViewModelFactory(
                    LocalContext.current
                )
            )
            val searchProjectViewModel2 = viewModel<ProjectSearchableViewModel>(
                factory = ProjectViewModelFactory(LocalContext.current)
            )

            ScreenWithTopBar(
                enableActions = false,
                crochCounterViewModel = crochCounterViewModel,
                navigateToProject = {
                    navController.navigate(ProjectScreens.ProjectSearchScreen)
                },
                navigateToPatterns = {
                    navController.navigate(PatternScreens)
                }
            ) {
                SearchableScreen(
                    paddingValues = PaddingValues(),
                    //searchProjectViewModel = searchProjectViewModel,
                    searchableViewModel = searchProjectViewModel2,
                    clickPatternItem = { projectItem ->
                        crochCounterViewModel.makeProjectVisible(projectItem)
                        navController.navigate(ProjectScreens.ProjectScreenNav)
                    },
                    navigateToCreate = {
                        navController.navigate(ProjectScreens.ProjectCreateScreen)
                    }
                )
            }
        }

        composable<ProjectScreens.ProjectCreateScreen> {
            val patternMainViewModel: CreateProjectScreenViewModel =
                viewModel(factory = MainApplication.container.createProjectViewModelFactory)

            ScreenWithTopBar(navController) {
                CreateProjectScreen(
                    paddingValues = PaddingValues(),
                    navigateToMainMenu = {

                    },
                    navigateToCreateProject = {

                    },
                    createProjectScreenViewModel = patternMainViewModel
                )
            }
        }
    }
}

@Composable
fun ScreenWithTopBar(
    navController: NavController,
    content: @Composable() () -> Unit
) {
    val crochCounterViewModel: CrochCounterViewModel = viewModel(
        factory = CrochCounterViewModelFactory(
            LocalContext.current
        )
    )
    ScreenWithTopBar(
        enableActions = false,
        crochCounterViewModel = crochCounterViewModel,
        navigateToProject = {
            navController.navigate(ProjectScreens.ProjectSearchScreen)
        },
        navigateToPatterns = {
            navController.navigate(PatternScreens)
        }
    ) {
        content()
    }
}