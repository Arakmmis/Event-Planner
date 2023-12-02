package task.swenson.eventplanner.presentation.screens.error

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import task.swenson.eventplanner.R
import task.swenson.eventplanner.domain.util.TextHelper
import task.swenson.eventplanner.presentation.theme.BreakerBay
import task.swenson.eventplanner.presentation.theme.MiddleGray

@Composable
fun ErrorScreen(
    modifier: Modifier = Modifier,
    error: TextHelper? = null
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Icon(
                modifier = Modifier
                    .size(100.dp)
                    .align(Alignment.CenterHorizontally),
                painter = painterResource(id = R.drawable.ic_no_data),
                tint = BreakerBay,
                contentDescription = null
            )

            Text(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 16.dp),
                text = error?.asString(LocalContext.current)
                    ?: stringResource(id = R.string.err_try_again),
                color = MiddleGray,
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                lineHeight = 22.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Preview
@Composable
fun ErrorScreenPreview() {
    ErrorScreen(
        error = TextHelper.DynamicString("Internal Server Error! Please try again!")
    )
}