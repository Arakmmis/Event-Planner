package task.swenson.eventplanner.data.pojos

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import task.swenson.eventplanner.data.local.DbConfig

@Entity(tableName = DbConfig.ITEMS_TABLE_NAME)
data class Item(
    @PrimaryKey
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val imageUrl: String? = null,
    @SerializedName("maxBudget")
    val maxBudget: Int? = null,
    @SerializedName("minBudget")
    val minBudget: Int? = null,
    @SerializedName("avgBudget")
    val avgBudget: Int? = null,
    @SerializedName("title")
    val title: String? = null,

    val isSelected: Boolean = false,
    val selectedFromCategoryId: Int? = null
)