package task.swenson.eventplanner.domain.util

import android.content.Context

sealed class TextHelper {

    data class Exception(val error: kotlin.Exception) : TextHelper()

    data class DynamicString(val value: String) : TextHelper()

    class StringRes(
        @androidx.annotation.StringRes val resId: Int,
        vararg val args: Any
    ) : TextHelper()

    fun asString(context: Context? = null): String {
        return when (this) {
            is Exception -> error.localizedMessage ?: ""

            is DynamicString -> value

            is StringRes -> context?.getString(resId, *args) ?: ""
        }
    }
}
