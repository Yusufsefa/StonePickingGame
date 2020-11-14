package com.yyusufsefa.stonepickinggame

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_prepare_level.*

class PrepareLevel : Fragment(R.layout.fragment_prepare_level){

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        gridView.adapter = StoneAdapter(requireContext(), MockList.getMockList())
    }

}