package com.mangbaam.mvitemplate.util

import androidx.lifecycle.SavedStateHandle
import com.mangbaam.mvitemplate.BuildConfig
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import timber.log.Timber

/**
 * SavedStateHandle 을 통해 상태를 저장하는 MutableStateFlow
 *
 * `adb shell am kill "com.mangbaam.mvitemplate"` 명령으로 테스트 가능
 *
 * @param savedStateHandle
 * @param key 저장할 상태 키
 * @param initialValue 초기 값
 * @author mangbaam
 * */
class SaveableMutableStateFlow<T>(
    private val savedStateHandle: SavedStateHandle,
    private val key: String,
    initialValue: T,
) : MutableStateFlow<T> {

    init {
        try {
            savedStateHandle["TEST"] = initialValue
        } catch (e: IllegalArgumentException) {
            if (BuildConfig.DEBUG_MODE) {
                throw IllegalArgumentException("SavedState 에 저장할 수 없는 타입입니다. https://developer.android.com/topic/libraries/architecture/viewmodel-savedstate?hl=ko#direct 에서 지원하는 타입을 확인하세요")
            } else {
                Timber.e("SavedState 에 저장할 수 없는 타입입니다. https://developer.android.com/topic/libraries/architecture/viewmodel-savedstate?hl=ko#direct 에서 지원하는 타입을 확인하세요")
            }
        } finally {
            savedStateHandle.remove<T>("TEST")
        }
    }

    private val _state = try {
        MutableStateFlow(savedStateHandle.getStateFlow(key, initialValue).value)
    } catch (_: IllegalArgumentException) {
        MutableStateFlow(initialValue)
    }

    override val subscriptionCount: StateFlow<Int>
        get() = _state.subscriptionCount

    override suspend fun emit(value: T) {
        _state.emit(value)
    }

    @ExperimentalCoroutinesApi
    override fun resetReplayCache() {
        _state.resetReplayCache()
    }

    override fun tryEmit(value: T): Boolean {
        return _state.tryEmit(value)
    }

    override var value: T
        get() = _state.value
        set(value) {
            try {
                savedStateHandle[key] = value
            } catch (_: IllegalArgumentException) {
            }
            _state.value = value
        }

    override fun compareAndSet(expect: T, update: T): Boolean {
        return _state.compareAndSet(expect, update).also { success ->
            try {
                if (success) savedStateHandle[key] = update
            } catch (_: IllegalArgumentException) {
            }
        }
    }

    override val replayCache: List<T>
        get() = _state.replayCache

    override suspend fun collect(collector: FlowCollector<T>): Nothing {
        _state.collect(collector)
    }
}

/**
 * @see SaveableMutableStateFlow
 * */
fun <T> SavedStateHandle.getSaveableMutableStateFlow(
    key: String,
    initialValue: T,
): SaveableMutableStateFlow<T> =
    SaveableMutableStateFlow(this, key, initialValue)
