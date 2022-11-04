package com.don.storyApp.presentation

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.don.storyApp.R
import com.don.storyApp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment?
        val navController = navHostFragment?.navController

        navController?.apply {
            appBarConfiguration = AppBarConfiguration(this.graph)
            setupActionBarWithNavController(this, appBarConfiguration)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        findNavController(R.id.nav_host_fragment).currentDestination?.let {
            val logOut = menu.findItem(R.id.action_log_out)
            val addStory = menu.findItem(R.id.action_add_story)
            val map = menu.findItem(R.id.action_map)

            when (it.id) {
                R.id.login_fragment,
                R.id.register_fragment -> {
                    logOut.isVisible = false
                    addStory.isVisible = false
                    map.isVisible = false
                }
                R.id.detail_fragment,
                R.id.add_story_fragment -> {
                    menu.clear()
                }
                R.id.stories_fragment -> {
                    supportActionBar?.let { actionBar ->
                        actionBar.setDisplayHomeAsUpEnabled(false)
                        actionBar.setHomeButtonEnabled(false)
                    }
                }
                R.id.map_fragment -> {
                    map.isVisible = false
                    addStory.isVisible = false
                }
                else -> {
                    // do nothing
                }
            }
        }
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}