package task.swenson.eventplanner.domain.use_case

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import task.swenson.eventplanner.data.pojos.Item
import task.swenson.eventplanner.domain.repository.IEventsRepository
import task.swenson.eventplanner.domain.use_case.util.getItemList
import task.swenson.eventplanner.domain.util.InvalidCategoryId
import task.swenson.eventplanner.domain.util.NoInputProvided
import task.swenson.eventplanner.domain.util.NullOrEmptyOutputData
import task.swenson.eventplanner.domain.util.Resource
import task.swenson.eventplanner.domain.util.TextHelper

@OptIn(ExperimentalCoroutinesApi::class)
class FetchItemsTest {

    private lateinit var fetchItems: FetchItems
    private val repo: IEventsRepository = mock()

    @Before
    fun setUp() {
        fetchItems = FetchItems(repo)
    }

    /**
     * Verify that the proper methods are called given the right input
     */

    @Test
    fun `Given category id and unselected items, verify fetchItems is called`() = runTest {
        val stubbedResponse: Resource<List<Item>?> = Resource.Success(emptyList())
        val categoryId = 1

        Mockito.`when`(repo.fetchItems(categoryId)).thenReturn(stubbedResponse)

        fetchItems(categoryId)

        verify(repo, times(1)).fetchItems(categoryId)
    }

    @Test
    fun `Given category id and selected items, verify fetchItems is called`() = runTest {
        val stubbedResponse: Resource<List<Item>?> = Resource.Success(emptyList())
        val categoryId = 1

        Mockito.`when`(repo.fetchItems(categoryId)).thenReturn(stubbedResponse)

        fetchItems(categoryId, true)

        verify(repo, times(1)).fetchItems(categoryId)
    }

    @Test
    fun `Given no category id and selected items, verify fetchSelectedItems is called`() = runTest {
        val stubbedResponse: Resource<List<Item>?> = Resource.Success(emptyList())
        Mockito.`when`(repo.fetchSelectedItems()).thenReturn(stubbedResponse)

        fetchItems(isSelected = true)

        verify(repo, times(1)).fetchSelectedItems()
    }

    /**
     * Test input validity
     */

    @Test
    fun `Given no category id and unselected items, return error`() = runTest {
        val result = fetchItems()

        assertThat(result.message).isEqualTo(TextHelper.Exception(NoInputProvided))
    }

    @Test
    fun `If category id isn't valid, return error`() = runTest {
        val stubbedResponse: Resource<List<Item>?> = Resource.Success(listOf(Item()))
        val categoryId = -1

        Mockito.`when`(repo.fetchItems(categoryId)).thenReturn(stubbedResponse)

        val result: Resource<List<Item>?> = fetchItems(categoryId)

        assertThat(result.message).isEqualTo(TextHelper.Exception(InvalidCategoryId))
    }

    /**
     * Test output validity for all cases of output
     */

    @Test
    fun `Given category id and unselected items, return all category items`() = runTest {
        val stubbedResponse: Resource<List<Item>?> = Resource.Success(getItemList())
        val categoryId = 1

        Mockito.`when`(repo.fetchItems(categoryId)).thenReturn(stubbedResponse)

        val result: Resource<List<Item>?> = fetchItems(categoryId)

        assertThat(result.data?.size).isEqualTo(stubbedResponse.data?.size)
    }

    @Test
    fun `Given category id and selected items, return only category selected items`() = runTest {
        val stubbedResponse: Resource<List<Item>?> = Resource.Success(getItemList())
        val categoryId = 1

        Mockito.`when`(repo.fetchItems(categoryId)).thenReturn(stubbedResponse)

        val result: Resource<List<Item>?> = fetchItems(
            categoryId = categoryId,
            isSelected = true
        )

        assertThat(
            result.data
        ).isEqualTo(
            stubbedResponse.data?.filter {
                it.selectedFromCategoryId == categoryId && it.isSelected
            }
        )
    }

    @Test
    fun `Given no category id and selected items, return all selected items`() = runTest {
        val stubbedResponse: Resource<List<Item>?> =
            Resource.Success(getItemList().filter { it.isSelected })

        Mockito.`when`(repo.fetchSelectedItems()).thenReturn(stubbedResponse)

        val result = fetchItems(isSelected = true)

        assertThat(result.data).isEqualTo(
            stubbedResponse.data?.filter { it.isSelected }
        )
    }

    @Test
    fun `If category items are null, return error`() = runTest {
        val stubbedResponse: Resource<List<Item>?> = Resource.Success(data = null)
        val categoryId = 1

        Mockito.`when`(repo.fetchItems(categoryId)).thenReturn(stubbedResponse)

        val result = fetchItems(categoryId)

        assertThat(result.message).isEqualTo(TextHelper.Exception(NullOrEmptyOutputData))
    }

    @Test
    fun `If category items are empty, return error`() = runTest {
        val stubbedResponse: Resource<List<Item>?> = Resource.Success(data = emptyList())
        val categoryId = 1

        Mockito.`when`(repo.fetchItems(categoryId)).thenReturn(stubbedResponse)

        val result = fetchItems(categoryId)

        assertThat(result.message).isEqualTo(TextHelper.Exception(NullOrEmptyOutputData))
    }

    @Test
    fun `If selected category items are null, return error`() = runTest {
        val stubbedResponse: Resource<List<Item>?> = Resource.Success(data = null)
        val categoryId = 1

        Mockito.`when`(repo.fetchItems(categoryId)).thenReturn(stubbedResponse)

        val result = fetchItems(categoryId, true)

        assertThat(result.message).isEqualTo(TextHelper.Exception(NullOrEmptyOutputData))
    }

    @Test
    fun `If selected category items are empty, return error`() = runTest {
        val stubbedResponse: Resource<List<Item>?> = Resource.Success(data = emptyList())
        val categoryId = 1

        Mockito.`when`(repo.fetchItems(categoryId)).thenReturn(stubbedResponse)

        val result = fetchItems(categoryId, true)

        assertThat(result.message).isEqualTo(TextHelper.Exception(NullOrEmptyOutputData))
    }

    @Test
    fun `If selected items are null, return error`() = runTest {
        val stubbedResponse: Resource<List<Item>?> = Resource.Success(data = null)
        Mockito.`when`(repo.fetchSelectedItems()).thenReturn(stubbedResponse)

        val result = fetchItems(isSelected = true)

        assertThat(result.message).isEqualTo(TextHelper.Exception(NullOrEmptyOutputData))
    }

    @Test
    fun `If selected items are empty, return error`() = runTest {
        val stubbedResponse: Resource<List<Item>?> = Resource.Success(data = emptyList())
        Mockito.`when`(repo.fetchSelectedItems()).thenReturn(stubbedResponse)

        val result = fetchItems(isSelected = true)

        assertThat(result.message).isEqualTo(TextHelper.Exception(NullOrEmptyOutputData))
    }

    @Test
    fun `If category items data in list are null or empty, return error`() = runTest {
        val stubbedResponse: Resource<List<Item>?> = Resource.Success(listOf(Item()))
        val categoryId = 1

        Mockito.`when`(repo.fetchItems(categoryId)).thenReturn(stubbedResponse)

        val result = fetchItems(categoryId)

        assertThat(result.message).isEqualTo(TextHelper.Exception(NullOrEmptyOutputData))
    }

    @Test
    fun `If selected category items data in list are null or empty, return error`() = runTest {
        val stubbedResponse: Resource<List<Item>?> = Resource.Success(listOf(Item()))
        val categoryId = 1

        Mockito.`when`(repo.fetchItems(categoryId)).thenReturn(stubbedResponse)

        val result = fetchItems(categoryId, true)

        assertThat(result.message).isEqualTo(TextHelper.Exception(NullOrEmptyOutputData))
    }

    @Test
    fun `If selected items data in list are null or empty, return error`() = runTest {
        val stubbedResponse: Resource<List<Item>?> = Resource.Success(listOf(Item()))
        Mockito.`when`(repo.fetchSelectedItems()).thenReturn(stubbedResponse)

        val result = fetchItems(isSelected = true)

        assertThat(result.message).isEqualTo(TextHelper.Exception(NullOrEmptyOutputData))
    }
}