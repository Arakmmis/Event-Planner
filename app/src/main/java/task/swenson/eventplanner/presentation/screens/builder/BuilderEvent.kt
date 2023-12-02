package task.swenson.eventplanner.presentation.screens.builder

import task.swenson.eventplanner.data.pojos.Category
import task.swenson.eventplanner.domain.util.TextHelper

sealed class BuilderEvent {
    data object ScreenStarted : BuilderEvent()

    data class CategoriesLoaded(val categories: List<Category>) : BuilderEvent()

    data class TotalBudgetLoaded(val totalBudget: Int? = null): BuilderEvent()

    data class CategoryClicked(val category: Category) : BuilderEvent()

    data class SaveClicked(val totalBudget: Int) : BuilderEvent()

    data class ErrorLoading(val error: TextHelper) : BuilderEvent()
}