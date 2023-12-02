package task.swenson.eventplanner.presentation.screens.builder

sealed class BuilderSideEffects {

    data class NavigateToItemsList(val categoryId: Int) : BuilderSideEffects()

    data class NavigateToTotalBudget(val totalBudget: Int) : BuilderSideEffects()
}