package task.swenson.eventplanner.domain.use_case

import task.swenson.eventplanner.data.pojos.Item
import task.swenson.eventplanner.domain.util.InvalidCategoryId
import task.swenson.eventplanner.domain.util.InvalidItem
import task.swenson.eventplanner.domain.util.Resource
import task.swenson.eventplanner.domain.util.TextHelper

class ToggleItemSelection(
    private val validator: ValidateItems = ValidateItems()
) {

    operator fun invoke(item: Item, categoryId: Int? = null): Resource<Item> {
        return if (validator(item).error == null) {
            if (!item.isSelected)
                if (categoryId == null || categoryId <= -1)
                    return Resource.Error(
                        error = TextHelper.Exception(InvalidCategoryId),
                        data = null
                    )

            Resource.Success(
                item.copy(
                    isSelected = !item.isSelected,
                    selectedFromCategoryId = if (item.isSelected) null else categoryId
                )
            )
        } else
            Resource.Error(
                error = TextHelper.Exception(InvalidItem)
            )
    }
}