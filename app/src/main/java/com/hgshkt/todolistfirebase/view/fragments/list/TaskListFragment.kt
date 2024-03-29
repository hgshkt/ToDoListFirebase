package com.hgshkt.todolistfirebase.view.fragments.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.hgshkt.todolistfirebase.R
import com.hgshkt.todolistfirebase.view.fragments.create.CreateTaskFragment
import com.hgshkt.todolistfirebase.view.model.TaskDisplay
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
                is MainViewModel.UIState.FilledList -> updateUiToFilled(it.tasks)
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

    private fun updateUiToFilled(tasks: List<TaskDisplay>) {
        progressBar.visibility = INVISIBLE
        recyclerView.visibility = VISIBLE
        addButton.visibility = VISIBLE

        recyclerView.adapter = TaskListAdapter(tasks)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.Callback() {
            override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ): Int = makeMovementFlags(0, ItemTouchHelper.END)

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                if (direction == ItemTouchHelper.END) {
                    viewModel.delete(tasks[viewHolder.adapterPosition])
                }
            }
        })
        itemTouchHelper.attachToRecyclerView(recyclerView)
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