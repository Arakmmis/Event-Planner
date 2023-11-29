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
        else
            result
    }
}