package com.yyusufsefa.stonepickinggame.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yyusufsefa.stonepickinggame.db.GridItemRepository

class GridViewModelFactory(private val repository: GridItemRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PrepareLevelViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PrepareLevelViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}