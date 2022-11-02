package com.don.storyApp.presentation.login

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.don.storyApp.R
import com.don.storyApp.databinding.FragmentLoginBinding
import com.don.storyApp.util.showSnackBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var binding: FragmentLoginBinding? = null

    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)

        binding?.apply {
            lifecycleOwner = this@LoginFragment
            vm = viewModel
        }
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (viewModel.hasAccessToken()) {
            findNavController().navigate(R.id.action_login_fragment_to_stories_fragment)
        }

        binding?.apply {
            tilEmail.editText?.doAfterTextChanged {
                doCheckValidation()
            }
            tilPassword.editText?.doAfterTextChanged {
                doCheckValidation()
            }
            btnLogin.setOnClickListener {
                viewModel.submitLogin(
                    errorMessage = {
                        showSnackBar(root, it)
                    },
                    onSuccess = {
                        findNavController().navigate(R.id.action_login_fragment_to_stories_fragment)
                    }
                )
            }

            tvRegister.setOnClickListener {
                findNavController().navigate(R.id.action_login_fragment_to_register_fragment)
            }
        }
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
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun doCheckValidation() {
        viewModel.isButtonEnabled.value =
            binding?.tilEmail?.isFormValid() == true && binding?.tilPassword?.isFormValid() == true
    }
}