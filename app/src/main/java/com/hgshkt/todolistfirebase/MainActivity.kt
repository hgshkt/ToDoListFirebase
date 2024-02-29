package com.hgshkt.todolistfirebase

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.common.api.ApiException
import com.hgshkt.todolistfirebase.view.viewModel.MainViewModel

class MainActivity : AppCompatActivity(), OnAuthActivity {

    private val signInRequestCode = 1
    private var viewModel: MainViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel!!.uiState.observeForever { state ->
            when (state) {
                is MainViewModel.UIState.LoginScreen -> updateUiToLoginScreen()
                is MainViewModel.UIState.ToDoListScreen -> updateUiToListScreen()
                is MainViewModel.UIState.CreateTaskScreen -> updateUiToCreateTaskScreen()
            }
        }
    }

    private fun updateUiToCreateTaskScreen() {
        TODO("Not yet implemented")
    }

    private fun updateUiToListScreen() {
        TODO("Not yet implemented")
    }

    private fun updateUiToLoginScreen() {
        TODO("Not yet implemented")
    }

    override fun launch(intent: Intent) {
        startActivityForResult(intent, signInRequestCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            signInRequestCode -> viewModel!!.signInWithCredential(
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