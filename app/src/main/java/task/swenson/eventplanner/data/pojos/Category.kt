package task.swenson.eventplanner.data.pojos


import com.google.gson.annotations.SerializedName

class Category(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("image")
    val imageUrl: String? = null,
    @SerializedName("title")
    val title: String? = null,

    val selectedItems: Int? = null
)