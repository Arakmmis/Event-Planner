package task.swenson.eventplanner.presentation.screens.builder

import task.swenson.eventplanner.data.pojos.Category
import task.swenson.eventplanner.domain.util.TextHelper

data class BuilderState(
    val isLoading: Boolean = true,
    val categories: List<Category> = emptyList(),
    val totalBudget: Int? = null,
    val error: TextHelper? = null
)