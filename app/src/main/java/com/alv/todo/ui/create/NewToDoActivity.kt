package com.alv.todo.ui.create

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.widget.Toast
import com.alv.todo.R

import com.alv.todo.domain.ToDo
import convalida.annotations.OnValidationSuccess
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject
import com.alv.todo.databinding.ActivityNewToDoBinding
import java.util.*


class NewToDoActivity : DaggerAppCompatActivity() {
    private lateinit var binding: ActivityNewToDoBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: NewToDoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_new_to_do)
        viewModel = ViewModelProviders
                .of(this, viewModelFactory)
                .get(NewToDoViewModel::class.java)

        binding.viewModel = viewModel
        val date = Date().time
        binding.toDo = ToDo(id = null, name = "", isComplete = false, dateCompleted = date, dateCreated = date)
        binding.setLifecycleOwner(this)

        setupToolbar()

        NewToDoActivityFieldsValidation.init(this, binding)

        observeWasSaved()
        observeHasError()
    }

    override fun onDestroy() {
        viewModel.dispose()
        super.onDestroy()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.includeToolbar?.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        binding.includeToolbar?.toolbar?.setNavigationOnClickListener {
            viewModel.close()
        }
    }

    @OnValidationSuccess
    fun todoSaved() {
        binding.toDo?.let { viewModel.save(it) }
    }

    private fun observeWasSaved() {
        viewModel.wasSaved.observe(this, Observer {
            it?.let { if(it) { finish() } }
        })
    }

    private fun observeHasError() {
        viewModel.hasError.observe(this, Observer {
            Toast.makeText(this, R.string.save_failure, Toast.LENGTH_LONG)
                    .show()
        })
    }
}
