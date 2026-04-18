package com.csk.astromap.ui.screen

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.csk.astromap.data.model.ISSAstros
import com.csk.astromap.data.model.ISSLocation
import com.csk.astromap.data.model.ISSPosition
import com.csk.astromap.ui.viewmodel.ISSInfo
import com.csk.astromap.ui.viewmodel.RecentItemUiState
import org.junit.Rule
import org.junit.Test

class SuccessScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun successScreen_displaysISSLocation() {
        val issInfo = ISSInfo(
            issLocation = ISSLocation(ISSPosition("10.0", "20.0"), "success", 123),
            issAstros = ISSAstros("success", 0, emptyList())
        )
        val recentState = RecentItemUiState(emptyList())

        composeTestRule.setContent {
            SuccessScreen(issInfo = issInfo, recentItemUiState = recentState)
        }

        composeTestRule.onNodeWithText("ISS Location", substring = true).assertIsDisplayed()
        composeTestRule.onNodeWithText("10.0", substring = true).assertIsDisplayed()
        composeTestRule.onNodeWithText("20.0", substring = true).assertIsDisplayed()
    }
}
