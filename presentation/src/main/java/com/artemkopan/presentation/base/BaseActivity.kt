package com.artemkopan.presentation.base

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import com.artemkopan.core.tools.Logger
import dagger.Lazy
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

@Suppress("MemberVisibilityCanBePrivate")
abstract class BaseActivity<ViewModel : BaseViewModel> : AppCompatActivity() {

    protected val destroyDisposable = CompositeDisposable()
    protected lateinit var viewModel: ViewModel

    @Inject
    protected lateinit var viewModelFactory: Lazy<ViewModelProvider.Factory>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
        registerViewModelLifecycle()
        if (getContentView() != View.NO_ID) {
            setContentView(getContentView())
        }
        onCreated(savedInstanceState)
    }

    override fun onDestroy() {
        destroyDisposable.clear()
        unregisterViewModelLifecycle()
        super.onDestroy()
    }

    protected abstract fun onCreated(savedInstanceState: Bundle?)

    @LayoutRes
    protected abstract fun getContentView(): Int

    protected abstract fun getViewModelClass(): Class<ViewModel>

    /**
     * If you don't want to init view model, you can override method and stay empty
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