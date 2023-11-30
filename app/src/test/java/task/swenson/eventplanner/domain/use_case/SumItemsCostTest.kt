package task.swenson.eventplanner.domain.use_case

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import task.swenson.eventplanner.domain.use_case.util.getItemList

class SumItemsCostTest {

    private val sumCost = SumItemsCost()

    @Test
    fun `Given items, return the sum of their avgBudget`() {
        val items = getItemList()
        val cost: Int = items.sumOf { it.avgBudget ?: 0 }

        val result = sumCost(items)

        assertThat(result.data).isEqualTo(cost)
    }

    @Test
    fun `Given items with same id, return sum without duplicates`() {
        val items = getItemList()
        val cost: Int = items.toSet().sumOf { it.avgBudget ?: 0 }

        val result = sumCost(items)

        assertThat(result.data).isEqualTo(cost)
    }
}