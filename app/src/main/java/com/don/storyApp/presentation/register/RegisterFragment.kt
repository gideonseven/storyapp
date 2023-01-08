package com.don.storyApp.presentation.register

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
import com.don.storyApp.databinding.FragmentRegisterBinding
import com.don.storyApp.util.showSnackBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private var binding: FragmentRegisterBinding? = null

    private val viewModel by viewModels<RegisterViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)

        binding?.apply {
            lifecycleOwner = this@RegisterFragment
            vm = viewModel
        }
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {
            tilName.editText?.doAfterTextChanged {
                doCheckValidation()
            }
            tilPassword.editText?.doAfterTextChanged {
                doCheckValidation()
            }
            tilEmail.editText?.doAfterTextChanged {
                doCheckValidation()
            }
            btnRegister.setOnClickListener {
                viewModel.submitRegister()
            }

            viewModel.errorMessage.observe(this@RegisterFragment.viewLifecycleOwner) {
                if (it.isNotEmpty()) {
                    showSnackBar(root, it)
                }
            }

            viewModel.simpleNetworkModel.observe(this@RegisterFragment.viewLifecycleOwner) {
                if (it.error == false) {
                    showSnackBar(root, it.message.orEmpty())
                    findNavController().popBackStack()
                }
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
            binding?.tilEmail?.isFormValid() == true
                    && binding?.tilPassword?.isFormValid() == true
                    && binding?.tilName?.isFormValid() == true
    }
}