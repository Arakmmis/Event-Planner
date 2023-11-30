package task.swenson.eventplanner.domain.use_case

import task.swenson.eventplanner.data.pojos.Item
import task.swenson.eventplanner.domain.repository.IEventsRepository
import task.swenson.eventplanner.domain.util.DeletionFailure
import task.swenson.eventplanner.domain.util.Resource
import task.swenson.eventplanner.domain.util.TextHelper

class DeleteItem(
    private val repo: IEventsRepository,
    private val validator: ValidateItems = ValidateItems()
) {

    suspend operator fun invoke(item: Item?): Resource<Item> {
        val isValidItem = validator(item)

        if (isValidItem.error != null)
            return isValidItem

        val result = item?.let { repo.deleteItem(it) }

        return if (result?.data == null)
            Resource.Error(
                error = TextHelper.Exception(DeletionFailure)
            )
        else
            result
    }
}