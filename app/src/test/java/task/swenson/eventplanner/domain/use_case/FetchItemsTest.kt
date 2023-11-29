package task.swenson.eventplanner.domain.use_case

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import task.swenson.eventplanner.data.pojos.Item
import task.swenson.eventplanner.domain.repository.IEventsRepository
import task.swenson.eventplanner.domain.util.Resource

@OptIn(ExperimentalCoroutinesApi::class)
class FetchItemsTest {

    private lateinit var fetchItems: FetchItems
    private val repo: IEventsRepository = mock()

    @Before
    fun setUp() {
        fetchItems = FetchItems(repo)
    }

    @Test
    fun `If category id isn't valid, return error`() = runTest {
        val categoryId = -1

        val stubbedResponse: Resource<List<Item>?> = Resource.Success(listOf(Item()))
        Mockito.`when`(repo.fetchItems(categoryId)).thenReturn(stubbedResponse)

        val result: Resource<List<Item>?> = fetchItems(categoryId)

        assertThat(result.data).isNull()
        assertThat(result.message).isNotNull()
    }

    @Test
    fun `If items retrieval successful, return item list`() = runTest {
        val stubbedResponse: Resource<List<Item>?> = Resource.Success(
            listOf(
                Item(
                    id = 1,
                    title = "Item",
                    imageUrl = "www.google.com",
                    minBudget = 10,
                    maxBudget = 100,
                    avgBudget = 50
                )
            )
        )
        val categoryId = 1

        Mockito.`when`(repo.fetchItems(categoryId)).thenReturn(stubbedResponse)

        val result: Resource<List<Item>?> = fetchItems(categoryId)

        assertThat(result.data).isNotNull()
        assertThat(result.data).isNotEmpty()
    }

    @Test
    fun `If items are null, return error`() = runTest {
        val stubbedResponse: Resource<List<Item>?> = Resource.Success(data = null)
        val categoryId = 1

        Mockito.`when`(repo.fetchItems(categoryId)).thenReturn(stubbedResponse)

        val result = fetchItems(categoryId)

        assertThat(result.message).isNotNull()
    }

    @Test
    fun `If items are empty, return error`() = runTest {
        val stubbedResponse: Resource<List<Item>?> = Resource.Success(data = emptyList())
        val categoryId = 1

        Mockito.`when`(repo.fetchItems(categoryId)).thenReturn(stubbedResponse)

        val result = fetchItems(categoryId)

        assertThat(result.message).isNotNull()
    }

    @Test
    fun `If items data in list are null or empty, return error`() = runTest {
        val stubbedResponse: Resource<List<Item>?> = Resource.Success(listOf(Item()))
        val categoryId = 1

        Mockito.`when`(repo.fetchItems(categoryId)).thenReturn(stubbedResponse)

        val result = fetchItems(categoryId)

        assertThat(result.message).isNotNull()
    }
}