package task.swenson.eventplanner.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import task.swenson.eventplanner.domain.data_source.ILocalDataSource
import task.swenson.eventplanner.domain.data_source.IRemoteDataSource
import task.swenson.eventplanner.domain.repository.EventsRepository
import task.swenson.eventplanner.domain.repository.IEventsRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides
    @Singleton
    fun provideRepository(
        remoteDataSource: IRemoteDataSource,
        localDataSource: ILocalDataSource
    ): IEventsRepository =
        EventsRepository(
            remoteDataSource = remoteDataSource,
            localDataSource = localDataSource
        )
}