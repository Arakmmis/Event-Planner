package task.swenson.eventplanner.domain.repository

import task.swenson.eventplanner.data.pojos.Category
import task.swenson.eventplanner.domain.util.Resource

interface IEventsRepository {

    suspend fun fetchCategories(): Resource<List<Category>?>
}