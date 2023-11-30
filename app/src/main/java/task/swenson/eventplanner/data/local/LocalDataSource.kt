package task.swenson.eventplanner.data.local

import task.swenson.eventplanner.data.pojos.Item
import task.swenson.eventplanner.domain.data_source.ILocalDataSource
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val eventsDao: EventsDao
) : ILocalDataSource {

    override suspend fun fetchItems(): List<Item>? =
        eventsDao.fetchItems()

    override suspend fun upsertItem(item: Item): Boolean {
        val rowId = eventsDao.upsertItem(item)
        return rowId == item.id.toLong()
    }

    override suspend fun deleteItem(item: Item): Boolean {
        val deletedRows = eventsDao.deleteItem(item)
        return deletedRows == 1
    }
}