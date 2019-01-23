package com.alv.todo.ui.create

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.alv.todo.data.room.ToDoDao
import com.alv.todo.domain.ToDo
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class NewToDoViewModel @Inject constructor(
        private val toDoDao: ToDoDao
) : ViewModel() {

    private lateinit var disposable: Disposable

    val isSaving: MutableLiveData<Boolean> = MutableLiveData()

    val wasSaved: MutableLiveData<Boolean> = MutableLiveData()

    val hasError: MutableLiveData<Boolean> = MutableLiveData()

    override fun onCleared() {
        super.onCleared()
        dispose()
    }

    fun dispose() {
        if (!disposable.isDisposed) {
            disposable.dispose()
        }
    }

    fun close() {
        disposable = Observable
                .fromCallable {
                    Log.i("CLOSE", "CLOSE")
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { isSaving.value = true }
                .doAfterTerminate { isSaving.value = false }
                .subscribe(
                        { wasSaved.value = true },
                        { hasError.value = true ; wasSaved.value = false }
                )
    }

    fun save(item: ToDo) {
        disposable = Observable
                .fromCallable {
                    if(item != null)
                        toDoDao.insert(item)
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { isSaving.value = true }
                .doAfterTerminate { isSaving.value = false }
                .subscribe(
                        { wasSaved.value = true },
                        { hasError.value = true ; wasSaved.value = false }
                )
    }

}