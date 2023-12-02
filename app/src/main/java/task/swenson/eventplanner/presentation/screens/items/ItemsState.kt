package task.swenson.eventplanner.presentation.screens.items

import task.swenson.eventplanner.data.pojos.Item
import task.swenson.eventplanner.domain.util.TextHelper

data class ItemsState(
    val isLoading: Boolean = true,
    val items: List<Item> = emptyList(),
    val minBudget: Int? = null,
    val maxBudget: Int? = null,
    val error: TextHelper? = null
)