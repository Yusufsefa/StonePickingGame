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
import com.yyusufsefa.stonepickinggame.toast
import com.yyusufsefa.stonepickinggame.viewmodel.GridViewModelFactory
import com.yyusufsefa.stonepickinggame.viewmodel.PrepareLevelViewModel
import kotlinx.android.synthetic.main.fragment_play_game.*
import kotlinx.coroutines.InternalCoroutinesApi

class PlayGameFragment : Fragment(R.layout.fragment_play_game) {

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

        viewmodel.allGridItem.observe(viewLifecycleOwner, {
            gridViewPlayGame.adapter = StoneAdapter(
                requireContext(),
                it,
                ::onClick
            )
        })

    }

    private fun onClick(clickedItem: GridItem) {
        requireContext().toast(clickedItem.mode.toString())
    }

}