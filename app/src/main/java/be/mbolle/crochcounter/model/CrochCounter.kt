package be.mbolle.crochcounter.model

data class CrochCounterState(
    val list: List<CrochCounter> = emptyList<CrochCounter>(),
    val name: String? = null,
    val counter: Int = 0,
    val createProjectState: CreateProjectState = CreateProjectState()
)

data class CreateProjectState(
    val text: String? = "",
    val isVisible: Boolean = false
)

data class CrochCounter(
    val name: String? = null,
    val counter: Int = 0,
    val id: Int
)
