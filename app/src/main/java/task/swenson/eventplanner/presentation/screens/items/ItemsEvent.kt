package task.swenson.eventplanner.presentation.screens.items

import task.swenson.eventplanner.data.pojos.Item
import task.swenson.eventplanner.domain.util.TextHelper

sealed class ItemsEvent {
    data class ScreenStarted(val categoryId: Int) : ItemsEvent()

    data class ItemsLoaded(val items: List<Item>) : ItemsEvent()

    data class TotalBudgetLoaded(
        val minBudget: Int? = null,
        val maxBudget: Int? = null
    ) : ItemsEvent()

    data class ItemUpdated(val item: Item) : ItemsEvent()

    data class ItemClicked(val item: Item) : ItemsEvent()

    data class ErrorLoading(val error: TextHelper) : ItemsEvent()
}
