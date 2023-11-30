package task.swenson.eventplanner.domain.repository

import task.swenson.eventplanner.data.pojos.Category
import task.swenson.eventplanner.data.pojos.Item
import task.swenson.eventplanner.domain.data_source.ILocalDataSource
import task.swenson.eventplanner.domain.data_source.IRemoteDataSource
import task.swenson.eventplanner.domain.util.NullOrEmptyOutputData
import task.swenson.eventplanner.domain.util.Resource
import task.swenson.eventplanner.domain.util.TextHelper
import task.swenson.eventplanner.domain.util.UpsertFailure

class EventsRepository(
    private val remoteDataSource: IRemoteDataSource,
    private val localDataSource: ILocalDataSource
) : IEventsRepository {

    override suspend fun fetchCategories(): Resource<List<Category>?> =
        remoteDataSource.fetchCategories()

    override suspend fun fetchItems(categoryId: Int): Resource<List<Item>?> =
        remoteDataSource.fetchItems(categoryId)

    override suspend fun fetchSelectedItems(): Resource<List<Item>?> {
        val items = localDataSource.fetchItems()

        return if (items != null)
            Resource.Success(items)
        else
            Resource.Error(
                error = TextHelper.Exception(NullOrEmptyOutputData)
            )
    }

    override suspend fun upsertItem(item: Item): Resource<Item> {
        val isSuccess = localDataSource.upsertItem(item)

        return if (isSuccess)
            Resource.Success(item)
        else
            Resource.Error(
                error = TextHelper.Exception(UpsertFailure)
            )
    }
}