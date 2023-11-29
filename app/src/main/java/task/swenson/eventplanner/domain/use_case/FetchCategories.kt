package task.swenson.eventplanner.domain.use_case

import task.swenson.eventplanner.data.pojos.Category
import task.swenson.eventplanner.domain.repository.IEventsRepository
import task.swenson.eventplanner.domain.util.Resource

class FetchCategories(
    private val repository: IEventsRepository
) {

    suspend operator fun invoke(): Resource<List<Category>?> =
        repository.fetchCategories()
}