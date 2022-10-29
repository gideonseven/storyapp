package com.don.storyApp.presentation.add_story

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.don.storyApp.databinding.FragmentAddStoryBinding
import dagger.hilt.android.AndroidEntryPoint


/**
 * Created by gideon on 29 October 2022
 * gideon@cicil.co.id
 * https://www.cicil.co.id/
 */
@AndroidEntryPoint
class AddStoryFragment : Fragment() {

    private var binding: FragmentAddStoryBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddStoryBinding.inflate(inflater, container, false)

        binding?.apply {
            lifecycleOwner = this@AddStoryFragment
        }
        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}