package com.yyusufsefa.stonepickinggame.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yyusufsefa.stonepickinggame.GridItem
import com.yyusufsefa.stonepickinggame.db.GridItemRepository
import kotlinx.coroutines.launch

class PrepareLevelViewModel(private val repository: GridItemRepository) : ViewModel() {

    val allGridItem: LiveData<List<GridItem>> = repository.allGridItem

    fun insert(gridItem: GridItem) = viewModelScope.launch {
        repository.insert(gridItem)
    }

}
