package task.swenson.eventplanner.domain.use_case

import task.swenson.eventplanner.data.pojos.Item
import task.swenson.eventplanner.domain.util.InvalidItem
import task.swenson.eventplanner.domain.util.Resource
import task.swenson.eventplanner.domain.util.TextHelper

class ToggleItemSelection(
    private val validator: ValidateItems = ValidateItems()
) {

    operator fun invoke(item: Item): Resource<Item> {
        return if (validator(item).error == null)
            Resource.Success(item.copy(isSelected = !item.isSelected))
        else
            Resource.Error(
                error = TextHelper.Exception(InvalidItem)
            )
    }
}