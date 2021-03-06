package com.artemkopan.presentation.base

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.artemkopan.core.tools.Logger
import dagger.Lazy
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.*
import javax.inject.Inject

abstract class BaseFragment<ViewModel : BaseViewModel> : Fragment() {

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
        clearFindViewByIdCache()
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