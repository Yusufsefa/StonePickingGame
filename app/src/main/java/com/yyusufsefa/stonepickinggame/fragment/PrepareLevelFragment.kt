package com.yyusufsefa.stonepickinggame.fragment

import android.os.Bundle
import android.view.View
import android.widget.ToggleButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
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

        initGridView()
        initToggles()
        btnReset.setOnClickListener { resetGrid() }
        btnSave.setOnClickListener { saveLevel() }

    }

    private fun initGridView() {
        gridView.adapter = StoneAdapter(
            requireContext(),
            MockList.getMockList(),
            ::onGridViewItemClick
        )
    }

    private fun saveLevel() {
        val list = (gridView.adapter as StoneAdapter).gridItemList
        //level 1 save
        //level 2 save
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
            StoneType.WALL -> clickedItem.mode = StoneType.WALL
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
    }

    private fun resetGrid() {
        initGridView()
        toggleCheckedFalser(arrayOf(toggleNormalStone, toggleMainStone, toggleWallStone))
        mainStoneLimit = 1
        normalStoneLimit = 7
        (gridView.adapter as StoneAdapter).notifyDataSetChanged()
    }

}