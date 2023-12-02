package task.swenson.eventplanner.presentation.screens.items

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.BUFFERED
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import task.swenson.eventplanner.data.pojos.Item
import task.swenson.eventplanner.domain.use_case.DeleteItem
import task.swenson.eventplanner.domain.use_case.FetchItems
import task.swenson.eventplanner.domain.use_case.ToggleItemSelection
import task.swenson.eventplanner.domain.use_case.UpsertItem
import task.swenson.eventplanner.domain.util.NullOrEmptyOutputData
import task.swenson.eventplanner.domain.util.TextHelper
import task.swenson.eventplanner.util.StateReducerFlow
import javax.inject.Inject

@HiltViewModel
class ItemsViewModel @Inject constructor(
    private val fetchItems: FetchItems,
    private val upsertItem: UpsertItem,
    private val deleteItem: DeleteItem,
    private val toggleItemSelection: ToggleItemSelection
) : ViewModel() {

    val state = StateReducerFlow(
        initialState = ItemsState(),
        reduceState = ::reduceState
    )

    private val sideEffectsChannel = Channel<ItemsSideEffects>(BUFFERED)
    val sideEffects = sideEffectsChannel.receiveAsFlow()

    private var categoryId: Int = -1

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
            state.copy(isLoading = true, items = event.items)
        }

        is ItemsEvent.TotalBudgetLoaded ->
            state.copy(
                isLoading = false,
                minBudget = event.minBudget,
                maxBudget = event.maxBudget
            )

        is ItemsEvent.ItemClicked -> {
            toggleItem(event.item, categoryId)
            state.copy(isLoading = true)
        }

        is ItemsEvent.ItemUpdated -> {
            val items = state.items.toMutableList()
            val oldItem = items.find { it.id == event.item.id }
            val index = items.indexOf(oldItem)

            items.remove(oldItem)
            items.add(index, event.item)

            getTotalBudget(categoryId)
            state.copy(isLoading = true, items = items)
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

    private fun toggleItem(item: Item, categoryId: Int) = viewModelScope.launch {
        val response = toggleItemSelection(item = item, categoryId = categoryId)

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

        if (item.isSelected)
            delete(item = response.data)
        else
            update(item = response.data)
    }

    private fun update(item: Item) = viewModelScope.launch {
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

        state.handleEvent(ItemsEvent.ItemUpdated(response.data))
    }

    private fun delete(item: Item) = viewModelScope.launch {
        val response = deleteItem(item)

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

        state.handleEvent(ItemsEvent.ItemUpdated(response.data))
    }

    private fun getTotalBudget(categoryId: Int) = viewModelScope.launch {
        val response = fetchItems(categoryId, isSelected = true)

        if (response.error != null) {
            state.handleEvent(ItemsEvent.TotalBudgetLoaded())
            state.handleEvent(ItemsEvent.ErrorLoading(response.error))
            return@launch
        }

        if (response.data.isNullOrEmpty()) {
            state.handleEvent(ItemsEvent.TotalBudgetLoaded())
            state.handleEvent(
                ItemsEvent.ErrorLoading(TextHelper.Exception(NullOrEmptyOutputData))
            )
            return@launch
        }

        val minBudget = response.data.sumOf { it.minBudget ?: 0 }
        val maxBudget = response.data.sumOf { it.maxBudget ?: 0 }

        state.handleEvent(ItemsEvent.TotalBudgetLoaded(minBudget, maxBudget))
    }
}