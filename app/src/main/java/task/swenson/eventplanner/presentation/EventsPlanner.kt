package task.swenson.eventplanner.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import task.swenson.eventplanner.presentation.screens.builder.BuilderScreen

@Composable
fun EventsPlannerApp() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.EVENT_BUILDER
    ) {
        composable(route = Routes.EVENT_BUILDER) {
            BuilderScreen {}
        }
    }
}

object Routes {

    const val EVENT_BUILDER = "event builder"
    const val ITEMS_LIST = "items list"
    const val TOTAL_BUDGET = "total budget"
}