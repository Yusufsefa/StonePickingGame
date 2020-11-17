package com.yyusufsefa.stonepickinggame.fragment

import android.os.Bundle
import android.view.View
import android.widget.ToggleButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.yyusufsefa.stonepickinggame.AutoGenerator
import com.yyusufsefa.stonepickinggame.MockList
import com.yyusufsefa.stonepickinggame.R
import com.yyusufsefa.stonepickinggame.adapter.StoneAdapter
import com.yyusufsefa.stonepickinggame.db.GridItemRepository
import com.yyusufsefa.stonepickinggame.db.GridRoomDatabase
import com.yyusufsefa.stonepickinggame.model.GridItem
import com.yyusufsefa.stonepickinggame.model.StoneType
import com.yyusufsefa.stonepickinggame.toast
import com.yyusufsefa.stonepickinggame.viewmodel.GridViewModelFactory
import com.yyusufsefa.stonepickinggame.viewmodel.PrepareLevelViewModel
import kotlinx.android.synthetic.main.fragment_prepare_level.*
import kotlinx.coroutines.InternalCoroutinesApi

class PrepareLevelFragment : Fragment(R.layout.fragment_prepare_level) {

    private var gridType: StoneType = StoneType.NONE

    private var mainStoneLimit = 1
    private var normalStoneLimit = 7
    private var wallStoneLimit = 5

    private val level by lazy { arguments?.getInt("level") }

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

        toolbar.title = "${level}. Seviye Hazırlanıyor."
        normalStoneLimit = if (level == 1) 7 else 9
        wallStoneLimit = if (level == 1) 5 else 7

        initGridView()
        initToggles()
        btnReset.setOnClickListener { resetGrid() }
        btnSave.setOnClickListener { saveLevel() }
        btnAuto.setOnClickListener {
            mainStoneLimit = 1
            normalStoneLimit = if (level == 1) 7 else 9
            wallStoneLimit = if (level == 1) 5 else 7
            autosequence(
                mainStoneLimit,
                normalStoneLimit,
                wallStoneLimit
            )
        }

    }

    private fun initGridView() {
        gridView.adapter = StoneAdapter(
            requireContext(),
            MockList.getMockList(),
            ::onGridViewItemClick
        )
    }

    @InternalCoroutinesApi
    private fun saveLevel() {
        level?.let { viewmodel.deleteToLevel(it) }
        val list = (gridView.adapter as StoneAdapter).gridItemList
        for (x in list.indices) {
            if (level == 1)
                list[x].level = 1
            else
                list[x].level = 2
        }
        calculateMaxMove(list)
        viewmodel.insert(list)
    }

    private fun calculateMaxMove(list: List<GridItem>) {
        var mainX = 0
        var mainY = 0
        var normalX = 0
        var normalY = 0
        for (x in list.indices) {
            if (list[x].mode == StoneType.MAINSTONE) {
                mainX = list[x].x
                mainY = list[x].y
            }
            if (list[x].mode == StoneType.NORMALSTONE) {
                normalX = if (mainX > list[x].x) {
                    (mainX - list[x].x)
                } else {
                    (list[x].x - mainX)
                }
                normalY = if (mainY > list[x].y) {
                    (mainY - list[x].y)
                } else {
                    (list[x].y - mainY)
                }
            }
            list[x].maxMove = (normalX + normalY)
        }
    }

    private fun onGridViewItemClick(clickedItem: GridItem) {
        if (clickedItem.mode != StoneType.NONE) {
            requireContext().toast("Seçtiğiniz kare boş değil")
            return
        }

        when (gridType) {
            StoneType.MAINSTONE -> {
                if (mainStoneLimit > 0) {
                    clickedItem.mode = StoneType.MAINSTONE
                    mainStoneLimit--
                } else requireContext().toast("İlgili kategoride limit dolmuş")
            }
            StoneType.NORMALSTONE -> {
                if (normalStoneLimit > 0) {
                    clickedItem.mode = StoneType.NORMALSTONE
                    normalStoneLimit--
                } else requireContext().toast("İlgili kategoride limit dolmuş")
            }
            StoneType.WALL -> {
                if (wallStoneLimit > 0) {
                    clickedItem.mode = StoneType.WALL
                    wallStoneLimit--
                } else requireContext().toast("İlgili kategoride limit dolmuş")
            }
            StoneType.NONE -> requireContext().toast("Lütfen seçim yapın")
        }
        (gridView.adapter as StoneAdapter).notifyDataSetChanged()
    }


    private fun initToggles() {
        toggleMainStone.setOnCheckedChangeListener { buttonView, isChecked ->
            gridType = if (isChecked) {
                toggleCheckedFalser(arrayOf(toggleNormalStone, toggleWallStone))
                StoneType.MAINSTONE
            } else StoneType.NONE
        }
        toggleNormalStone.setOnCheckedChangeListener { buttonView, isChecked ->
            gridType = if (isChecked) {
                toggleCheckedFalser(arrayOf(toggleMainStone, toggleWallStone))
                StoneType.NORMALSTONE
            } else StoneType.NONE
        }
        toggleWallStone.setOnCheckedChangeListener { buttonView, isChecked ->
            gridType = if (isChecked) {
                toggleCheckedFalser(arrayOf(toggleMainStone, toggleNormalStone))
                StoneType.WALL
            } else StoneType.NONE
        }
    }

    private fun toggleCheckedFalser(toggleList: Array<ToggleButton>) {
        toggleList.forEach { it.isChecked = false }
        toggleList.forEach { it.isEnabled = true }
    }

    private fun toggleisEnabledFalser(toggleList: Array<ToggleButton>) {
        toggleList.forEach { it.isEnabled = false }
    }

    private fun resetGrid() {
        initGridView()
        mainStoneLimit = 1
        normalStoneLimit = if (level == 1) 7 else 9
        wallStoneLimit = if (level == 1) 5 else 7
        toggleCheckedFalser(arrayOf(toggleNormalStone, toggleMainStone, toggleWallStone))
        (gridView.adapter as StoneAdapter).notifyDataSetChanged()
    }

    private fun autosequence(
        mainStone: Int,
        normalStone: Int,
        wall: Int
    ) {
        gridView.adapter = StoneAdapter(
            requireContext(),
            AutoGenerator.getAutoGridList(mainStone, normalStone, wall),
            ::onGridViewItemClick
        )
        toggleCheckedFalser(arrayOf(toggleNormalStone, toggleMainStone, toggleWallStone))
        toggleisEnabledFalser(arrayOf(toggleNormalStone, toggleMainStone, toggleWallStone))
        (gridView.adapter as StoneAdapter).notifyDataSetChanged()
    }

}