package task.swenson.eventplanner.data.remote

import retrofit2.HttpException
import task.swenson.eventplanner.data.pojos.Category
import task.swenson.eventplanner.data.pojos.Item
import task.swenson.eventplanner.domain.data_source.IRemoteDataSource
import task.swenson.eventplanner.domain.util.Resource
import task.swenson.eventplanner.domain.util.TextHelper
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val api: Api
) : IRemoteDataSource {

    override suspend fun fetchCategories(): Resource<List<Category>?> {
        val result = api.fetchCategories()

        return if (result.isSuccessful)
            Resource.Success(data = result.body())
        else
            Resource.Error(
                error = TextHelper.Exception(HttpException(result)),
                data = result.body()
            )
    }

    override suspend fun fetchItems(categoryId: Int): Resource<List<Item>?> {
        val result = api.fetchItems(categoryId)

        return if (result.isSuccessful)
            Resource.Success(data = result.body())
        else
            Resource.Error(
                error = TextHelper.Exception(HttpException(result)),
                data = result.body()
            )
    }
}