package task.swenson.eventplanner.presentation.screens.builder

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import kotlinx.coroutines.flow.Flow
import task.swenson.eventplanner.R
import task.swenson.eventplanner.presentation.components.Budget
import task.swenson.eventplanner.presentation.components.CategoryCard
import task.swenson.eventplanner.presentation.components.Header
import task.swenson.eventplanner.presentation.screens.error.ErrorScreen
import task.swenson.eventplanner.presentation.theme.BreakerBay
import task.swenson.eventplanner.presentation.theme.MiddleGray
import task.swenson.eventplanner.presentation.theme.Shape
import task.swenson.eventplanner.presentation.util.OnLifecycleEvent

@Composable
fun BuilderScreen(
    vm: BuilderViewModel = hiltViewModel(),
    onNavigateToList: (categoryId: Int, categoryName: String) -> Unit,
    onNavigateToTotalBudget: (budget: Int) -> Unit
) {
    val state by vm.state.collectAsState()

    LaunchEffects(vm.sideEffects, onNavigateToList, onNavigateToTotalBudget)

    OnLifecycleEvent { _, event ->
        when (event) {
            Lifecycle.Event.ON_START -> {
                vm.state.handleEvent(BuilderEvent.ScreenStarted)
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

        if (!state.isLoading)
            if (state.error != null && state.categories.isEmpty())
                ErrorScreen(
                    modifier = Modifier.weight(1f),
                    error = state.error
                )
            else
                CategoriesList(vm, state)
    }
}

@Composable
fun CategoriesList(
    vm: BuilderViewModel,
    state: BuilderState
) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Header(
            title = stringResource(id = R.string.title_event_builder),
            subTitle = stringResource(id = R.string.subtitle_event_builder),
            showBackButton = false
        )

        Budget(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            avgBudget = state.totalBudget
        )

        Spacer(modifier = Modifier.size(24.dp))

        LazyVerticalGrid(
            modifier = Modifier.weight(1f),
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(state.categories.size) {
                CategoryCard(
                    modifier = Modifier,
                    category = state.categories[it],
                    handleEvent = { _ ->
                        vm.state.handleEvent(BuilderEvent.CategoryClicked(state.categories[it]))
                    }
                )
            }
        }

        Spacer(modifier = Modifier.size(16.dp))

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            enabled = state.totalBudget != null && state.totalBudget > 0,
            shape = Shape.medium,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = BreakerBay
            ),
            onClick = {
                vm.state.handleEvent(
                    BuilderEvent.SaveClicked(state.totalBudget ?: 0)
                )
            }
        ) {
            Text(
                text = stringResource(R.string.btn_save),
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = if (state.totalBudget != null && state.totalBudget > 0)
                    Color.White
                else
                    MiddleGray,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun LaunchEffects(
    sideEffects: Flow<BuilderSideEffects>,
    onNavigateToList: (categoryId: Int, categoryName: String) -> Unit,
    onNavigateToTotalBudget: (budget: Int) -> Unit
) {
    val context = LocalContext.current

    LaunchedEffect(key1 = context) {
        sideEffects.collect { effect ->
            when (effect) {
                is BuilderSideEffects.NavigateToItemsList ->
                    onNavigateToList(effect.categoryId, effect.categoryName)

                is BuilderSideEffects.NavigateToTotalBudget ->
                    onNavigateToTotalBudget(effect.totalBudget)
            }
        }
    }
}