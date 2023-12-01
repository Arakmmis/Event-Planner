package task.swenson.eventplanner.presentation.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val colorPalette = lightColors(
    primary = Pampas,
    primaryVariant = Pampas,
    secondary = BreakerBay
)

@Composable
fun EventsPlannerTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = colorPalette,
        typography = Typography,
        shapes = Shape,
        content = content
    )
}