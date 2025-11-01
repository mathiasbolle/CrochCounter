package be.mbolle.crochcounter

import android.app.Application
import be.mbolle.crochcounter.core.di.ProductionContainer


class MainApplication : Application() {

    companion object {
        lateinit var container: ProductionContainer
            private set

    }

    override fun onCreate() {
        super.onCreate()
        container = ProductionContainer(this)

    }
}