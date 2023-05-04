package com.mangbaam.mvitemplate.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding

abstract class MviActivity<BINDING : ViewDataBinding, STATE : Any, EVENT : Any, SIDE_EFFECT : Any>(
    @LayoutRes layoutId: Int,
) : BaseActivity<BINDING>(layoutId) {

    abstract val viewModel: MviViewModel<STATE, EVENT, SIDE_EFFECT>

    abstract fun render(state: STATE)
    abstract fun handleEvent(event: EVENT)
    abstract fun handleSideEffect(sideEffect: SIDE_EFFECT)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observe()
    }

    open fun observe() {
        viewModel.observe(
            this,
            state = ::render,
            event = ::handleEvent,
            sideEffect = ::handleSideEffect,
        )
    }
}
