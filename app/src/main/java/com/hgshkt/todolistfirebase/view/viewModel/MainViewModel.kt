package com.hgshkt.todolistfirebase.view.viewModel

import android.content.Context
import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.common.api.ApiException
import com.hgshkt.todolistfirebase.data.auth.FirebaseHelper
import com.hgshkt.todolistfirebase.data.repository.TaskRepository
import com.hgshkt.todolistfirebase.data.repository.models.CreateTaskData

class MainViewModel : ViewModel() {

    private val firebaseHelper: FirebaseHelper = FirebaseHelper()
    private val repository: TaskRepository? = null

    private val _uiState = MutableLiveData<UIState>(UIState.LoginScreen)
    val uiState: LiveData<UIState> = _uiState

    fun updateUiStateByUserLogging(context: Context) {
        val account = firebaseHelper.getLastSignedInAccount(context)

        if (account == null) {
            _uiState.postValue(UIState.LoginScreen)
        } else {
            _uiState.postValue(UIState.ToDoListScreen)
        }
    }

    fun signInButtonClick(context: Context, buttonClick: (Intent) -> Unit) {
        val intent = firebaseHelper.createSignInIntent(context)
        buttonClick(intent)
    }

    fun signInWithCredential(
        data: Intent,
        afterSignIn: () -> Unit,
        errorHandling: (ApiException) -> Unit
    ) {
        firebaseHelper.signInWithCredential(
            data = data,
            afterSignIn = afterSignIn,
            errorHandling = { e -> errorHandling(e) }
        )
    }

    fun createTask(description: String) {
        val data = CreateTaskData(
            description = description
        )
        repository!!.create(data)
    }

    sealed class UIState {
        data object LoginScreen : UIState()

        data object ToDoListScreen : UIState()

        data object CreateTaskScreen : UIState()
    }
}