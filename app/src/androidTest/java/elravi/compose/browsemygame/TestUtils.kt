package elravi.compose.browsemygame

import androidx.activity.ComponentActivity
import androidx.annotation.StringRes
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.navigation.NavController
import org.junit.Assert
import org.junit.rules.TestRule

fun NavController.assertCurrentRouteName(expectedRouteName: String) {
    Assert.assertEquals(expectedRouteName, currentBackStackEntry?.destination?.route)
}

fun <R1 : TestRule, A : ComponentActivity> AndroidComposeTestRule<R1, A>.getString(@StringRes id: Int, vararg args: Any): String {
    return activity.getString(id, *args)
}