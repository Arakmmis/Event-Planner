package task.swenson.eventplanner.presentation.components

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Composable
fun Budget(
    modifier: Modifier = Modifier,
    avgBudget: Int? = null,
    minBudget: Int? = null,
    maxBudget: Int? = null
) {
    Text(
        modifier = modifier,
        text = getBudget(
            avgBudget,
            minBudget,
            maxBudget
        ),
        color = Color.Black,
        fontSize = 37.sp,
        fontWeight = FontWeight.Black,
        textAlign = TextAlign.Center,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis
    )
}

private fun getBudget(
    avgBudget: Int? = null,
    minBudget: Int? = null,
    maxBudget: Int? = null
): String =
    if (avgBudget != null)
        "$$avgBudget"
    else if (minBudget != null && maxBudget != null)
        "$$minBudget-$$maxBudget"
    else
        ""

@Preview(showBackground = true)
@Composable
fun BudgetPreview() {
    Budget(
        minBudget = 10000,
        maxBudget = 100000
    )
}