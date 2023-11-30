package task.swenson.eventplanner.domain.use_case

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import task.swenson.eventplanner.data.pojos.Item
import task.swenson.eventplanner.domain.repository.IEventsRepository
import task.swenson.eventplanner.domain.use_case.util.getItemList
import task.swenson.eventplanner.domain.util.DeletionFailure
import task.swenson.eventplanner.domain.util.InvalidItem
import task.swenson.eventplanner.domain.util.Resource
import task.swenson.eventplanner.domain.util.TextHelper

@OptIn(ExperimentalCoroutinesApi::class)
class DeleteItemTest {

    private lateinit var deleteItem: DeleteItem
    private val repo: IEventsRepository = mock()

    @Before
    fun setUp() {
        deleteItem = DeleteItem(repo)
    }

    @Test
    fun `Given one item, delete it and return it`() = runTest {
        val item = getItemList()[0]
        val stubbedResponse = Resource.Success(data = item)

        `when`(repo.deleteItem(item)).thenReturn(stubbedResponse)

        val result = deleteItem(item)

        assertThat(result.data).isNotNull()
    }

    @Test
    fun `If item is null, return error`() = runTest {
        assertThat(deleteItem(null).error).isEqualTo(TextHelper.Exception(InvalidItem))
    }

    @Test
    fun `If item is invalid, return error`() = runTest {
        assertThat(deleteItem(Item()).error).isEqualTo(TextHelper.Exception(InvalidItem))
    }

    @Test
    fun `If deletion fails, return error`() = runTest {
        val item = getItemList()[0]
        val stubbedResponse = Resource.Error<Item>(
            error = TextHelper.Exception(DeletionFailure)
        )

        `when`(repo.deleteItem(item)).thenReturn(stubbedResponse)

        val result = deleteItem(item)

        assertThat(result.error).isEqualTo(
            TextHelper.Exception(DeletionFailure)
        )
    }
}