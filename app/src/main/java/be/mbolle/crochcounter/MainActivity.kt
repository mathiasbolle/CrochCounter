package be.mbolle.crochcounter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import be.mbolle.crochcounter.ui.CrochCounterApp
import be.mbolle.crochcounter.ui.theme.CrochCounterTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CrochCounterTheme {
                CrochCounterApp()
            }
        }
    }
}
