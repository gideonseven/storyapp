package com.don.storyApp.presentation.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.don.storyApp.R
import com.don.storyApp.databinding.FragmentLoginBinding
import com.don.storyApp.util.Validation
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var binding: FragmentLoginBinding? = null

    private val viewModel by viewModels<LoginViewModel>()

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

        binding?.let { loginBinding ->
            loginBinding.edLoginEmail.doAfterTextChanged {
                with(viewModel) {
                    mIsValidEmail.value = Validation.isValidEmail(loginBinding.tilEmail)
                    viewModel.checkForm()
                    Timber.e(" ==== edLoginEmail ${Validation.isValidEmail(loginBinding.tilEmail)}")
                }
            }
            loginBinding.edLoginPassword.doAfterTextChanged {
                with(viewModel) {
                    mIsValidPassword.value = Validation.isValidPassword(loginBinding.tilPassword)
                    viewModel.checkForm()
                    Timber.e(" ==== edLoginPassword ${Validation.isValidPassword(loginBinding.tilPassword)}")
                }
            }
            loginBinding.btnLogin.setOnClickListener {
                viewModel.submitLogin(
                    errorMessage = {
                        Snackbar.make(loginBinding.root, it, Snackbar.LENGTH_SHORT).apply {
                            setTextColor(
                                ContextCompat.getColor(
                                    this.context,
                                    R.color.md_white_1000
                                )
                            )
                            show()
                        }
                    },
                    onSuccess = {
                    }
                )
            }

            loginBinding.tvRegister.setOnClickListener {

            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}