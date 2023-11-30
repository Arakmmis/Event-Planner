package task.swenson.eventplanner.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import task.swenson.eventplanner.data.local.DbConfig
import task.swenson.eventplanner.data.local.EventsDao
import task.swenson.eventplanner.data.local.EventsDb
import task.swenson.eventplanner.data.local.LocalDataSource
import task.swenson.eventplanner.domain.data_source.ILocalDataSource
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalDataSourceModule {

    @Provides
    @Singleton
    fun provideLocalDataSource(
        dao: EventsDao
    ): ILocalDataSource =
        LocalDataSource(dao)

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): EventsDb =
        Room.databaseBuilder(
            context,
            EventsDb::class.java,
            DbConfig.DB_NAME
        )
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideDao(db: EventsDb): EventsDao =
        db.eventsDao()
}