package com.hgshkt.todolistfirebase.view.viewModel

import android.content.Context
import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.hgshkt.todolistfirebase.data.FirebaseHelper

class MainViewModel : ViewModel() {

    private val firebaseHelper: FirebaseHelper = FirebaseHelper()

    private val _uiState = MutableLiveData<UIState>(UIState.LoginScreen)
    val uiState: LiveData<UIState> = _uiState

    fun signIn(context: Context) {
        val account = firebaseHelper.getLastSignedInAccount(context)

        if (account == null) {
            _uiState.postValue(UIState.LoginScreen)
        } else {
            _uiState.postValue(UIState.ToDoListScreen)
        }
    }

    fun signInButtonClick(context: Context, buttonClick:(Intent) -> Unit) {
        val intent = firebaseHelper.createSignInIntent(context)
        buttonClick(intent)
    }

    sealed class UIState {
        data object LoginScreen : UIState()

        data object ToDoListScreen : UIState()

        data object CreateTaskScreen : UIState()
    }
}