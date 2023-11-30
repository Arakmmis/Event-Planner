package task.swenson.eventplanner.domain.use_case

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import task.swenson.eventplanner.domain.use_case.util.getItemList
import task.swenson.eventplanner.domain.use_case.util.itemInvalidId
import task.swenson.eventplanner.domain.use_case.util.itemNoAvgBudget
import task.swenson.eventplanner.domain.use_case.util.itemNoMaxBudget
import task.swenson.eventplanner.domain.use_case.util.itemNoMinBudget
import task.swenson.eventplanner.domain.use_case.util.itemNoTitle
import task.swenson.eventplanner.domain.util.InvalidItem
import task.swenson.eventplanner.domain.util.NullOrEmptyOutputData
import task.swenson.eventplanner.domain.util.TextHelper

class ValidateItemsTest {

    private val validator = ValidateItems()

    @Test
    fun `If item is null, return error`() {
        assertThat(
            validator(item = null).error
        ).isEqualTo(
            TextHelper.Exception(InvalidItem)
        )
    }

    @Test
    fun `If item data is invalid, return error`() {
        assertThat(
            validator(item = itemInvalidId()).error
        ).isEqualTo(
            TextHelper.Exception(InvalidItem)
        )
    }

    @Test
    fun `If item doesn't have id, return error`() {
        assertThat(
            validator(item = itemInvalidId()).error
        ).isEqualTo(
            TextHelper.Exception(InvalidItem)
        )
    }

    @Test
    fun `If item doesn't have title, return error`() {
        assertThat(
            validator(item = itemNoTitle()).error
        ).isEqualTo(
            TextHelper.Exception(InvalidItem)
        )
    }

    @Test
    fun `If item doesn't have min budget, return error`() {
        assertThat(
            validator(item = itemNoMinBudget()).error
        ).isEqualTo(
            TextHelper.Exception(InvalidItem)
        )
    }

    @Test
    fun `If item doesn't have max budget, return error`() {
        assertThat(
            validator(item = itemNoMaxBudget()).error
        ).isEqualTo(
            TextHelper.Exception(InvalidItem)
        )
    }

    @Test
    fun `If item doesn't have avg budget, return error`() {
        assertThat(
            validator(item = itemNoAvgBudget()).error
        ).isEqualTo(
            TextHelper.Exception(InvalidItem)
        )
    }

    @Test
    fun `If item is valid, return item`() {
        assertThat(
            validator(item = getItemList()[0]).data
        ).isNotNull()
    }

    @Test
    fun `If item list is null, return error`() {
        assertThat(
            validator(items = null).error
        ).isEqualTo(
            TextHelper.Exception(NullOrEmptyOutputData)
        )
    }

    @Test
    fun `If item list is empty, return error`() {
        assertThat(
            validator(items = emptyList()).error
        ).isEqualTo(
            TextHelper.Exception(NullOrEmptyOutputData)
        )
    }

    @Test
    fun `If item list contains all invalid items, return error`() {
        assertThat(
            validator(
                items = listOf(
                    itemInvalidId(),
                    itemNoAvgBudget(),
                    itemNoTitle(),
                    itemNoMaxBudget(),
                    itemNoMinBudget()
                )
            ).error
        ).isEqualTo(
            TextHelper.Exception(NullOrEmptyOutputData)
        )
    }

    @Test
    fun `If item list contains some invalid items, return list without the invalid items`() {
        assertThat(
            validator(
                items = getItemList() + itemInvalidId() + itemNoTitle()
            ).data
        ).isEqualTo(getItemList())
    }

    @Test
    fun `If item list is valid, return item list`() {
        assertThat(
            validator(items = getItemList()).data
        ).isEqualTo(getItemList())
    }
}