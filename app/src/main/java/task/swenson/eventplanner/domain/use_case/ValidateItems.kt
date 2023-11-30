package task.swenson.eventplanner.domain.use_case

import task.swenson.eventplanner.data.pojos.Item
import task.swenson.eventplanner.domain.util.InvalidItem
import task.swenson.eventplanner.domain.util.NullOrEmptyOutputData
import task.swenson.eventplanner.domain.util.Resource
import task.swenson.eventplanner.domain.util.TextHelper

class ValidateItems {

    operator fun invoke(item: Item?): Resource<Item> =
        item?.let {
            if (isItemValid(item))
                Resource.Success(data = item)
            else
                Resource.Error(
                    error = TextHelper.Exception(InvalidItem)
                )
        } ?: Resource.Error(
            error = TextHelper.Exception(InvalidItem)
        )

    operator fun invoke(items: List<Item>?): Resource<List<Item>?> {
        if (items.isNullOrEmpty())
            return Resource.Error(
                error = TextHelper.Exception(NullOrEmptyOutputData)
            )

        val validItems = items.filter { isItemValid(it) }

        return if (validItems.isEmpty())
            Resource.Error(
                error = TextHelper.Exception(NullOrEmptyOutputData)
            )
        else
            Resource.Success(data = validItems)
    }

    private fun isItemValid(item: Item): Boolean =
        item.id != null
                && !item.title.isNullOrEmpty()
                && item.minBudget != null
                && item.maxBudget != null
                && item.avgBudget != null
}