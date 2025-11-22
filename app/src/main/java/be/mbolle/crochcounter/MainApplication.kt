package be.mbolle.crochcounter

import android.app.Application
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import be.mbolle.crochcounter.core.di.ProductionContainer


class MainApplication : Application(), ViewModelStoreOwner {

    private val appViewModelStore = ViewModelStore()

    companion object {
        lateinit var container: ProductionContainer
            private set

    }

    override fun onCreate() {
        super.onCreate()
        container = ProductionContainer(
            this,
            this
        )
    }

    override val viewModelStore: ViewModelStore
        get() = appViewModelStore
}