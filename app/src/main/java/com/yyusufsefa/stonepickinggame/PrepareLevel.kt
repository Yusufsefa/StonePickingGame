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
    }


    private fun onGridViewItemClick(clickedItem: GridItem) {

        clickedItem.mode = when (gridType) {
            GridType.MAINSTONE -> StoneType.MAINSTONE

            GridType.NORMALSTONE -> StoneType.NORMALSTONE

            GridType.WALL -> StoneType.WALL

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
            }
        }
        toggleNormalStone.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                toggleMainStone.isChecked = false
                toggleWallStone.isChecked = false
                gridType = GridType.NORMALSTONE
            }
        }
        toggleWallStone.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                toggleMainStone.isChecked = false
                toggleNormalStone.isChecked = false
                gridType = GridType.WALL
            }
        }
    }


}



