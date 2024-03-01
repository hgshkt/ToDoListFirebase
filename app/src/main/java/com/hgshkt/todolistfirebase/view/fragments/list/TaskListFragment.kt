package com.hgshkt.todolistfirebase.view.fragments.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.hgshkt.todolistfirebase.R
import com.hgshkt.todolistfirebase.view.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TaskListFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init(view)

        viewModel.uiState.observeForever {
            when (it) {
                is MainViewModel.UIState.LoadingTaskList -> updateUiToLoading()
                is MainViewModel.UIState.FilledList -> updateUiToFilled()
                else -> {}
            }
        }
    }

    private fun updateUiToFilled() {
        TODO("Not yet implemented")
    }

    private fun updateUiToLoading() {
        TODO("Not yet implemented")
    }

    private fun init(view: View) {
        recyclerView = view.findViewById(R.id.taskListRecyclerView)
    }
}