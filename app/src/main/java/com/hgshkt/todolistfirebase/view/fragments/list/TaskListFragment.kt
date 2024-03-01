package com.hgshkt.todolistfirebase.view.fragments.list

import android.opengl.Visibility
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.hgshkt.todolistfirebase.R
import com.hgshkt.todolistfirebase.view.fragments.create.CreateTaskFragment
import com.hgshkt.todolistfirebase.view.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TaskListFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var addButton: FloatingActionButton
    private lateinit var progressBar: ProgressBar

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
        addButton.setOnClickListener {
            val createTaskFragment = CreateTaskFragment()

            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, createTaskFragment)
                .addToBackStack(CreateTaskFragment::class.java.name)
                .commit()
        }
        viewModel.fetchTasks()
    }

    private fun updateUiToFilled() {
        progressBar.visibility = INVISIBLE
        recyclerView.visibility = VISIBLE
        addButton.visibility = VISIBLE
    }

    private fun updateUiToLoading() {
        progressBar.visibility = VISIBLE
        recyclerView.visibility = INVISIBLE
        addButton.visibility = INVISIBLE
    }

    private fun init(view: View) {
        recyclerView = view.findViewById(R.id.taskListRecyclerView)
        addButton = view.findViewById(R.id.taskListFragmentAddButton)
        progressBar = view.findViewById(R.id.progressBar)
    }
}