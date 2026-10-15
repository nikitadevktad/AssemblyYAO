package com.nikitadevktad.assemblyyao

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.v2.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import org.junit.Rule
import org.junit.Test

class AppNavigationTest {
    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun contactsDestinationOpensFromBottomBar() {
        composeRule.onNodeWithTag("bottom_contacts").performClick()
        composeRule.onNodeWithTag("contacts_screen").assertIsDisplayed()
    }

    @Test
    fun drawerOpensFromHomeMenuAndNavigatesToAbout() {
        composeRule.onNodeWithContentDescription("Меню").performClick()
        composeRule.onNodeWithTag("drawer_about").performClick()
        composeRule.onNodeWithTag("bottom_about").assertIsDisplayed()
    }
}
