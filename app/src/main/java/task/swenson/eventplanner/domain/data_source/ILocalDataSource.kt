package task.swenson.eventplanner.domain.data_source

import task.swenson.eventplanner.data.pojos.Item

interface ILocalDataSource {

    suspend fun fetchItems(): List<Item>?
}