package com.don.storyApp.presentation.stories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.don.storyApp.databinding.FragmentRegisterBinding
import com.don.storyApp.presentation.register.RegisterViewModel
import com.don.storyApp.util.Validation
import com.don.storyApp.util.showSnackBar
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber


/**
 * Created by gideon on 29 October 2022
 * gideon@cicil.co.id
 * https://www.cicil.co.id/
 */
@AndroidEntryPoint
class StoriesFragment : Fragment() {

    private var binding: FragmentRegisterBinding? = null

    private val viewModel by viewModels<RegisterViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)

        binding?.apply {
            lifecycleOwner = this@StoriesFragment
            vm = viewModel
        }
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.let { registerBinding ->
            registerBinding.edRegisterName.doAfterTextChanged {
                with(viewModel) {
                    mIsValidName.value = Validation.isValidName(registerBinding.tilName)
                    viewModel.checkForm()
                    Timber.e(" ==== edName ${Validation.isValidName(registerBinding.tilName)}")
                }
            }
            registerBinding.edRegisterPassword.doAfterTextChanged {
                with(viewModel) {
                    mIsValidPassword.value = Validation.isValidPassword(registerBinding.tilPassword)
                    viewModel.checkForm()
                    Timber.e(" ==== edLoginPassword ${Validation.isValidPassword(registerBinding.tilPassword)}")
                }
            }
            registerBinding.edRegisterEmail.doAfterTextChanged {
                with(viewModel) {
                    mIsValidEmail.value = Validation.isValidEmail(registerBinding.tilEmail)
                    viewModel.checkForm()
                    Timber.e(" ==== edLoginEmail ${Validation.isValidEmail(registerBinding.tilEmail)}")
                }
            }
            registerBinding.btnRegister.setOnClickListener {
                viewModel.submitRegister(
                    errorMessage = {
                        showSnackBar(registerBinding.root, it)
                    },
                    onSuccess = {
                        showSnackBar(registerBinding.root, it.message.orEmpty())
                    }
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}