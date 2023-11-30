package task.swenson.eventplanner.domain.use_case

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import task.swenson.eventplanner.data.pojos.Item

class ToggleItemSelectionTest {

    private val toggleSelection = ToggleItemSelection()

    @Test
    fun `If item is invalid, return error`() {
        val item = Item()

        val result = toggleSelection(item)

        assertThat(result.error).isNotNull()
    }

    @Test
    fun `If item is selected, then unselect it`() {
        val item = Item(
            id = 1,
            title = "Item",
            imageUrl = "www.google.com",
            minBudget = 10,
            maxBudget = 100,
            avgBudget = 50,
            isSelected = true,
            selectedFromCategoryId = 2
        )

        val result = toggleSelection(item)

        assertThat(result.data?.isSelected).isFalse()
    }

    @Test
    fun `If item isn't selected, then select it`() {
        val item = Item(
            id = 1,
            title = "Item",
            imageUrl = "www.google.com",
            minBudget = 10,
            maxBudget = 100,
            avgBudget = 50,
            isSelected = false,
            selectedFromCategoryId = 2
        )

        val result = toggleSelection(item)

        assertThat(result.data?.isSelected).isTrue()
    }
}