package task.swenson.eventplanner.domain.use_case

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import task.swenson.eventplanner.data.pojos.Category
import task.swenson.eventplanner.domain.util.Resource
import task.swenson.eventplanner.domain.util.TextHelper

@OptIn(ExperimentalCoroutinesApi::class)
class FetchCategoriesTest {

    private val fetchCategories: FetchCategories = mock()

    @Test
    fun `If categories successful, return category list`() = runTest {
        val stubbedResponse: Resource<List<Category>?> = Resource.Success(listOf(Category()))
        `when`(fetchCategories()).thenReturn(stubbedResponse)

        val result = fetchCategories()

        assertThat(result.data).isNotNull()
        assertThat(result.data).isNotEmpty()
    }

    @Test
    fun `If categories are empty or null, return empty error`() = runTest {
        val errorMsg = "Category list is empty"
        val stubbedResponse: Resource<List<Category>?> = Resource.Error(
            message = TextHelper.Exception(
                IllegalStateException(errorMsg)
            ),
            data = null
        )
        `when`(fetchCategories()).thenReturn(stubbedResponse)

        val result = fetchCategories()

        assertThat(result.message?.asString()).isEqualTo(errorMsg)
    }
}