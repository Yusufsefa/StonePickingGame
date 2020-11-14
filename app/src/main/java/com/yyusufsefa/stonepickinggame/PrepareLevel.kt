package com.yyusufsefa.stonepickinggame

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_prepare_level.*

class PrepareLevel : Fragment(R.layout.fragment_prepare_level) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        gridView.adapter = StoneAdapter(
            requireContext(),
            MockList.getMockList(),
            ::onGridViewItemClick
        )

        initToggles()
    }


    private fun onGridViewItemClick(gridItemList: List<GridItem>, clickedItem: GridItem) {
        Toast.makeText(
            requireContext(),
            clickedItem.x.toString() + " " + clickedItem.y,
            Toast.LENGTH_LONG
        ).show()
    }


    private fun initToggles() {
        toggleMainStone.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                toggleNormalStone.isChecked = false
                toggleWallStone.isChecked = false
            }
        }
        toggleNormalStone.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                toggleMainStone.isChecked = false
                toggleWallStone.isChecked = false
            }
        }
        toggleWallStone.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                toggleMainStone.isChecked = false
                toggleNormalStone.isChecked = false
            }
        }
    }


}



