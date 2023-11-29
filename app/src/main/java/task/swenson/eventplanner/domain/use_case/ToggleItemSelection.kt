package task.swenson.eventplanner.domain.use_case

import task.swenson.eventplanner.data.pojos.Item
import task.swenson.eventplanner.domain.util.InvalidInputData
import task.swenson.eventplanner.domain.util.Resource
import task.swenson.eventplanner.domain.util.TextHelper

class ToggleItemSelection {

    operator fun invoke(item: Item): Resource<Item> {
        val isValid = item.id != null
                && !item.title.isNullOrEmpty()
                && !item.imageUrl.isNullOrEmpty()
                && item.minBudget != null
                && item.maxBudget != null
                && item.avgBudget != null

        return if (isValid)
            Resource.Success(item.copy(isSelected = !item.isSelected))
        else
            Resource.Error(
                message = TextHelper.Exception(InvalidInputData)
            )
    }
}