package com.mangbaam.mvitemplate.sample.timer

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.mangbaam.mvitemplate.base.MviViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize
import timber.log.Timber

class MainViewModel(
    savedStateHandle: SavedStateHandle,
) : MviViewModel<MainState, MainEvent, MainSideEffect>(
    MainState(),
    savedStateHandle,
) {
    private var timerJob: Job? = null

    init {
        if (state.running) startTimer(true)
    }

    fun start() = viewModelScope.launch {
        event(MainEvent.Start)
    }

    fun pause() = viewModelScope.launch {
        event(MainEvent.Pause)
        postSideEffect(MainSideEffect.SnackBar("다시 시작"))
    }

    fun stop() = viewModelScope.launch {
        event(MainEvent.Stop)
    }

    fun startTimer(forceStart: Boolean = false) = intent {
        if (!forceStart && state.running) return@intent

        reduce { state.copy(running = true) }

        timerJob = viewModelScope.launch {
            while (true) {
                delay(1000)
                Timber.tag("MANGBAAM - MainViewModel").d("time: ${state.seconds}")
                reduce { state.copy(seconds = seconds.plus(1)) }
                if (state.seconds % 10 == 0L) postSideEffect(MainSideEffect.Toast("${state.seconds} 초 경과"))
            }
        }
    }

    fun pauseTimer() = intent {
        reduce { state.copy(running = false) }
        timerJob?.cancel()
        postSideEffect(MainSideEffect.SnackBar("일시중단 되었습니다", "다시 시작"))
    }

    fun stopTimer() = intent {
        reduce { state.copy(running = false) }
        timerJob?.cancel()
        reduce { state.copy(seconds = 0) }
    }
}

@Parcelize
data class MainState(
    val title: String = "초시계",
    val running: Boolean = false,
    val seconds: Long = 0,
) : Parcelable

sealed interface MainEvent {
    object Start : MainEvent
    object Pause : MainEvent
    object Stop : MainEvent
}

sealed interface MainSideEffect {
    data class Toast(val message: String) : MainSideEffect
    data class SnackBar(val message: String, val action: String? = null) : MainSideEffect
}
