package com.don.storyApp.presentation.stories

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.don.storyApp.R
import com.don.storyApp.databinding.FragmentStoriesBinding
import com.don.storyApp.util.Extras
import dagger.hilt.android.AndroidEntryPoint


/**
 * Created by gideon on 29 October 2022
 * gideon@cicil.co.id
 * https://www.cicil.co.id/
 */
@AndroidEntryPoint
class StoriesFragment : Fragment() {

    private var binding: FragmentStoriesBinding? = null

    private val viewModel by viewModels<StoriesViewModel>()

    private val storiesAdapter: StoriesAdapter by lazy {
        StoriesAdapter(
            onClick = { story, iv ->
                findNavController().navigate(
                    resId = R.id.action_stories_fragment_to_detail_fragment,
                    args = bundleOf(Extras.KEY_STORY to story),
                    navOptions = null,
                    navigatorExtras = FragmentNavigatorExtras(
                        iv to "transName"
                    )
                )
            }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStoriesBinding.inflate(inflater, container, false)

        binding?.apply {
            lifecycleOwner = this@StoriesFragment
            vm = viewModel
            adapter = storiesAdapter
        }
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getStories()

        binding?.viewError?.errorRetry?.setOnClickListener {
            getStories()
        }

        activity?.onBackPressedDispatcher?.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    activity?.finish()
                }
            }
        )
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
                findNavController().navigate(R.id.action_general_to_add_story_fragment)
                true
            }
            R.id.action_log_out -> {
                viewModel.logout()
                findNavController().navigate(R.id.action_general_to_nav_graph)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun getStories() {
        viewModel.getStories(
            errorMessage = {
                binding?.let { vBinding ->
                    vBinding.viewError.errorTitle.text = it
                }
            },
            onSuccess = {
                storiesAdapter.submitList(it)
            }
        )
    }
}