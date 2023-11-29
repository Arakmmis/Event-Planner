package task.swenson.eventplanner.domain.use_case

import task.swenson.eventplanner.data.pojos.Item
import task.swenson.eventplanner.domain.util.Resource

class SumItemsCost {

    operator fun invoke(items: List<Item>): Resource<Int> =
        Resource.Success(data = items.toSet().sumOf { it.avgBudget ?: 0 })
}