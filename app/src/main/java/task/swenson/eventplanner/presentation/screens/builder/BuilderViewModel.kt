package task.swenson.eventplanner.presentation.screens.builder

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.BUFFERED
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import task.swenson.eventplanner.data.pojos.Category
import task.swenson.eventplanner.domain.use_case.FetchCategories
import task.swenson.eventplanner.domain.use_case.FetchItems
import task.swenson.eventplanner.domain.use_case.SumItemsCost
import task.swenson.eventplanner.domain.util.NullOrEmptyOutputData
import task.swenson.eventplanner.domain.util.TextHelper
import task.swenson.eventplanner.util.StateReducerFlow
import javax.inject.Inject

@HiltViewModel
class BuilderViewModel @Inject constructor(
    private val fetchCategories: FetchCategories,
    private val fetchItems: FetchItems,
    private val sumItemsCost: SumItemsCost
) : ViewModel() {

    val state = StateReducerFlow(
        initialState = BuilderState(),
        reduceState = ::reduceState
    )

    private val sideEffectsChannel = Channel<BuilderSideEffects>(BUFFERED)
    val sideEffects = sideEffectsChannel.receiveAsFlow()

    private fun reduceState(
        state: BuilderState,
        event: BuilderEvent
    ): BuilderState = when (event) {
        is BuilderEvent.ScreenStarted -> {
            getCategories()
            state.copy(isLoading = true)
        }

        is BuilderEvent.CategoriesLoaded -> {
            getTotalBudget()
            state.copy(isLoading = true, categories = event.categories)
        }

        is BuilderEvent.TotalBudgetLoaded ->
            state.copy(isLoading = false, totalBudget = event.totalBudget)

        is BuilderEvent.CategoryClicked -> {
            sendEffect(event.category)
            state
        }

        is BuilderEvent.SaveClicked -> {
            viewModelScope.launch {
                sideEffectsChannel.send(
                    BuilderSideEffects.NavigateToTotalBudget(event.totalBudget)
                )
            }

            state
        }

        is BuilderEvent.ErrorLoading ->
            when (event.error) {
                is TextHelper.Exception -> {
                    if (event.error.error is NullOrEmptyOutputData)
                        state.copy(
                            isLoading = false,
                            categories = state.categories,
                            error = TextHelper.DynamicString("No Data Found!")
                        )
                    else
                        state.copy(
                            isLoading = false,
                            categories = state.categories,
                            error = event.error
                        )
                }

                else ->
                    state.copy(
                        isLoading = false,
                        categories = state.categories,
                        error = event.error
                    )
            }
    }

    private fun getCategories() = viewModelScope.launch {
        val response = fetchCategories()

        if (response.error != null) {
            state.handleEvent(BuilderEvent.ErrorLoading(response.error))
            return@launch
        }

        if (response.data.isNullOrEmpty()) {
            state.handleEvent(
                BuilderEvent.ErrorLoading(TextHelper.Exception(NullOrEmptyOutputData))
            )
            return@launch
        }

        val selectedItems = fetchItems(isSelected = true)

        if (selectedItems.error != null) {
            state.handleEvent(BuilderEvent.ErrorLoading(selectedItems.error))
            return@launch
        }

        val updatedCategories: List<Category> = response.data.map { category ->
            var accumulator = 0

            selectedItems.data?.forEach {
                if (it.selectedFromCategoryId == category.id)
                    accumulator++
            }

            return@map Category(
                id = category.id,
                title = category.title,
                imageUrl = category.imageUrl,
                selectedItems = accumulator
            )
        }

        state.handleEvent(BuilderEvent.CategoriesLoaded(updatedCategories))
    }

    private fun getTotalBudget() = viewModelScope.launch {
        val response = fetchItems(isSelected = true)

        if (response.error != null) {
            state.handleEvent(BuilderEvent.TotalBudgetLoaded())
            state.handleEvent(BuilderEvent.ErrorLoading(response.error))
            return@launch
        }

        if (response.data.isNullOrEmpty()) {
            state.handleEvent(BuilderEvent.TotalBudgetLoaded())
            state.handleEvent(
                BuilderEvent.ErrorLoading(TextHelper.Exception(NullOrEmptyOutputData))
            )
            return@launch
        }

        val totalBudget = sumItemsCost(response.data)

        if (totalBudget.error != null) {
            state.handleEvent(BuilderEvent.ErrorLoading(totalBudget.error))
            return@launch
        }

        state.handleEvent(BuilderEvent.TotalBudgetLoaded(totalBudget.data))
    }

    private fun sendEffect(category: Category) = viewModelScope.launch {
        category.id?.let {
            sideEffectsChannel.send(
                BuilderSideEffects.NavigateToItemsList(
                    it,
                    category.title ?: ""
                )
            )
        }
    }
}