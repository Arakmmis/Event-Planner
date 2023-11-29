package task.swenson.eventplanner.data.pojos


import com.google.gson.annotations.SerializedName

data class Item(
    @SerializedName("avgBudget")
    val avgBudget: Int? = null,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("image")
    val imageUrl: String? = null,
    @SerializedName("maxBudget")
    val maxBudget: Int? = null,
    @SerializedName("minBudget")
    val minBudget: Int? = null,
    @SerializedName("title")
    val title: String? = null,

    val isSelected: Boolean = false
)