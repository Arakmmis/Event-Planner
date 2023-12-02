package task.swenson.eventplanner.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import task.swenson.eventplanner.R
import task.swenson.eventplanner.presentation.theme.MiddleGray

@Composable
fun Header(
    modifier: Modifier = Modifier,
    title: String,
    subTitle: String,
    showBackButton: Boolean,
    onBackClicked: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .padding(horizontal = 24.dp)
            .fillMaxWidth()
    ) {
        if (showBackButton)
            Icon(
                modifier = Modifier
                    .size(25.dp)
                    .scale(scaleX = -1f, scaleY = 1f)
                    .clickable { onBackClicked() },
                painter = painterResource(id = R.drawable.ic_arrow),
                contentDescription = null,
            )
        else
            Spacer(modifier = Modifier.size(25.dp))

        Text(
            modifier = Modifier
                .padding(top = 8.dp, start = 14.dp, bottom = 16.dp, end = 14.dp)
                .fillMaxWidth(),
            text = title,
            color = Color.Black,
            textAlign = TextAlign.Center,
            fontSize = 18.sp,
            fontWeight = FontWeight.Black,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.subtitle1
        )

        Text(
            modifier = Modifier
                .padding(horizontal = 14.dp)
                .padding(bottom = 16.dp)
                .widthIn(max = 240.dp)
                .align(Alignment.CenterHorizontally),
            text = subTitle,
            color = MiddleGray,
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
            lineHeight = 22.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Preview
@Composable
fun HeaderPreview() {
    Header(
        title = "Drinks",
        subTitle = "Add to your event to view our cost estimate.",
        showBackButton = true
    ) {
        // Do Nothing
    }
}