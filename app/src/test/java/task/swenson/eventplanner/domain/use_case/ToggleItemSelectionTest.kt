package task.swenson.eventplanner.domain.use_case

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import task.swenson.eventplanner.data.pojos.Item
import task.swenson.eventplanner.domain.use_case.util.notSelectedItem
import task.swenson.eventplanner.domain.use_case.util.selectedItem
import task.swenson.eventplanner.domain.util.InvalidCategoryId
import task.swenson.eventplanner.domain.util.InvalidItem
import task.swenson.eventplanner.domain.util.TextHelper

class ToggleItemSelectionTest {

    private val toggleSelection = ToggleItemSelection()

    @Test
    fun `If item is invalid, return error`() {
        val item = Item()

        val result = toggleSelection(item, 1)

        assertThat(result.error).isEqualTo(TextHelper.Exception(InvalidItem))
    }

    @Test
    fun `If item isn't selected and category id is invalid, return error`() {
        val item = notSelectedItem()
        val categoryId = -1

        val result = toggleSelection(item, categoryId)

        assertThat(result.error).isEqualTo(TextHelper.Exception(InvalidCategoryId))
    }

    @Test
    fun `If item isn't selected and category id is null, return error`() {
        val item = notSelectedItem()

        val result = toggleSelection(item)

        assertThat(result.error).isEqualTo(TextHelper.Exception(InvalidCategoryId))
    }

    @Test
    fun `If item is selected, return item unselected`() {
        val item = selectedItem()

        val result = toggleSelection(item)

        assertThat(result.data?.isSelected).isFalse()
    }

    @Test
    fun `If item is selected, return item without category id`() {
        val item = selectedItem()
        val categoryId = 1

        val result = toggleSelection(item, categoryId)

        assertThat(result.data?.selectedFromCategoryId).isNull()
    }

    @Test
    fun `If item isn't selected, return item selected`() {
        val item = notSelectedItem()
        val categoryId = 1

        val result = toggleSelection(item, categoryId)

        assertThat(result.data?.isSelected).isTrue()
    }

    @Test
    fun `If item isn't selected, return item with category id`() {
        val item = notSelectedItem()
        val categoryId = 1

        val result = toggleSelection(item, categoryId)

        assertThat(result.data?.selectedFromCategoryId).isEqualTo(categoryId)
    }
}