package task.swenson.eventplanner.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import task.swenson.eventplanner.R
import task.swenson.eventplanner.data.pojos.Item
import task.swenson.eventplanner.presentation.theme.DarkGray
import task.swenson.eventplanner.presentation.theme.Shape

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ItemCard(
    modifier: Modifier = Modifier,
    item: Item,
    handleEvent: (item: Item) -> Unit
) {
    Card(
        modifier = modifier
            .shadow(
                elevation = 2.dp,
                shape = Shape.medium
            ),
        backgroundColor = Color.White,
        shape = Shape.medium,
        onClick = {
            handleEvent(item)
        }
    ) {
        Column {
            Box(
                modifier = Modifier.wrapContentWidth()
            ) {
                AsyncImage(
                    modifier = Modifier.height(120.dp),
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(item.imageUrl)
                        .crossfade(true)
                        .build(),
                    placeholder = painterResource(R.drawable.placeholder),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )

                Icon(
                    modifier = Modifier
                        .padding(8.dp)
                        .size(28.dp)
                        .align(Alignment.TopEnd)
                        .shadow(elevation = 5.dp),
                    painter =
                        if (item.isSelected)
                            painterResource(id = R.drawable.ic_selected)
                        else
                            painterResource(id = R.drawable.ic_add)
                    ,
                    contentDescription = null
                )
            }

            Text(
                modifier = Modifier
                    .padding(top = 8.dp, start = 14.dp, bottom = 4.dp, end = 14.dp)
                    .fillMaxWidth(),
                text = item.title ?: "",
                color = DarkGray,
                fontSize = 14.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                modifier = Modifier
                    .padding(horizontal = 14.dp)
                    .padding(bottom = 16.dp)
                    .fillMaxWidth(),
                text = String.format("$%d-$%d", item.minBudget, item.maxBudget),
                color = Color.Black,
                fontSize = 14.sp,
                fontWeight = FontWeight.Black,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ItemCardPreview() {
    ItemCard(
        modifier = Modifier
            .width(200.dp),
        item = Item(
            id = 1,
            title = "Staff",
            imageUrl = "",
            minBudget = 10,
            maxBudget = 1000,
            avgBudget = 500,
            isSelected = true,
            selectedFromCategoryId = 2
        )
    ) {
        // Do Nothing
    }
}