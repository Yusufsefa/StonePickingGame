package com.yyusufsefa.stonepickinggame.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yyusufsefa.stonepickinggame.db.GridItemRepository
import com.yyusufsefa.stonepickinggame.model.GridItem
import kotlinx.coroutines.launch

class PrepareLevelViewModel(private val repository: GridItemRepository) : ViewModel() {

    val allGridItem: LiveData<List<GridItem>> = repository.allGridItem

    fun insert(gridList: List<GridItem>) = viewModelScope.launch {
        repository.insert(gridList)
    }

    fun deleteToLevel(level: Int) = viewModelScope.launch {
        repository.deleteToLevel(level)
    }

}
