package task.swenson.eventplanner.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import task.swenson.eventplanner.presentation.theme.EventsPlannerTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EventsPlannerTheme {
                EventsPlannerApp()
            }
        }
    }
}