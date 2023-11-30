package task.swenson.eventplanner.domain.use_case

import task.swenson.eventplanner.data.pojos.Item
import task.swenson.eventplanner.domain.repository.IEventsRepository
import task.swenson.eventplanner.domain.util.InvalidCategoryId
import task.swenson.eventplanner.domain.util.NoInputProvided
import task.swenson.eventplanner.domain.util.NullOrEmptyOutputData
import task.swenson.eventplanner.domain.util.Resource
import task.swenson.eventplanner.domain.util.TextHelper

class FetchItems(private val repo: IEventsRepository) {

    suspend operator fun invoke(
        categoryId: Int? = null,
        isSelected: Boolean = false
    ): Resource<List<Item>?> {
        return if (categoryId != null) {
            if (categoryId <= -1)
                return Resource.Error(
                    message = TextHelper.Exception(InvalidCategoryId),
                    data = null
                )

            val result = repo.fetchItems(categoryId)

            if (result.message != null)
                return result

            return if (isSelected) {
                validateList(
                    Resource.Success(
                        data = result.data?.filter {
                            it.selectedFromCategoryId == categoryId && it.isSelected
                        }
                    )
                )
            } else
                validateList(result)
        } else if (isSelected) {
            validateList(repo.fetchSelectedItems())
        } else
            Resource.Error(
                message = TextHelper.Exception(NoInputProvided)
            )
    }

    private fun validateList(resource: Resource<List<Item>?>): Resource<List<Item>?> {
        return if (resource.data.isNullOrEmpty())
            Resource.Error(
                message = TextHelper.Exception(NullOrEmptyOutputData)
            )
        else {
            val items = getPopulatedItems(resource.data)

            return if (items.isEmpty())
                Resource.Error(
                    message = TextHelper.Exception(NullOrEmptyOutputData)
                )
            else
                Resource.Success(data = items)
        }
    }

    private fun getPopulatedItems(items: List<Item>) =
        items.filter {
            it.id != null
                    && !it.title.isNullOrEmpty()
                    && it.minBudget != null
                    && it.maxBudget != null
                    && it.avgBudget != null
        }
}