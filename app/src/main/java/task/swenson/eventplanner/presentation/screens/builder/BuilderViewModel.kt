package task.swenson.eventplanner.presentation.screens.builder

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.BUFFERED
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import task.swenson.eventplanner.domain.use_case.FetchCategories
import task.swenson.eventplanner.domain.use_case.FetchItems
import task.swenson.eventplanner.domain.util.NullOrEmptyOutputData
import task.swenson.eventplanner.domain.util.TextHelper
import task.swenson.eventplanner.util.StateReducerFlow
import javax.inject.Inject

@HiltViewModel
class BuilderViewModel @Inject constructor(
    private val fetchCategories: FetchCategories,
    private val fetchItems: FetchItems
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
            state.copy(isLoading = false, categories = event.categories)
        }

        is BuilderEvent.TotalBudgetLoaded ->
            state.copy(isLoading = false, totalBudget = event.totalBudget)

        is BuilderEvent.CategoryClicked -> {
            viewModelScope.launch {
                event.category.id?.let {
                    sideEffectsChannel.send(BuilderSideEffects.NavigateToItemsList(it))
                }
            }

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

        state.handleEvent(BuilderEvent.CategoriesLoaded(response.data))
    }

    private fun getTotalBudget() = viewModelScope.launch {
        val response = fetchItems(isSelected = true)

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

        val totalBudget = response.data.map { it.avgBudget }.sumOf { it ?: 0 }

        state.handleEvent(BuilderEvent.TotalBudgetLoaded(totalBudget))
    }
}