package task.swenson.eventplanner.presentation.screens.items

sealed class ItemsSideEffects {

    data class ShowToast(val message: String): ItemsSideEffects()
}
