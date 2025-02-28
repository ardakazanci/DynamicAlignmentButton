package com.ardakazanci.dynamicalignmentbutton

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import org.junit.Rule
import org.junit.Test

class AlignmentMenuDemoTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testClickableBoxesExist() {
        composeTestRule.setContent {
            AlignmentMenuDemo()
        }

        composeTestRule.onNodeWithTag("leftBox").assertExists()
        composeTestRule.onNodeWithTag("centerBox").assertExists()
        composeTestRule.onNodeWithTag("rightBox").assertExists()
    }
}