package task.swenson.eventplanner.data.remote

import retrofit2.Response
import retrofit2.http.GET
import task.swenson.eventplanner.data.pojos.Category
import task.swenson.eventplanner.data.remote.Url.URL_CATEGORIES

interface Api {

    @GET(URL_CATEGORIES)
    suspend fun fetchCategories(): Response<List<Category>>
}