package com.don.storyApp.presentation.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.don.storyApp.databinding.FragmentDetailBinding
import com.don.storyApp.util.Constant
import com.don.storyApp.util.DateHelper
import dagger.hilt.android.AndroidEntryPoint


/**
 * Created by gideon on 29 October 2022
 * gideon@cicil.co.id
 * https://www.cicil.co.id/
 */

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private var binding: FragmentDetailBinding? = null

    private val args: DetailFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context?.let {
            sharedElementEnterTransition =
                TransitionInflater.from(it).inflateTransition(android.R.transition.explode)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater, container, false)

        binding?.apply {
            lifecycleOwner = this@DetailFragment
            story = args.keyStory
            helper = DateHelper()
            constant = Constant()
            executePendingBindings()
        }
        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}