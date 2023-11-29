package task.swenson.eventplanner.data.remote

import retrofit2.HttpException
import task.swenson.eventplanner.data.pojos.Category
import task.swenson.eventplanner.domain.data_source.IRemoteDataSource
import task.swenson.eventplanner.domain.util.Resource
import task.swenson.eventplanner.domain.util.TextHelper

class RemoteDataSource(private val api: Api) : IRemoteDataSource {

    override suspend fun fetchCategories(): Resource<List<Category>?> {
        val result = api.fetchCategories()

        return if (result.isSuccessful)
            Resource.Success(data = result.body())
        else
            Resource.Error(
                message = TextHelper.Exception(HttpException(result)),
                data = result.body()
            )
    }
}