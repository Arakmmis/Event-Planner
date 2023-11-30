package task.swenson.eventplanner.domain.use_case

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import task.swenson.eventplanner.data.pojos.Category
import task.swenson.eventplanner.domain.repository.IEventsRepository
import task.swenson.eventplanner.domain.util.Resource

@OptIn(ExperimentalCoroutinesApi::class)
class FetchCategoriesTest {

    private lateinit var fetchCategories: FetchCategories
    private val repo: IEventsRepository = mock()

    @Before
    fun setUp() {
        fetchCategories = FetchCategories(repo)
    }

    @Test
    fun `If categories retrieval successful, return category list`() = runTest {
        val stubbedResponse: Resource<List<Category>?> = Resource.Success(
            listOf(
                Category(
                    id = 1,
                    title = "Category 1",
                    imageUrl = "www.google.com"
                )
            )
        )
        `when`(repo.fetchCategories()).thenReturn(stubbedResponse)

        val result = fetchCategories()

        assertThat(result.data).isNotNull()
        assertThat(result.data).isNotEmpty()
    }

    @Test
    fun `If categories are null, return error`() = runTest {
        val stubbedResponse: Resource<List<Category>?> = Resource.Success(data = null)

        `when`(repo.fetchCategories()).thenReturn(stubbedResponse)

        val result = fetchCategories()

        assertThat(result.error).isNotNull()
    }

    @Test
    fun `If categories are empty, return error`() = runTest {
        val stubbedResponse: Resource<List<Category>?> = Resource.Success(data = emptyList())

        `when`(repo.fetchCategories()).thenReturn(stubbedResponse)

        val result = fetchCategories()

        assertThat(result.error).isNotNull()
    }

    @Test
    fun `If category data in list are null or empty, return error`() = runTest {
        val stubbedResponse: Resource<List<Category>?> = Resource.Success(listOf(Category()))
        `when`(repo.fetchCategories()).thenReturn(stubbedResponse)

        val result = fetchCategories()

        assertThat(result.error).isNotNull()
    }
}