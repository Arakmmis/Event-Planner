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
import task.swenson.eventplanner.domain.use_case.util.itemInvalidId
import task.swenson.eventplanner.domain.util.InvalidItem
import task.swenson.eventplanner.domain.util.Resource
import task.swenson.eventplanner.domain.util.TextHelper
import task.swenson.eventplanner.domain.util.UpsertFailure

@OptIn(ExperimentalCoroutinesApi::class)
class UpsertItemTest {

    private lateinit var upsertItem: UpsertItem
    private val repo: IEventsRepository = mock()

    @Before
    fun setUp() {
        upsertItem = UpsertItem(repo)
    }

    @Test
    fun `Given one item, insert or update it and return it`() = runTest {
        val item = getItemList()[0]
        val stubbedResponse = Resource.Success(data = item)

        `when`(repo.upsertItem(item)).thenReturn(stubbedResponse)

        val result = upsertItem(item)

        assertThat(result.data).isNotNull()
    }

    @Test
    fun `If item is null, return error`() = runTest {
        assertThat(upsertItem(null).error).isEqualTo(TextHelper.Exception(InvalidItem))
    }

    @Test
    fun `If item is invalid, return error`() = runTest {
        assertThat(upsertItem(itemInvalidId()).error).isEqualTo(TextHelper.Exception(InvalidItem))
    }

    @Test
    fun `If upserting fails, return error`() = runTest {
        val item = getItemList()[0]
        val stubbedResponse = Resource.Error<Item>(
            error = TextHelper.Exception(UpsertFailure)
        )

        `when`(repo.upsertItem(item)).thenReturn(stubbedResponse)

        val result = upsertItem(item)

        assertThat(result.error).isEqualTo(
            TextHelper.Exception(UpsertFailure)
        )
    }
}