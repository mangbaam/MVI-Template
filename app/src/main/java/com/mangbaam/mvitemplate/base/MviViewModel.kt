package com.mangbaam.mvitemplate.base

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewModelScope
import com.mangbaam.mvitemplate.util.getSaveableMutableStateFlow
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.withContext

abstract class MviViewModel<STATE : Any, EVENT : Any, SIDE_EFFECT : Any>(
    initialState: STATE,
    savedStateHandle: SavedStateHandle? = null,
    stateKey: String? = null,
) : ViewModel() {

    private val _stateFlow: MutableStateFlow<STATE> =
        savedStateHandle?.getSaveableMutableStateFlow(
            stateKey ?: getDefaultStateKey(),
            initialState,
        ) ?: MutableStateFlow(initialState)

    val stateFlow = _stateFlow.asStateFlow()
    val state get() = stateFlow.value

    private val _event = MutableSharedFlow<EVENT>()
    val event = _event.asSharedFlow()

    private val _sideEffect = Channel<SIDE_EFFECT>(Channel.BUFFERED)
    val sideEffect = _sideEffect.receiveAsFlow()

    fun intent(transform: suspend MviViewModel<STATE, EVENT, SIDE_EFFECT>.() -> Unit) {
        viewModelScope.launch(SINGLE_THREAD) {
            this@MviViewModel.transform()
        }
    }

    suspend fun reduce(reducer: STATE.() -> STATE) {
        withContext(SINGLE_THREAD) {
            _stateFlow.value = state.reducer()
        }
    }

    suspend fun event(event: EVENT) {
        _event.emit(event)
    }

    suspend fun postSideEffect(event: SIDE_EFFECT) {
        _sideEffect.send(event)
    }

    fun observe(
        lifecycleOwner: LifecycleOwner,
        lifecycleState: Lifecycle.State = Lifecycle.State.STARTED,
        state: (suspend (state: STATE) -> Unit)? = null,
        event: (suspend (event: EVENT) -> Unit)? = null,
        sideEffect: (suspend (sideEffect: SIDE_EFFECT) -> Unit)? = null,
    ) {
        lifecycleOwner.lifecycleScope.launch {
            lifecycleOwner.lifecycle.repeatOnLifecycle(lifecycleState) {
                state?.let { launch { stateFlow.collect { state(it) } } }
                event?.let { launch { this@MviViewModel.event.collect { event(it) } } }
                sideEffect?.let { launch { this@MviViewModel.sideEffect.collect { sideEffect(it) } } }
            }
        }
    }

    fun getDefaultStateKey() = "${this::class.java}_state_key"

    companion object {
        @OptIn(DelicateCoroutinesApi::class)
        private val SINGLE_THREAD = newSingleThreadContext("mvi")
    }
}
