package task.swenson.eventplanner.domain.repository

import task.swenson.eventplanner.data.pojos.Category
import task.swenson.eventplanner.data.pojos.Item
import task.swenson.eventplanner.domain.util.Resource

interface IEventsRepository {

    suspend fun fetchCategories(): Resource<List<Category>?>

    suspend fun fetchItems(categoryId: Int): Resource<List<Item>?>

    suspend fun fetchSelectedItems(): Resource<List<Item>?>

    suspend fun upsertItem(item: Item): Resource<Item>

    suspend fun deleteItem(item: Item): Resource<Item>
}