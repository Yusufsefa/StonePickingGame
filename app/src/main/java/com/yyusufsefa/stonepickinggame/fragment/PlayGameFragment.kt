package com.yyusufsefa.stonepickinggame.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.yyusufsefa.stonepickinggame.R
import com.yyusufsefa.stonepickinggame.adapter.StoneAdapter
import com.yyusufsefa.stonepickinggame.db.GridItemRepository
import com.yyusufsefa.stonepickinggame.db.GridRoomDatabase
import com.yyusufsefa.stonepickinggame.model.GridItem
import com.yyusufsefa.stonepickinggame.model.StoneType
import com.yyusufsefa.stonepickinggame.toast
import com.yyusufsefa.stonepickinggame.viewmodel.GridViewModelFactory
import com.yyusufsefa.stonepickinggame.viewmodel.PrepareLevelViewModel
import kotlinx.android.synthetic.main.fragment_play_game.*
import kotlinx.coroutines.InternalCoroutinesApi

class PlayGameFragment : Fragment(R.layout.fragment_play_game) {

    private val level by lazy { arguments?.getInt("level") }

    private var gridList: List<GridItem>? = null

    private var oldClickedItem: GridItem? = null

    private var isGridSelected = false


    @InternalCoroutinesApi
    private val viewmodel by lazy {
        ViewModelProvider(
            this,
            GridViewModelFactory(
                GridItemRepository(GridRoomDatabase.getDatabase(requireContext()).gridItemDao())
            )
        ).get(PrepareLevelViewModel::class.java)
    }

    @InternalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewmodel.allGridItem.observe(viewLifecycleOwner,
            Observer<List<GridItem>> { it ->
                gridList = it.filter { it.level == level }
                gridViewPlayGame.adapter = StoneAdapter(
                    requireContext(),
                    gridList!!,
                    ::onClick
                )
            })

    }

    private fun onClick(clickedItem: GridItem) {
        //seçilen taş hareket ettirme
        if (isGridSelected) {
            if (clickedItem.isBackgroundActive) {
                oldClickedItem?.mode = StoneType.NONE
                if (clickedItem.mode != StoneType.MAINSTONE) clickedItem.mode = StoneType.NORMALSTONE
                else requireContext().toast("Taş toplandı")
                isGridSelected = false
                resetAllMovableBackground()
                (gridViewPlayGame.adapter as StoneAdapter).notifyDataSetChanged()
            } else {
                requireContext().toast("Taşınız oraya hareket edemez")
            }
        }
        // else de ilk defa taş seçimi yapılıyor.
        else {
            if (clickedItem.mode == StoneType.NORMALSTONE) {
                oldClickedItem = clickedItem
                isGridSelected = true
                gridMovableBackgroundChanger()
            } else {
                requireContext().toast("Seçiminiz hareket edemez")
            }
        }
    }


    private fun gridMovableBackgroundChanger() {
        val i = gridList!!.indexOf(oldClickedItem)
        val indexList = mutableListOf<Int>()
        if (oldClickedItem?.x != 1) indexList.add(i-10)
        if (oldClickedItem?.x != 10) indexList.add(i+10)
        if (oldClickedItem?.y != 1) indexList.add(i-1)
        if (oldClickedItem?.y != 10) indexList.add(i+1)
        val list = indexList.map { gridList!![it] }
        list.forEach {
            if (isGridItemAvailableForBackgroundActive(it))
                it.isBackgroundActive = true
        }
        (gridViewPlayGame.adapter as StoneAdapter).notifyDataSetChanged()
    }

    private fun isGridItemAvailableForBackgroundActive(gridItem: GridItem): Boolean {
        if (gridItem.mode == StoneType.NONE || gridItem.mode == StoneType.MAINSTONE)
            return true
        return false
    }

    private fun resetAllMovableBackground() {
        gridList!!.forEach {
            if (it.isBackgroundActive)
                it.isBackgroundActive = false
        }
        (gridViewPlayGame.adapter as StoneAdapter).notifyDataSetChanged()
    }

}