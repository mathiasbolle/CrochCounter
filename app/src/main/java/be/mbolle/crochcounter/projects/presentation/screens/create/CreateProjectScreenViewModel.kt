package be.mbolle.crochcounter.projects.presentation.screens.create

import androidx.lifecycle.viewModelScope
import be.mbolle.crochcounter.core.model.PatternRepository
import be.mbolle.crochcounter.core.presentation.screens.search.PatternSearchableViewModel
import be.mbolle.crochcounter.projects.model.use_cases.CreateProjectUseCase
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class CreateProjectScreenViewModel(
    val patternRepository: PatternRepository,
    val createProjectUseCase: CreateProjectUseCase
) :
    PatternSearchableViewModel(patternRepository) {

    fun choseSelectedPattern(patternName: String) {
        viewModelScope.launch {
            val pattern = patternByName(patternName).await()

            pattern?.let {
                createProject(pattern)
            }
        }
    }

    fun patternByName(patternName: String): Deferred<be.mbolle.crochcounter.patterns.data.Pattern?> {
        return viewModelScope.async {
            val pattern = patternRepository.getPatternByName(patternName)
            return@async pattern
        }
    }


    fun createProject(pattern: be.mbolle.crochcounter.patterns.data.Pattern) {
        viewModelScope.launch {
            pattern.let { createProjectUseCase.invoke(it.patternId) }
        }
    }
}