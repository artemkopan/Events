package com.artemkopan.presentation.base

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.artemkopan.core.tools.Logger
import dagger.Lazy
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

abstract class BaseFragment<ViewModel : BaseViewModel> : androidx.fragment.app.Fragment() {

    protected val destroyViewDisposable = CompositeDisposable()
    protected lateinit var viewModel: ViewModel

    @Inject
    protected lateinit var viewModelFactory: Lazy<ViewModelProvider.Factory>

    @LayoutRes
    protected abstract fun getContentView(): Int
    protected abstract fun getViewModelClass(): Class<ViewModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerViewModelLifecycle()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return if (getContentView() != View.NO_ID) {
            inflater.inflate(getContentView(), container, false)
        } else {
            super.onCreateView(inflater, container, savedInstanceState)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViewModel()
    }

    override fun onDestroyView() {
        destroyViewDisposable.clear()
        super.onDestroyView()
    }

    override fun onDestroy() {
        unregisterViewModelLifecycle()
        super.onDestroy()
    }

    /**
     * if initialization view model does not need, override and leave empty method
     */
    protected fun initViewModel() {
        if (!::viewModelFactory.isInitialized) {
            Logger.w("ViewModelFactory is not initialized! Please, check your dagger inject logic.")
            return
        }
        viewModel = ViewModelProviders.of(this, viewModelFactory.get())
                .get(getViewModelClass())
    }

    protected fun registerViewModelLifecycle() {
        if (::viewModel.isInitialized) {
            lifecycle.addObserver(viewModel)
        }
    }

    protected fun unregisterViewModelLifecycle() {
        if (::viewModel.isInitialized) {
            lifecycle.removeObserver(viewModel)
        }
    }
}