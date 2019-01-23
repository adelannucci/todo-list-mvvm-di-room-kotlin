    package com.alv.todo.ui.list

    import android.arch.lifecycle.MutableLiveData
    import android.arch.lifecycle.ViewModel
    import com.alv.todo.data.repository.ToDoRepository
    import com.alv.todo.domain.ToDo
    import com.alv.todo.ui.Constants.LIST_TODO_ACTIVITY
    import com.alv.todo.ui.Constants.START_NEW_TODO_ACTIVITY
    import com.alv.todo.ui.RxBus
    import io.reactivex.Observable
    import io.reactivex.android.schedulers.AndroidSchedulers
    import io.reactivex.disposables.CompositeDisposable
    import io.reactivex.disposables.Disposable
    import io.reactivex.schedulers.Schedulers
    import javax.inject.Inject


    class ListToDoViewModel
    @Inject
    constructor(private val repository: ToDoRepository) : ViewModel() {

        private lateinit var disposable: Disposable
        private val compositeDisposable: CompositeDisposable = CompositeDisposable()

        val todoList: MutableLiveData<List<ToDo>> = MutableLiveData()
        var action: String = ""
        val isLoading: MutableLiveData<Boolean> = MutableLiveData()
        val hasSuccess: MutableLiveData<Boolean> = MutableLiveData()
        val hasError: MutableLiveData<Boolean> = MutableLiveData()

        override fun onCleared() {
            super.onCleared()
            if(!disposable.isDisposed) {
                disposable.dispose()
            }

            if(!compositeDisposable.isDisposed) {
                compositeDisposable.dispose()
            }
        }

        private fun handlerSuccess(type: String) {
            action = type
            hasSuccess.value = true
            hasError.value = false
        }

        private fun handlerError(type: String) {
            action = type
            hasSuccess.value = false
            hasError.value = true

        }

        fun update(item: ToDo) {
            disposable = Observable
                    .fromCallable { repository.update(item) }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe { isLoading.value = true }
                    .doAfterTerminate { isLoading.value = false }
                    .subscribe({handlerSuccess("UPDATE")},{handlerError("UPDATE")})
        }

        fun delete(item: ToDo) {
            disposable = Observable
                    .fromCallable { repository.delete(item) }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe { isLoading.value = true }
                    .doAfterTerminate { isLoading.value = false }
                    .subscribe({handlerSuccess("DELETE")},{handlerError("DELETE")})
        }

        fun listAll() {
            compositeDisposable.add(repository.listAll()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe { isLoading.value = true }
                    .doAfterTerminate { isLoading.value = false }
                    .subscribe(
                            {
                                todoList.value = it
                                handlerSuccess("ALL")
                            },
                            { handlerError("ALL") }
                    )
            )
        }

        fun startNewToDoActivity() {
            RxBus.publish(LIST_TODO_ACTIVITY, START_NEW_TODO_ACTIVITY)
        }

    }