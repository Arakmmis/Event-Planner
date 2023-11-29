package task.swenson.eventplanner.data.remote

object Url {

    const val BASE_URL = "https://swensonhe-dev-challenge.s3.us-west-2.amazonaws.com/"

    const val PATH_CATEGORY_ID = "id"

    const val URL_CATEGORIES = "categories.json"
    const val URL_ITEMS = "categories/{$PATH_CATEGORY_ID}.json"
}