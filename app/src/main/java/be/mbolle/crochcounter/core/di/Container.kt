package be.mbolle.crochcounter.core.di

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import be.mbolle.crochcounter.core.data.db.CrochDatabase
import be.mbolle.crochcounter.patterns.data.PatternRoomRepository
import be.mbolle.crochcounter.patterns.screens.PatternMainViewModel
import be.mbolle.crochcounter.patterns.screens.create.CreatePatternViewModel
import be.mbolle.crochcounter.projects.data.ProjectRoomRepository
import be.mbolle.crochcounter.projects.model.use_cases.CreateProjectUseCase
import be.mbolle.crochcounter.projects.presentation.screens.create.CreateProjectScreenViewModel

/**
 * DI container for production
 */


interface Container
class ProductionContainer(context: Context) : Container {
    val crocherDatabase = CrochDatabase.getDatabase(context)
    val patternRepository = PatternRoomRepository(crocherDatabase.patternDao())
    val projectRepository = ProjectRoomRepository(crocherDatabase.crochDao())

    val crochetFactory: ViewModelProvider.Factory = viewModelFactory {
        initializer {
            val savedStateHandle = createSavedStateHandle()
            PatternMainViewModel(
                patternRepository = patternRepository,
                patternWithPatternItemDao = crocherDatabase.patternWithPatternItemDao(),
                patternItemDao = crocherDatabase.patternItemDao(),
                savedStateHandle
            )
        }
    }

    val createPatternFactory : ViewModelProvider.Factory by lazy {
        viewModelFactory {
            initializer {
                CreatePatternViewModel()
            }
        }
    }

    val createProjectViewModelFactory: ViewModelProvider.Factory = viewModelFactory {
        initializer {
            CreateProjectScreenViewModel(
                patternRepository = patternRepository,
                createProjectUseCase = CreateProjectUseCase(
                    projectRepository = projectRepository,
                    patternRepository = patternRepository,
                    projectWithPatternDao = crocherDatabase.projectWithPatternDao()
                )
            )
        }
    }
}