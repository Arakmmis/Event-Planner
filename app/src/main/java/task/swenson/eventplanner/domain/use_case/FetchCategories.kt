package task.swenson.eventplanner.domain.use_case

import task.swenson.eventplanner.data.pojos.Category
import task.swenson.eventplanner.domain.repository.IEventsRepository
import task.swenson.eventplanner.domain.util.NullOrEmptyOutputData
import task.swenson.eventplanner.domain.util.Resource
import task.swenson.eventplanner.domain.util.TextHelper
import javax.inject.Inject

class FetchCategories @Inject constructor(
    private val repo: IEventsRepository
) {

    suspend operator fun invoke(): Resource<List<Category>?> {
        val result = repo.fetchCategories()

        return if (result.data.isNullOrEmpty())
            Resource.Error(
                error = TextHelper.Exception(NullOrEmptyOutputData)
            )
        else {
            val populatedCategories = result.data.filter {
                it.id != null && !it.title.isNullOrEmpty() && !it.imageUrl.isNullOrEmpty()
            }

            return if (populatedCategories.isEmpty())
                Resource.Error(
                    error = TextHelper.Exception(NullOrEmptyOutputData)
                )
            else
                Resource.Success(data = populatedCategories)
        }
    }
}