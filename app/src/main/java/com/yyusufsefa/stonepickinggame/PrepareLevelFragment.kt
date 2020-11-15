package com.yyusufsefa.stonepickinggame

import android.os.Bundle
import android.view.View
import android.widget.ToggleButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.yyusufsefa.stonepickinggame.db.GridItemRepository
import com.yyusufsefa.stonepickinggame.db.GridRoomDatabase
import com.yyusufsefa.stonepickinggame.model.GridItem
import com.yyusufsefa.stonepickinggame.model.StoneType
import com.yyusufsefa.stonepickinggame.viewmodel.GridViewModelFactory
import com.yyusufsefa.stonepickinggame.viewmodel.PrepareLevelViewModel
import kotlinx.android.synthetic.main.fragment_prepare_level.*
import kotlinx.coroutines.InternalCoroutinesApi

class PrepareLevelFragment : Fragment(R.layout.fragment_prepare_level) {

    private var gridType: StoneType = StoneType.NONE

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

        gridView.adapter = StoneAdapter(
            requireContext(),
            MockList.getMockList(),
            ::onGridViewItemClick
        )
        initToggles()
        btnReset.setOnClickListener { resetGrid() }


        viewmodel.allGridItem.observe(viewLifecycleOwner,
            Observer<List<GridItem>> {
                requireContext().toast("Veriler geldi baba")
            })

    }

    private fun onGridViewItemClick(clickedItem: GridItem) {
        when (gridType) {
            StoneType.MAINSTONE -> clickedItem.mode = StoneType.MAINSTONE
            StoneType.NORMALSTONE -> clickedItem.mode = StoneType.NORMALSTONE
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
        gridView.adapter = StoneAdapter(
            requireContext(),
            MockList.getMockList(),
            ::onGridViewItemClick
        )
        toggleCheckedFalser(arrayOf(toggleNormalStone, toggleMainStone, toggleWallStone))
        (gridView.adapter as StoneAdapter).notifyDataSetChanged()
    }

}