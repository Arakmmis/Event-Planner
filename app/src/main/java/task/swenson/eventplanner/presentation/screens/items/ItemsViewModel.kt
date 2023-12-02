package task.swenson.eventplanner.presentation.screens.items

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.BUFFERED
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import task.swenson.eventplanner.data.pojos.Item
import task.swenson.eventplanner.domain.use_case.FetchItems
import task.swenson.eventplanner.domain.use_case.UpsertItem
import task.swenson.eventplanner.domain.util.NullOrEmptyOutputData
import task.swenson.eventplanner.domain.util.TextHelper
import task.swenson.eventplanner.util.StateReducerFlow
import javax.inject.Inject

@HiltViewModel
class ItemsViewModel @Inject constructor(
    private val fetchItems: FetchItems,
    private val upsertItem: UpsertItem
) : ViewModel() {

    val state = StateReducerFlow(
        initialState = ItemsState(),
        reduceState = ::reduceState
    )

    private val sideEffectsChannel = Channel<ItemsSideEffects>(BUFFERED)
    val sideEffects = sideEffectsChannel.receiveAsFlow()

    private var categoryId = -1

    private fun reduceState(
        state: ItemsState,
        event: ItemsEvent
    ): ItemsState = when (event) {
        is ItemsEvent.ScreenStarted -> {
            categoryId = event.categoryId
            getItems(event.categoryId)
            state.copy(isLoading = true)
        }

        is ItemsEvent.ItemsLoaded -> {
            getTotalBudget(categoryId)
            state.copy(isLoading = false, items = event.items)
        }

        is ItemsEvent.TotalBudgetLoaded ->
            state.copy(isLoading = false, totalBudget = event.totalBudget)

        is ItemsEvent.ItemClicked -> {
            updateItem(event.item)
            state.copy(isLoading = true)
        }

        is ItemsEvent.ItemUpdated -> {
            val items = state.items.toMutableList()
            val oldItem = items.find { it.id == event.item.id }
            val index = items.indexOf(oldItem)

            items.remove(oldItem)
            items.add(index, event.item)

            state.copy(isLoading = false, items = items)
        }

        is ItemsEvent.ErrorLoading ->
            when (event.error) {
                is TextHelper.Exception -> {
                    if (event.error.error is NullOrEmptyOutputData)
                        state.copy(
                            isLoading = false,
                            error = TextHelper.DynamicString("No Data Found!")
                        )
                    else
                        state.copy(
                            isLoading = false,
                            error = event.error
                        )
                }

                else ->
                    state.copy(
                        isLoading = false,
                        error = event.error
                    )
            }
    }

    private fun getItems(categoryId: Int) = viewModelScope.launch {
        val response = fetchItems(categoryId)

        if (response.error != null) {
            state.handleEvent(ItemsEvent.ErrorLoading(response.error))
            return@launch
        }

        if (response.data.isNullOrEmpty()) {
            state.handleEvent(
                ItemsEvent.ErrorLoading(TextHelper.Exception(NullOrEmptyOutputData))
            )
            return@launch
        }

        state.handleEvent(ItemsEvent.ItemsLoaded(response.data))
    }

    private fun updateItem(item: Item) = viewModelScope.launch {
        val response = upsertItem(item)

        if (response.error != null) {
            state.handleEvent(ItemsEvent.ErrorLoading(response.error))
            return@launch
        }

        if (response.data == null) {
            state.handleEvent(
                ItemsEvent.ErrorLoading(TextHelper.Exception(NullOrEmptyOutputData))
            )
            return@launch
        }

        getTotalBudget(categoryId)
        state.handleEvent(ItemsEvent.ItemUpdated(response.data))
    }

    private fun getTotalBudget(categoryId: Int) = viewModelScope.launch {
        val response = fetchItems(categoryId, isSelected = true)

        if (response.error != null) {
            state.handleEvent(ItemsEvent.ErrorLoading(response.error))
            return@launch
        }

        if (response.data.isNullOrEmpty()) {
            state.handleEvent(
                ItemsEvent.ErrorLoading(TextHelper.Exception(NullOrEmptyOutputData))
            )
            return@launch
        }

        val totalBudget = response.data.map { it.avgBudget }.sumOf { it ?: 0 }

        state.handleEvent(ItemsEvent.TotalBudgetLoaded(totalBudget))
    }
}