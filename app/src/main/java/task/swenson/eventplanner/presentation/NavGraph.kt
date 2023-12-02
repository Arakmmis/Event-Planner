package task.swenson.eventplanner.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import task.swenson.eventplanner.presentation.Arguments.BUDGET
import task.swenson.eventplanner.presentation.Arguments.CATEGORY_ID
import task.swenson.eventplanner.presentation.Arguments.CATEGORY_NAME
import task.swenson.eventplanner.presentation.Routes.EVENT_BUILDER
import task.swenson.eventplanner.presentation.Routes.ITEMS_LIST
import task.swenson.eventplanner.presentation.Routes.TOTAL_BUDGET
import task.swenson.eventplanner.presentation.screens.builder.BuilderScreen
import task.swenson.eventplanner.presentation.screens.items.ItemsScreen
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
                onNavigateToList = { categoryId, categoryName ->
                    navController.navigate("$ITEMS_LIST/$categoryId/$categoryName")
                },
                onNavigateToTotalBudget = {
                    navController.navigate("$TOTAL_BUDGET/$it")
                }
            )
        }

        composable(
            route = "$ITEMS_LIST/{$CATEGORY_ID}/{$CATEGORY_NAME}",
            arguments = listOf(
                navArgument(name = CATEGORY_ID) {
                    type = NavType.IntType
                },
                navArgument(name = CATEGORY_NAME) {
                    type = NavType.StringType
                }
            )
        ) {
            ItemsScreen(
                categoryId = it.arguments?.getInt(CATEGORY_ID) ?: -1,
                categoryName = it.arguments?.getString(CATEGORY_NAME) ?: ""
            ) {
                navController.popBackStack()
            }
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
    const val CATEGORY_ID = "category_id"
    const val CATEGORY_NAME = "category_name"
}