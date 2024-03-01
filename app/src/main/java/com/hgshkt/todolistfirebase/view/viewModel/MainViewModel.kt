package com.hgshkt.todolistfirebase.view.viewModel

import android.content.Context
import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.common.api.ApiException
import com.hgshkt.todolistfirebase.data.auth.FirebaseAuthHelper
import com.hgshkt.todolistfirebase.data.repository.TaskRepository
import com.hgshkt.todolistfirebase.data.repository.models.CreateTaskData
import com.hgshkt.todolistfirebase.view.model.TaskDisplay
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val firebaseAuthHelper: FirebaseAuthHelper,
    private val repository: TaskRepository
) : ViewModel() {

    private val _uiState = MutableLiveData<UIState>(UIState.LoginScreen)
    val uiState: LiveData<UIState> = _uiState

    fun updateUiStateByUserLogging(context: Context) {
        val account = firebaseAuthHelper.getLastSignedInAccount(context)

        if (account == null) {
            _uiState.postValue(UIState.LoginScreen)
        } else {
            _uiState.postValue(UIState.LoadingTaskList)
        }
    }

    fun signInButtonClick(context: Context, buttonClick: (Intent) -> Unit) {
        val intent = firebaseAuthHelper.createSignInIntent(context)
        _uiState.postValue(UIState.LoadingLoginScreen)
        buttonClick(intent)
    }

    fun signInWithCredential(
        data: Intent,
        afterSignIn: () -> Unit,
        errorHandling: (ApiException) -> Unit
    ) {
        firebaseAuthHelper.signInWithCredential(
            data = data,
            afterSignIn = afterSignIn,
            errorHandling = { e -> errorHandling(e) }
        )
    }

    fun createTask(description: String) {
        val data = CreateTaskData(
            description = description
        )
        repository.create(data)
    }

    fun delete(task: TaskDisplay) {
        repository.delete(task)
    }

    fun fetchTasks() {
        viewModelScope.launch {
            val tasks = repository.getTasks()
            _uiState.postValue(UIState.FilledList(tasks))
        }
    }

    sealed class UIState {
        data object LoginScreen : UIState()

        data object LoadingLoginScreen : UIState()

        data object LoadingTaskList : UIState()

        data class FilledList(val tasks: List<TaskDisplay>) : UIState()

        data object CreateTaskScreen : UIState()
    }
}