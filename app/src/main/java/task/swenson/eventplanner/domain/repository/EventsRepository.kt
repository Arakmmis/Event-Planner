package task.swenson.eventplanner.domain.repository

import task.swenson.eventplanner.data.pojos.Category
import task.swenson.eventplanner.data.pojos.Item
import task.swenson.eventplanner.domain.data_source.IRemoteDataSource
import task.swenson.eventplanner.domain.util.Resource

class EventsRepository(
    private val remoteDataSource: IRemoteDataSource
) : IEventsRepository {

    override suspend fun fetchCategories(): Resource<List<Category>?> =
        remoteDataSource.fetchCategories()

    override suspend fun fetchItems(categoryId: Int): Resource<List<Item>?> =
        remoteDataSource.fetchItems(categoryId)
}