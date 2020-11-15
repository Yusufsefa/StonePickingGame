package com.yyusufsefa.stonepickinggame

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.yyusufsefa.stonepickinggame.db.GridItemRepository
import com.yyusufsefa.stonepickinggame.db.GridRoomDatabase
import com.yyusufsefa.stonepickinggame.model.GridItem
import com.yyusufsefa.stonepickinggame.model.GridType
import com.yyusufsefa.stonepickinggame.model.StoneType
import com.yyusufsefa.stonepickinggame.viewmodel.GridViewModelFactory
import com.yyusufsefa.stonepickinggame.viewmodel.PrepareLevelViewModel
import kotlinx.android.synthetic.main.fragment_prepare_level.*
import kotlinx.coroutines.InternalCoroutinesApi

class PrepareLevelFragment : Fragment(R.layout.fragment_prepare_level) {

    private var gridType: GridType? = null

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
                Toast.makeText(requireContext(), "Veriler geldi baba", Toast.LENGTH_SHORT).show()
            })

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



