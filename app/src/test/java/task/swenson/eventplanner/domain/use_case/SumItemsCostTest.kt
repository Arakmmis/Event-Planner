package task.swenson.eventplanner.domain.use_case

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import task.swenson.eventplanner.data.pojos.Item

class SumItemsCostTest {

    private val sumCost = SumItemsCost()

    @Test
    fun `Given items, return the sum of their avgBudget`() {
        val items = listOf(
            Item(
                id = 1,
                title = "Item",
                imageUrl = "www.google.com",
                minBudget = 10,
                maxBudget = 100,
                avgBudget = 50
            ),
            Item(
                id = 2,
                title = "Item",
                imageUrl = "www.google.com",
                minBudget = 10,
                maxBudget = 100,
                avgBudget = 50
            ),
            Item(
                id = 3,
                title = "Item",
                imageUrl = "www.google.com",
                minBudget = 10,
                maxBudget = 100,
                avgBudget = 50
            )
        )
        val cost: Int = items.sumOf { it.avgBudget ?: 0 }

        val result = sumCost(items)

        assertThat(result.data).isEqualTo(cost)
    }

    @Test
    fun `Given items with same id, return sum without duplicates`() {
        val items = listOf(
            Item(
                id = 1,
                title = "Item",
                imageUrl = "www.google.com",
                minBudget = 10,
                maxBudget = 100,
                avgBudget = 50
            ),
            Item(
                id = 1,
                title = "Item",
                imageUrl = "www.google.com",
                minBudget = 10,
                maxBudget = 100,
                avgBudget = 50
            ),
            Item(
                id = 3,
                title = "Item",
                imageUrl = "www.google.com",
                minBudget = 10,
                maxBudget = 100,
                avgBudget = 50
            )
        )
        val cost: Int = items.toSet().sumOf { it.avgBudget ?: 0 }

        val result = sumCost(items)

        assertThat(result.data).isEqualTo(cost)
    }
}