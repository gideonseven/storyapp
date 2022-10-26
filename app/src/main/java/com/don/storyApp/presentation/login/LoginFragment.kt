package com.don.storyApp.presentation.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.don.storyApp.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint

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
        binding?.btnLogin?.setOnClickListener {
//            findNavController().navigate(R.id.action_LoginFragment_to_RegisterFragment)
        }


        binding?.apply {

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}