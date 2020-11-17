package com.yyusufsefa.stonepickinggame.fragment

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.yyusufsefa.stonepickinggame.R
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment(R.layout.fragment_home) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnFirstPrepareLevel.setOnClickListener {
            val bundle = bundleOf("level" to 1)
            findNavController().navigate(R.id.action_homeFragment_to_prepareLevel, bundle)
        }

        btnSecondPrepareLevel.setOnClickListener {
            val bundle = bundleOf("level" to 2)
            findNavController().navigate(R.id.action_homeFragment_to_prepareLevel, bundle)
        }

        btnPlayFirstLevel.setOnClickListener {
            val bundle = bundleOf("level" to 1)
            findNavController().navigate(R.id.action_homeFragment_to_playGameFragment, bundle)
        }

        btnPlaySecondLevel.setOnClickListener {
            val bundle = bundleOf("level" to 2)
            findNavController().navigate(R.id.action_homeFragment_to_playGameFragment, bundle)
        }

    }
}