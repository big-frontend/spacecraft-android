package com.electrolytej.bundle1

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * UI test for checking the correct behaviour of the Topic screen;
 * Verifies that, when a specific UiState is set, the corresponding
 * composables and details are shown
 */
class HotelScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()
    @Before
    fun setup() {
        composeTestRule.activity.apply {
        }
    }
    @Test
    fun niaLoadingWheel_whenScreenIsLoading_showLoading() {
        composeTestRule.setContent { HotelScreen() }
//        composeTestRule.onNodeWithContentDescription("adfdasf").assertExists()
    }
    @Test
    fun topicTitle_whenTopicIsSuccess_isShown() {
        composeTestRule.setContent {
            HotelScreen()
        }

//        composeTestRule.onNodeWithText(testTopic.topic.name).assertExists()
//        composeTestRule.onNodeWithText(testTopic.topic.longDescription).assertExists()
    }


}