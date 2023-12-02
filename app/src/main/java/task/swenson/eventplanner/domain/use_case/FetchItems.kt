package task.swenson.eventplanner.domain.use_case

import task.swenson.eventplanner.data.pojos.Item
import task.swenson.eventplanner.domain.repository.IEventsRepository
import task.swenson.eventplanner.domain.util.InvalidCategoryId
import task.swenson.eventplanner.domain.util.NoInputProvided
import task.swenson.eventplanner.domain.util.Resource
import task.swenson.eventplanner.domain.util.TextHelper
import javax.inject.Inject

class FetchItems @Inject constructor(
    private val repo: IEventsRepository,
    private val validator: ValidateItems = ValidateItems()
) {

    suspend operator fun invoke(
        categoryId: Int? = null,
        isSelected: Boolean = false
    ): Resource<List<Item>?> {
        return if (categoryId != null) {
            if (categoryId <= -1)
                return Resource.Error(
                    error = TextHelper.Exception(InvalidCategoryId),
                    data = null
                )

            val result = repo.fetchItems(categoryId)

            if (result.error != null)
                return result

            val localSelectedItems = repo.fetchSelectedItems()

            val updatedResult = Resource.Success(
                data = result.data?.map { remoteItem ->
                    localSelectedItems.data?.find { it.id == remoteItem.id } ?: remoteItem
                }
            )

            return if (isSelected) {
                validator(
                    updatedResult.data?.filter {
                        it.selectedFromCategoryId == categoryId && it.isSelected
                    }
                )
            } else
                validator(updatedResult.data)

        } else if (isSelected) {
            val result = repo.fetchSelectedItems()

            if (result.error != null)
                return result
            else
                validator(result.data)

        } else
            Resource.Error(
                error = TextHelper.Exception(NoInputProvided)
            )
    }
}