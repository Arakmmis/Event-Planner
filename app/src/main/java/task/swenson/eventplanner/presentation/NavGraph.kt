package task.swenson.eventplanner.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import task.swenson.eventplanner.presentation.Arguments.BUDGET
import task.swenson.eventplanner.presentation.Routes.EVENT_BUILDER
import task.swenson.eventplanner.presentation.Routes.TOTAL_BUDGET
import task.swenson.eventplanner.presentation.screens.builder.BuilderScreen
import task.swenson.eventplanner.presentation.screens.total_budget.TotalBudgetScreen

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = EVENT_BUILDER
    ) {
        composable(route = EVENT_BUILDER) {
            BuilderScreen(
                onNavigateToList = {},
                onNavigateToTotalBudget = {
                    navController.navigate("$TOTAL_BUDGET/$it")
                }
            )
        }

        composable(
            route = "$TOTAL_BUDGET/{$BUDGET}",
            arguments = listOf(
                navArgument(name = BUDGET) {
                    type = NavType.IntType
                }
            )
        ) {
            TotalBudgetScreen(budget = it.arguments?.getInt(BUDGET) ?: 0)
        }
    }
}

object Routes {
    const val EVENT_BUILDER = "event_builder"
    const val ITEMS_LIST = "items_list"
    const val TOTAL_BUDGET = "total_budget"
}

object Arguments {
    const val BUDGET = "budget"
}