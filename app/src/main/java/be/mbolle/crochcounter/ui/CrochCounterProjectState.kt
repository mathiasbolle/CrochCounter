package be.mbolle.crochcounter.ui

import be.mbolle.crochcounter.projects.model.Pattern


sealed class CrochCounterProjectState {
    object Loading : CrochCounterProjectState()
    data class Succes(
        val counter: String,
        val projectTitle: String,
        val patterns: List<Pattern>
    ) : CrochCounterProjectState()

    class Error(val errorMessage: String) : CrochCounterProjectState()
}
