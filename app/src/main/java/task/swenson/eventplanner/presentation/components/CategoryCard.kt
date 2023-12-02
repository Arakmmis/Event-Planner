package task.swenson.eventplanner.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import task.swenson.eventplanner.R
import task.swenson.eventplanner.data.pojos.Category
import task.swenson.eventplanner.presentation.screens.builder.BuilderEvent
import task.swenson.eventplanner.presentation.theme.BreakerBay
import task.swenson.eventplanner.presentation.theme.DarkGray
import task.swenson.eventplanner.presentation.theme.Shape

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CategoryCard(
    modifier: Modifier = Modifier,
    category: Category,
    handleEvent: (BuilderEvent) -> Unit
) {
    Card(
        modifier = modifier
            .shadow(
                elevation = 2.dp,
                shape = Shape.medium
            ),
        backgroundColor = Color.White,
        shape = Shape.medium,
        border = if (category.selectedItems > 0)
            BorderStroke(width = 2.dp, color = BreakerBay)
        else
            BorderStroke(width = 0.dp, color = BreakerBay),
        onClick = {
            handleEvent(BuilderEvent.CategoryClicked(category))
        }
    ) {
        Column {
            Box(
                modifier = Modifier.wrapContentWidth(),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    modifier = Modifier.height(120.dp),
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(category.imageUrl)
                        .crossfade(true)
                        .build(),
                    placeholder = painterResource(R.drawable.placeholder),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )

                if (category.selectedItems > 0) {
                    Text(
                        modifier = Modifier
                            .padding(8.dp)
                            .align(Alignment.TopEnd)
                            .border(
                                width = 0.5.dp,
                                color = Color.White,
                                shape = RoundedCornerShape(16.dp)
                            )
                            .background(
                                color = BreakerBay,
                                shape = RoundedCornerShape(16.dp)
                            )
                            .size(28.dp)
                            .padding(4.dp),
                        text = String.format("%02d", category.selectedItems),
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Light
                    )
                }
            }

            Row(
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .fillMaxWidth()
                    .padding(
                        horizontal = 14.dp,
                        vertical = 8.dp
                    ),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = category.title ?: "",
                    color = DarkGray,
                    fontSize = 14.sp
                )

                Icon(
                    modifier = Modifier.size(14.dp),
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_arrow),
                    contentDescription = null,
                    tint = BreakerBay
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CategoryCardPreview() {
    CategoryCard(
        modifier = Modifier
            .width(200.dp),
        category = Category(
            id = 1,
            title = "Staff",
            imageUrl = "",
            selectedItems = 99
        ),
    ) {
        // Do Nothing
    }
}