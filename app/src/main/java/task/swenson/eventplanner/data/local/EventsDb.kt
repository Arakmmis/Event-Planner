package task.swenson.eventplanner.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import task.swenson.eventplanner.data.pojos.Item

@Database(
    entities = [Item::class],
    version = DbConfig.DB_VERSION,
    exportSchema = false
)
abstract class EventsDb : RoomDatabase() {

    abstract fun eventsDao(): EventsDao
}