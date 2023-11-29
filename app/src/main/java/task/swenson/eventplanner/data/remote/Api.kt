package task.swenson.eventplanner.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import task.swenson.eventplanner.data.pojos.Category
import task.swenson.eventplanner.data.pojos.Item
import task.swenson.eventplanner.data.remote.Url.PATH_CATEGORY_ID
import task.swenson.eventplanner.data.remote.Url.URL_CATEGORIES
import task.swenson.eventplanner.data.remote.Url.URL_ITEMS

interface Api {

    @GET(URL_CATEGORIES)
    suspend fun fetchCategories(): Response<List<Category>>

    @GET(URL_ITEMS)
    suspend fun fetchItems(
        @Path(PATH_CATEGORY_ID) categoryId: Int
    ): Response<List<Item>>
}