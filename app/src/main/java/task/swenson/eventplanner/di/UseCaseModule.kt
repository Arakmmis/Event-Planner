package task.swenson.eventplanner.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import task.swenson.eventplanner.domain.repository.IEventsRepository
import task.swenson.eventplanner.domain.use_case.DeleteItem
import task.swenson.eventplanner.domain.use_case.FetchCategories
import task.swenson.eventplanner.domain.use_case.FetchItems
import task.swenson.eventplanner.domain.use_case.SumItemsCost
import task.swenson.eventplanner.domain.use_case.ToggleItemSelection
import task.swenson.eventplanner.domain.use_case.UpsertItem

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @Provides
    @ViewModelScoped
    fun provideFetchCategories(repo: IEventsRepository): FetchCategories =
        FetchCategories(repo)

    @Provides
    @ViewModelScoped
    fun provideFetchItems(repo: IEventsRepository): FetchItems =
        FetchItems(repo)

    @Provides
    @ViewModelScoped
    fun provideSumItemsCost(): SumItemsCost = SumItemsCost()

    @Provides
    @ViewModelScoped
    fun provideToggleItemSelection(): ToggleItemSelection =
        ToggleItemSelection()

    @Provides
    @ViewModelScoped
    fun provideUpsertItem(repo: IEventsRepository): UpsertItem =
        UpsertItem(repo)

    @Provides
    @ViewModelScoped
    fun provideDeleteItem(repo: IEventsRepository): DeleteItem =
        DeleteItem(repo)
}