package task.swenson.eventplanner.domain.util

data object InvalidCategoryId : Exception()

data object InvalidItem : Exception()

data object NoInputProvided : Exception()

data object NullOrEmptyOutputData : Exception()

data object UpsertFailure : Exception()

data object DeletionFailure : Exception()