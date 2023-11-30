package task.swenson.eventplanner.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import task.swenson.eventplanner.data.local.Query.SELECT_ALL_ITEMS_FROM_TABLE
import task.swenson.eventplanner.data.pojos.Item

@Dao
interface EventsDao {

    @Upsert
    suspend fun upsertItem(item: Item): Long

    @Delete
    suspend fun deleteItem(item: Item): Int

    @Query(SELECT_ALL_ITEMS_FROM_TABLE)
    suspend fun fetchItems(): List<Item>?
}