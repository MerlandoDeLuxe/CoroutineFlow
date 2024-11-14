package com.example.coroutineflow.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.coroutineflow.R
import com.example.coroutineflow.UserApp
import com.example.coroutineflow.databinding.ActivityCryptoBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.launch
import javax.inject.Inject

class CryptoActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityCryptoBinding.inflate(layoutInflater)
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(CryptoViewModel::class.java)
    }

    @Inject
    lateinit var adapter: CryptoAdapter

    private val component by lazy {
        (application as UserApp).component
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initializeAllElements()
        observeViewModel()

        binding.buttonRefreshList.setOnClickListener {
            viewModel.refreshList()
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.state
                    .collect {
                        when (it) {
                            is State.Content -> {
                                adapter.submitList(it.currencyList)
                                with(binding) {
                                    progressBar.visibility = View.INVISIBLE
                                    buttonRefreshList.isEnabled = true
                                }
                            }

                            State.Initial -> {
                                with(binding) {
                                    progressBar.visibility = View.VISIBLE
                                    buttonRefreshList.isEnabled = false
                                }
                            }

                            State.Loading -> {
                                with(binding) {
                                    progressBar.visibility = View.VISIBLE
                                    buttonRefreshList.isEnabled = false
                                }
                            }
                        }
                    }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.state2.collect {
                    when (it) {
                        is State.Content -> Log.d(TAG, "observeViewModel: ${it.currencyList.joinToString()}")
                        else -> {}
                    }
                }
            }
        }
    }

    private fun initializeAllElements() {
        binding.recycleView.adapter = adapter
        binding.recycleView.itemAnimator = null
    }

    companion object {

        private const val TAG = "CryptoActivity"
        fun newIntent(context: Context): Intent {
            return Intent(context, CryptoActivity::class.java)
        }
    }
}