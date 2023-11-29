package task.swenson.eventplanner.domain.util

sealed class Resource<T>(val data: T? = null, val message: TextHelper? = null) {
    class Success<T>(data: T?): Resource<T>(data)
    class Error<T>(message: TextHelper, data: T? = null): Resource<T>(data, message)
}