package com.hgshkt.todolistfirebase

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.common.api.ApiException
import com.hgshkt.todolistfirebase.view.fragments.create.CreateTaskFragment
import com.hgshkt.todolistfirebase.view.fragments.list.TaskListFragment
import com.hgshkt.todolistfirebase.view.fragments.splash.SplashFragment
import com.hgshkt.todolistfirebase.view.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), OnAuthActivity {

    private val signInRequestCode = 1
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.uiState.observeForever { state ->
            when (state) {
                is MainViewModel.UIState.LoginScreen -> updateUiToLoginScreen()
                is MainViewModel.UIState.LoadingTaskList -> updateUiToListScreen()
                is MainViewModel.UIState.CreateTaskScreen -> updateUiToCreateTaskScreen()
                is MainViewModel.UIState.FilledList -> {}
            }
        }
        viewModel.updateUiStateByUserLogging(applicationContext)
    }

    private fun updateUiToCreateTaskScreen() {
        val createTaskFragment = CreateTaskFragment()

        supportFragmentManager.beginTransaction()
            .add(createTaskFragment, CreateTaskFragment::class.java.name)
            .addToBackStack(CreateTaskFragment::class.java.name)
            .commit()
    }

    private fun updateUiToListScreen() {
        val taskListFragment = TaskListFragment()

        supportFragmentManager.beginTransaction()
            .add(taskListFragment, TaskListFragment::class.java.name)
            .commit()
    }

    private fun updateUiToLoginScreen() {
        val splashFragment = SplashFragment()

        supportFragmentManager.beginTransaction()
            .add(splashFragment, SplashFragment::class.java.name)
            .commit()
    }

    override fun launch(intent: Intent) {
        startActivityForResult(intent, signInRequestCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            signInRequestCode -> viewModel.signInWithCredential(
                data = data!!,
                afterSignIn = { updateUiToListScreen() },
                errorHandling = { e -> signInError(e) }
            )
        }
    }

    private fun signInError(e: ApiException) {
        Toast.makeText(this, "$e", Toast.LENGTH_LONG).show()
    }
}

interface OnAuthActivity {
    fun launch(intent: Intent)
}