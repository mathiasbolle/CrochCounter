package be.mbolle.crochcounter.core.di

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import be.mbolle.crochcounter.core.data.db.CrochDatabase
import be.mbolle.crochcounter.patterns.data.PatternRoomRepository
import be.mbolle.crochcounter.patterns.screens.PatternMainViewModel

/**
 * DI container for production
 */


interface Container
class ProductionContainer(context: Context): Container {
    val crocherDatabase = CrochDatabase.getDatabase(context)
    val patternRepository = PatternRoomRepository(crocherDatabase.patternDao())

    val crochetFactory: ViewModelProvider.Factory = viewModelFactory {
        initializer {
            val savedStateHandle = createSavedStateHandle()
            PatternMainViewModel(
                patternRepository = patternRepository,
                patternWithPatternItemDao = crocherDatabase.patternWithPatternItemDao(),
                patternItemDao = crocherDatabase.patternItemDao(),
                savedStateHandle
            )
        }
    }
}