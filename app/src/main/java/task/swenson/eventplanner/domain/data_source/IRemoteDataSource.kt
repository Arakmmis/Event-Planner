package task.swenson.eventplanner.domain.data_source

import task.swenson.eventplanner.data.pojos.Category
import task.swenson.eventplanner.data.pojos.Item
import task.swenson.eventplanner.domain.util.Resource

interface IRemoteDataSource {

    suspend fun fetchCategories(): Resource<List<Category>?>

    suspend fun fetchItems(categoryId: Int): Resource<List<Item>?>
}