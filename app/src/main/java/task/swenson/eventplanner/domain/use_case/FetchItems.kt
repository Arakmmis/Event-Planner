package task.swenson.eventplanner.domain.use_case

import task.swenson.eventplanner.data.pojos.Item
import task.swenson.eventplanner.domain.repository.IEventsRepository
import task.swenson.eventplanner.domain.util.InvalidInputData
import task.swenson.eventplanner.domain.util.NullOrEmptyOutputData
import task.swenson.eventplanner.domain.util.Resource
import task.swenson.eventplanner.domain.util.TextHelper

class FetchItems(private val repo: IEventsRepository) {

    suspend operator fun invoke(categoryId: Int): Resource<List<Item>?> {
        if (categoryId <= -1)
            return Resource.Error(
                message = TextHelper.Exception(InvalidInputData),
                data = null
            )

        val result = repo.fetchItems(categoryId)

        return if (result.data.isNullOrEmpty())
            Resource.Error(
                message = TextHelper.Exception(NullOrEmptyOutputData)
            )
        else {
            val categories = getPopulatedCategories(result.data)

            return if (categories.isEmpty())
                Resource.Error(
                    message = TextHelper.Exception(NullOrEmptyOutputData)
                )
            else
                Resource.Success(data = categories)
        }
    }

    private fun getPopulatedCategories(items: List<Item>) =
        items.filter {
            it.id != null
                    && !it.title.isNullOrEmpty()
                    && !it.imageUrl.isNullOrEmpty()
                    && it.minBudget != null
                    && it.maxBudget != null
                    && it.avgBudget != null
        }
}