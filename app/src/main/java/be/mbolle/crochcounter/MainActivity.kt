package be.mbolle.crochcounter

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModelProvider
import be.mbolle.crochcounter.service.Actions
import be.mbolle.crochcounter.service.CrochetService
import be.mbolle.crochcounter.ui.CrochCounterApp
import be.mbolle.crochcounter.ui.CrochCounterViewModelFactory
import be.mbolle.crochcounter.ui.theme.CrochCounterTheme
import be.mbolle.crochcounter.ui.theme.CrochCounterViewModel

class MainActivity : ComponentActivity() {
    private var serviceIntent: Intent? = null

    private val crochCounterViewModel by lazy {
        ViewModelProvider(
            this,
            CrochCounterViewModelFactory.getInstance(this)
        )[CrochCounterViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("MainActivity", "on create is called")
        super.onCreate(savedInstanceState)

        if (!Settings.canDrawOverlays(this)) {
            startActivity(Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION))
        }

        enableEdgeToEdge()
        setContent {
            CrochCounterTheme {
                CrochCounterApp(crochCounterViewModel = crochCounterViewModel)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        if (Settings.canDrawOverlays(this)) {
            Log.d("MainActivity", "this is called")
            serviceIntent = Intent(this, CrochetService::class.java)
            serviceIntent?.action = Actions.START.toString()


            startService(serviceIntent)
        }
    }

    override fun onResume() {
        super.onResume()

        if (serviceIntent?.action != null) {
            serviceIntent = Intent(this, CrochetService::class.java)
            serviceIntent?.action = Actions.STOP.toString()
            startService(serviceIntent)
        }

        Log.d("MainActivity", "it is running now kill it")

        // check if service is running
    }
}
