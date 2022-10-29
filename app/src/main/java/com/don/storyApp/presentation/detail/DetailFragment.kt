package com.don.storyApp.presentation.detail

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.don.storyApp.R
import com.don.storyApp.databinding.FragmentDetailBinding
import com.don.storyApp.util.Constant
import com.don.storyApp.util.DateHelper
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber


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
            setHasOptionsMenu(true)
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

    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_change_language -> {
                startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
                true
            }
            R.id.action_add_story -> {
                findNavController().navigate(R.id.action_General_to_AddStoryFragment)
                true
            }
            R.id.action_log_out -> {
                Timber.e("=== action_log_out ")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}