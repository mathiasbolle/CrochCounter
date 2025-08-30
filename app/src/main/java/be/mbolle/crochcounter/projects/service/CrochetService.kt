package be.mbolle.crochcounter.projects.service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.hardware.display.DisplayManager
import android.os.Build
import android.os.IBinder
import android.util.Log
import android.view.Display
import android.view.Gravity
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Recomposer
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
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
import be.mbolle.crochcounter.projects.presentation.composables.PopupWindow
import be.mbolle.crochcounter.ui.CrochCounterViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@RequiresApi(Build.VERSION_CODES.R)
class CrochetService() : Service(), ViewModelStoreOwner {

    private var overlayOffset by mutableStateOf(Offset.Zero)
    private var scale by mutableFloatStateOf(1f)
    val windowManager get() = overlayContext.getSystemService(WINDOW_SERVICE) as WindowManager
    var composeView: ComposeView? = null

    private val layoutParams = WindowManager.LayoutParams(
        WindowManager.LayoutParams.WRAP_CONTENT,
        WindowManager.LayoutParams.WRAP_CONTENT,
        WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS or
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or
                WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
        PixelFormat.TRANSLUCENT
    ).apply {
        gravity = Gravity.TOP or Gravity.CENTER_HORIZONTAL
    }

    internal val overlayContext: Context by lazy {
        // Get the default display
        val defaultDisplay: Display =
            getSystemService(DisplayManager::class.java).getDisplay(Display.DEFAULT_DISPLAY)
        // Create a display context, and then the window context
        createDisplayContext(defaultDisplay)
            .createWindowContext(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY, null)
    }


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

        val params = layoutParams

        composeView = ComposeView(overlayContext)

        val crochCounterViewModel by lazy {
            ViewModelProvider(
                this,
                CrochCounterViewModelFactory.getInstance(this)
            )[CrochCounterViewModel::class.java]
        }

        composeView?.setContent {
            OverlayDraggableContainer {
                PopupWindow(
                    counter = crochCounterViewModel.crochCounterState.counter,
                    addValue = { crochCounterViewModel.addCounterByOne() },
                    project = crochCounterViewModel.crochCounterState.name.toString()
                )
            }

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
        val scheduler = Recomposer(coroutineContext)
        composeView?.compositionContext = scheduler
        runRecomposeScope.launch {
            scheduler.runRecomposeAndApplyChanges()
        }
        windowManager.addView(composeView, params)
    }

    override val viewModelStore: ViewModelStore
        get() = ViewModelStore()



    @Composable
    internal fun OverlayDraggableContainer(
        modifier: Modifier = Modifier,
        content: @Composable BoxScope.() -> Unit
    ) {
        val state = rememberTransformableState { zoomChange, offsetChange, rotationChange ->
            scale *= zoomChange
        }

        Box(
            modifier = modifier
                .graphicsLayer(
                    scaleX = scale,
                    scaleY = scale,
                )
                .transformable(state = state)
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        change.consume()

                        // Update our current offset
                        val newOffset = overlayOffset + dragAmount
                        overlayOffset = newOffset

                        // Update the layout params, and then the view
                        layoutParams.apply {
                            x = overlayOffset.x.roundToInt()
                            y = overlayOffset.y.roundToInt()
                        }
                        windowManager.updateViewLayout(composeView, layoutParams)
                    }
                },
            content = content
        )
    }
}

enum class Actions {
    START, STOP
}

