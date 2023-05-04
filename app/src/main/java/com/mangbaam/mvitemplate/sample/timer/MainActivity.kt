package com.mangbaam.mvitemplate.sample.timer

import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.mangbaam.mvitemplate.R
import com.mangbaam.mvitemplate.base.BaseActivity
import com.mangbaam.mvitemplate.databinding.ActivityMainBinding
import com.mangbaam.mvitemplate.extension.snackbar
import com.mangbaam.mvitemplate.extension.toast

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViews()
        viewModel.observe(
            this,
            state = ::render,
            event = ::handleEvent,
            sideEffect = ::handleSideEffect,
        )
    }

    private fun initViews() {
        binding.vm = viewModel
    }

    private fun render(state: MainState) {
        binding.state = state
        binding.tvBody.setTextColor(
            ContextCompat.getColor(
                this,
                when {
                    state.running -> R.color.red
                    state.seconds > 0 -> R.color.blue
                    else -> R.color.black
                },
            ),
        )
    }

    private fun handleEvent(event: MainEvent) {
        when (event) {
            MainEvent.Start -> viewModel.startTimer()
            MainEvent.Pause -> viewModel.pauseTimer()
            MainEvent.Stop -> viewModel.stopTimer()
        }
    }

    private fun handleSideEffect(sideEffect: MainSideEffect) {
        when (sideEffect) {
            is MainSideEffect.SnackBar -> binding.root.snackbar(
                sideEffect.message,
                Snackbar.LENGTH_INDEFINITE,
                sideEffect.action,
            ) {
                viewModel.start()
            }

            is MainSideEffect.Toast -> toast(sideEffect.message)
        }
    }
}
