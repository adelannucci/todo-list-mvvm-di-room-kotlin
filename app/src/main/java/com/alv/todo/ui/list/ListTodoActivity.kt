package com.alv.todo.ui.list

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.alv.todo.R
import com.alv.todo.ui.Constants.LIST_TODO_ACTIVITY
import com.alv.todo.ui.Constants.START_NEW_TODO_ACTIVITY
import com.alv.todo.ui.RxBus
import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.functions.Consumer
import javax.inject.Inject
import android.support.v7.widget.DividerItemDecoration
import com.alv.todo.databinding.ActivityListTodoBinding
import com.alv.todo.domain.ToDo
import com.alv.todo.ui.Constants.DELETE_TODO
import com.alv.todo.ui.Constants.UPDATE_TODO
import com.alv.todo.ui.create.NewToDoActivity


class ListTodoActivity : DaggerAppCompatActivity() {

    private lateinit var binding: ActivityListTodoBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: ListToDoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_list_todo)
        viewModel = ViewModelProviders
                .of(this, viewModelFactory)
                .get(ListToDoViewModel::class.java)

        binding.viewModel = viewModel
        binding.setLifecycleOwner(this)

        setupRecyclerView()

        observeToDoList()

        callbackOpenActivity()
        callbackUpdateToDoItem()
        callbackDeleteToDoItem()

        callbackSuccess()
        callbackError()

    }

    override fun onResume() {
        super.onResume()
        viewModel.listAll()
    }

    override fun onDestroy() {
        super.onDestroy()
        RxBus.unregister(this)
    }

    private fun setupRecyclerView() {
        val recyclerView = binding.recyclerView
        val layoutManager = LinearLayoutManager(this)
        val divider = DividerItemDecoration(
                recyclerView.context,
                layoutManager.orientation
        )

        recyclerView.layoutManager = layoutManager
        recyclerView.addItemDecoration(divider)
    }

    private fun observeToDoList() {
        viewModel.todoList.observe(this, Observer {
            val items = it ?: emptyList()
            binding.recyclerView.adapter = ListToDoAdapter(items)
        })
    }

    private fun callbackOpenActivity() {
        RxBus.subscribe(LIST_TODO_ACTIVITY, this, Consumer {
            when(it.toString()) {
                START_NEW_TODO_ACTIVITY -> startNewToDoActivity()
            }
        })
    }

    private fun callbackUpdateToDoItem() {
        RxBus.subscribe(UPDATE_TODO, this, Consumer {
            val item: ToDo = it as ToDo
            viewModel.update(item)
        })
    }

    private fun callbackDeleteToDoItem() {
        RxBus.subscribe(DELETE_TODO, this, Consumer {
            val item: ToDo = it as ToDo
            viewModel.delete(item)
        })
    }

    private fun callbackSuccess() {
        viewModel.hasSuccess.observe(this, Observer {
            val isShow = it as Boolean
            if (isShow) {
                when (viewModel.action) {
                    "UPDATE" -> Toast.makeText(this, R.string.update, Toast.LENGTH_LONG).show()
                    "DELETE" -> viewModel.listAll()
                }
            }
        })
    }

    private fun callbackError() {
        viewModel.hasError.observe(this, Observer {
            val isShow = it as Boolean
            if (isShow) {
                when (viewModel.action) {
                    "ALL" -> Toast.makeText(this, R.string.load_failure, Toast.LENGTH_LONG).show()
                    "UPDATE" -> Toast.makeText(this, R.string.update_failure, Toast.LENGTH_LONG).show()
                    "DELETE" -> Toast.makeText(this, R.string.delete_failure, Toast.LENGTH_LONG).show()
                }
            }
        })
    }


    private fun startNewToDoActivity() {
        startActivity(Intent(this, NewToDoActivity::class.java))
    }

}