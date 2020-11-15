package com.yyusufsefa.stonepickinggame

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_prepare_level.*

class PrepareLevel : Fragment(R.layout.fragment_prepare_level) {

    private var gridType: GridType? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        gridView.adapter = StoneAdapter(
            requireContext(),
            MockList.getMockList(),
            ::onGridViewItemClick
        )
        initToggles()
        resetGrid()
    }

    private fun onGridViewItemClick(clickedItem: GridItem) {
        clickedItem.mode = when (gridType) {
            GridType.MAINSTONE -> StoneType.MAINSTONE

            GridType.NORMALSTONE -> StoneType.NORMALSTONE

            GridType.WALL -> StoneType.WALL

            GridType.NONE -> StoneType.NONE

            else -> StoneType.NONE
        }
        (gridView.adapter as StoneAdapter).notifyDataSetChanged()

    }


    private fun initToggles() {
        toggleMainStone.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                toggleNormalStone.isChecked = false
                toggleWallStone.isChecked = false
                gridType = GridType.MAINSTONE
            } else
                gridType = GridType.NONE
        }
        toggleNormalStone.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                toggleMainStone.isChecked = false
                toggleWallStone.isChecked = false
                gridType = GridType.NORMALSTONE
            } else
                gridType = GridType.NONE
        }
        toggleWallStone.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                toggleMainStone.isChecked = false
                toggleNormalStone.isChecked = false
                gridType = GridType.WALL
            } else
                gridType = GridType.NONE
        }
    }

    private fun resetGrid() {
        btnReset.setOnClickListener {
            gridView.adapter = StoneAdapter(
                requireContext(),
                MockList.getMockList(),
                ::onGridViewItemClick
            )
            toggleNormalStone.isChecked = false
            toggleWallStone.isChecked = false
            toggleMainStone.isChecked = false
            (gridView.adapter as StoneAdapter).notifyDataSetChanged()
        }
    }


}



