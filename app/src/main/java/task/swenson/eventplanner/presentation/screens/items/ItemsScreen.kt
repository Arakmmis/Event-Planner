package task.swenson.eventplanner.presentation.screens.items

import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import kotlinx.coroutines.flow.Flow
import task.swenson.eventplanner.R
import task.swenson.eventplanner.presentation.components.Budget
import task.swenson.eventplanner.presentation.components.Header
import task.swenson.eventplanner.presentation.components.ItemCard
import task.swenson.eventplanner.presentation.screens.error.ErrorScreen
import task.swenson.eventplanner.presentation.theme.BreakerBay
import task.swenson.eventplanner.presentation.util.OnLifecycleEvent

@Composable
fun ItemsScreen(
    vm: ItemsViewModel = hiltViewModel(),
    categoryId: Int,
    categoryName: String,
    onBackClicked: () -> Unit
) {
    val state by vm.state.collectAsState()

    LaunchEffects(vm.sideEffects)

    OnLifecycleEvent { _, event ->
        when (event) {
            Lifecycle.Event.ON_START -> {
                vm.state.handleEvent(ItemsEvent.ScreenStarted(categoryId))
            }

            else -> {}
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        AnimatedVisibility(visible = state.isLoading) {
            LinearProgressIndicator(
                modifier = Modifier.fillMaxWidth(),
                color = BreakerBay
            )
        }

        if (state.error != null && state.items.isEmpty())
            ErrorScreen(
                modifier = Modifier.weight(1f),
                error = state.error
            )
        else
            ItemsList(vm, state, categoryName, onBackClicked)
    }
}

@Composable
fun ItemsList(
    vm: ItemsViewModel,
    state: ItemsState,
    categoryName: String,
    onBackClicked: () -> Unit
) {
    Column {
        Header(
            modifier = Modifier.padding(vertical = 16.dp),
            title = categoryName,
            subTitle = stringResource(id = R.string.subtitle_event_builder),
            showBackButton = true
        ) {
            onBackClicked()
        }

        Budget(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .align(Alignment.CenterHorizontally),
            avgBudget = state.totalBudget
        )

        Spacer(modifier = Modifier.size(24.dp))

        LazyVerticalGrid(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .weight(1f),
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            items(state.items.size) {
                ItemCard(
                    modifier = Modifier,
                    item = state.items[it],
                    handleEvent = { _ ->
                        vm.state.handleEvent(ItemsEvent.ItemClicked(state.items[it]))
                    }
                )
            }
        }
    }
}

@Composable
fun LaunchEffects(
    sideEffects: Flow<ItemsSideEffects>
) {
    val context = LocalContext.current

    LaunchedEffect(key1 = context) {
        sideEffects.collect { effect ->
            when (effect) {
                is ItemsSideEffects.ShowToast ->
                    Toast.makeText(context, effect.message, LENGTH_SHORT).show()
            }
        }
    }
}