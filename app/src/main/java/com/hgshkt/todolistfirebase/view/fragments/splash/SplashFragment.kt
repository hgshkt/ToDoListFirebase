package com.hgshkt.todolistfirebase.view.fragments.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.google.android.gms.common.SignInButton
import com.hgshkt.todolistfirebase.OnAuthActivity
import com.hgshkt.todolistfirebase.R
import com.hgshkt.todolistfirebase.view.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : Fragment() {
    private lateinit var signInButton: SignInButton
    private lateinit var progressBar: ProgressBar

    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.splash_login_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init(view)

        signInButton.setOnClickListener {
            val activity = requireActivity() as OnAuthActivity

            viewModel.signInButtonClick(requireContext()) { intent ->
                activity.launch(intent)
            }
        }
        viewModel.uiState.observeForever {
            when (it) {
                is MainViewModel.UIState.LoginScreen -> updateUiToLogin()
                is MainViewModel.UIState.LoadingLoginScreen -> updateUiToLoading()
                else -> {}
            }
        }
    }

    private fun updateUiToLoading() {
        progressBar.visibility = VISIBLE
        signInButton.visibility = INVISIBLE
    }

    private fun updateUiToLogin() {
        progressBar.visibility = INVISIBLE
        signInButton.visibility = VISIBLE
    }

    private fun init(view: View) {
        signInButton = view.findViewById(R.id.signInButton)
        progressBar = view.findViewById(R.id.loginProgressBar)
    }
}