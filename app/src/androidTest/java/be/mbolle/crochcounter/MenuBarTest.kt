package be.mbolle.crochcounter

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasParent
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.lifecycle.viewmodel.compose.viewModel
import be.mbolle.crochcounter.ui.CrochCounterApp
import be.mbolle.crochcounter.ui.CrochCounterViewModel
import be.mbolle.crochcounter.ui.CrochCounterViewModelFactory
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MenuBarTest {

    @get:Rule
    val rule = createComposeRule()


    @Before
    fun before() {
        rule.setContent {
            val crocherCounterViewModel: CrochCounterViewModel = viewModel(factory = CrochCounterViewModelFactory(
                LocalContext.current
            ))
            CrochCounterApp(crochCounterViewModel = crocherCounterViewModel)
        }

        rule.onNodeWithContentDescription("show projects").performClick()
        rule.onNodeWithText("New Project").performClick()
        rule.onNodeWithText("Name").performTextInput("item 1")
        rule.onNodeWithText("Confirm").performClick()
        rule.onNodeWithContentDescription("show projects").performClick()
        rule.onNodeWithText("New Project").performClick()
        rule.onNodeWithText("Name").performTextInput("item 2")
        rule.onNodeWithText("Confirm").performClick()
    }

    @Test
    fun switchProjects() {
        rule.onNode(
            hasText("0") and
            hasParent(hasContentDescription("counter"))
        ).assertExists()


        rule.onNodeWithContentDescription("Project name counter").assertTextEquals("item 2")

    }
}