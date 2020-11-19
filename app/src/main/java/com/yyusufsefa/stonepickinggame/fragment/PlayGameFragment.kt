package com.yyusufsefa.stonepickinggame.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.yyusufsefa.stonepickinggame.R
import com.yyusufsefa.stonepickinggame.adapter.StoneAdapter
import com.yyusufsefa.stonepickinggame.db.GridItemRepository
import com.yyusufsefa.stonepickinggame.db.GridRoomDatabase
import com.yyusufsefa.stonepickinggame.model.GridItem
import com.yyusufsefa.stonepickinggame.model.StoneType
import com.yyusufsefa.stonepickinggame.toast
import com.yyusufsefa.stonepickinggame.toastSuccess
import com.yyusufsefa.stonepickinggame.viewmodel.GridViewModelFactory
import com.yyusufsefa.stonepickinggame.viewmodel.PrepareLevelViewModel
import kotlinx.android.synthetic.main.fragment_play_game.*
import kotlinx.coroutines.InternalCoroutinesApi

class PlayGameFragment : Fragment(R.layout.fragment_play_game) {

    private val level by lazy { arguments?.getInt("level") }

    private var gridList: List<GridItem>? = null

    private var oldClickedItem: GridItem? = null

    private var isGridSelected = false

    private var maxMove: Int = 0


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

        toolbar.title = "$level. Seviye"

        viewmodel.allGridItem.observe(viewLifecycleOwner, { it ->
            gridList = it.filter { it.level == level }
            if (gridList.isNullOrEmpty())
                levelIsEmpty()
            else
                gridViewPlayGame.adapter = StoneAdapter(
                    requireContext(),
                    gridList!!,
                    ::onClick
                )
        })
    }

    private fun levelIsEmpty() {
        requireContext().toast("Lütfen bu seviyeye ait oyun oluşturun.")
    }

    private fun onClick(clickedItem: GridItem) {
        if (isGridSelected) {
            moveStone(clickedItem)
        } else {
            selectStone(clickedItem)
        }
    }


    private fun moveStone(clickedItem: GridItem) {
        if (clickedItem.isBackgroundActive) {
            oldClickedItem?.mode = StoneType.NONE
            if (clickedItem.mode != StoneType.MAINSTONE) {
                clickedItem.mode = StoneType.NORMALSTONE
                clickedItem.maxMove = (oldClickedItem?.maxMove?.minus(1))
            } else {
                val size = gridList!!.filter { it.mode == StoneType.NORMALSTONE }.size
                if (size > 0) requireContext().toast("Taş toplandı")
                else winGame()
            }
            isGridSelected = false
            resetAllMovableBackground()
            (gridViewPlayGame.adapter as StoneAdapter).notifyDataSetChanged()
        } else {
            requireContext().toast("Taşınız oraya hareket edemez")
        }
    }

    private fun selectStone(clickedItem: GridItem) {
        if (clickedItem.mode == StoneType.NORMALSTONE && clickedItem.maxMove != 0) {
            oldClickedItem = clickedItem
            isGridSelected = true
            gridMovableBackgroundChanger()
        } else if (clickedItem.maxMove == 0) loseGame()
        else {
            requireContext().toast("Seçiminiz hareket edemez")
        }
    }

    private fun winGame() {
        requireContext().toastSuccess("Oyunu kazandınız")
        requireActivity().onBackPressed()
    }

    private fun loseGame() {
        requireContext().toast("Kaybettinnn")
        requireActivity().onBackPressed()
    }


    private fun gridMovableBackgroundChanger() {
        val i = gridList!!.indexOf(oldClickedItem)
        val indexList = mutableListOf<Int>()
        if (oldClickedItem?.x != 1) indexList.add(i - 1)
        if (oldClickedItem?.x != 10) indexList.add(i + 1)
        if (oldClickedItem?.y != 1) indexList.add(i - 10)
        if (oldClickedItem?.y != 10) indexList.add(i + 10)
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