package be.mbolle.crochcounter

import android.app.Service
import android.content.Intent
import android.graphics.PixelFormat
import android.os.IBinder
import android.util.Log
import android.view.WindowManager
import androidx.compose.runtime.Recomposer
import androidx.compose.ui.platform.AndroidUiDispatcher
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.compositionContext
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.setViewTreeLifecycleOwner
import androidx.lifecycle.setViewTreeViewModelStoreOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner
import be.mbolle.crochcounter.ui.CrochCounterViewModelFactory
import be.mbolle.crochcounter.ui.composables.PopupWindow
import be.mbolle.crochcounter.ui.theme.CrochCounterViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class CrochetService(): Service(), ViewModelStoreOwner {
    val windowManager get() = getSystemService(WINDOW_SERVICE) as WindowManager
    var composeView: ComposeView? = null

    override fun onBind(intent: Intent?): IBinder? {
         return null
    }

    override fun onStartCommand(
        intent: Intent?,
        flags: Int,
        startId: Int
    ): Int {
        Log.d("CrochetService", intent.toString())
        when (intent?.action) {
            Actions.START.toString() -> showOverlay()
            Actions.STOP.toString() -> removeOverlay()
        }

        return super.onStartCommand(intent, flags, startId)
    }

    private fun removeOverlay() {
        Log.d("CrochetService", "removeOverlay is called")
        windowManager.removeViewImmediate(composeView)
        composeView = null
    }

    private fun showOverlay() {
        Log.d("CrochetService", "overlay is called!")

        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS or
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                    WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or
                    WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
            //WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS or WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH or WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            //WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE or WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.OPAQUE
        )

        composeView = ComposeView(this)

        val crochCounterViewModel by lazy {
            ViewModelProvider(
                this,
                CrochCounterViewModelFactory.getInstance(this)
            )[CrochCounterViewModel::class.java]
        }

        composeView?.setContent {


            PopupWindow(counter = crochCounterViewModel.counter)


        }

        val lifecycleOwner = MyLifecycleOwner()
        lifecycleOwner.performRestore(null)
        lifecycleOwner.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)

        composeView?.setViewTreeLifecycleOwner(lifecycleOwner)

        composeView?.setViewTreeSavedStateRegistryOwner(lifecycleOwner)

        //val viewModelStoreOwner = My
        //val viewModelStore = ViewModelProvider(ViewModelStore())

        composeView?.setViewTreeViewModelStoreOwner(this)
        //ViewTreeViewModelStoreOwner.set(composeView) { viewModelStore }

        val coroutineContext = AndroidUiDispatcher.CurrentThread
        val runRecomposeScope = CoroutineScope(coroutineContext)
        val recomposer = Recomposer(coroutineContext)
        composeView?.compositionContext = recomposer
        runRecomposeScope.launch {
            recomposer.runRecomposeAndApplyChanges()
        }

        windowManager.addView(composeView, params)

    }

    override val viewModelStore: ViewModelStore
        get() = ViewModelStore()
}

enum class Actions {
    START, STOP
}