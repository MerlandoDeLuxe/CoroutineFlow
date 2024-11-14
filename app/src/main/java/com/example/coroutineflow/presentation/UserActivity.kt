package com.example.coroutineflow.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.coroutineflow.R
import com.example.coroutineflow.UserApp
import com.example.coroutineflow.databinding.ActivityUserBinding
import javax.inject.Inject

class UserActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityUserBinding.inflate(layoutInflater)
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(UserViewModel::class.java)
    }

    private val component by lazy {
        (application as UserApp).component
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setupOnClickListeners()
        observeViewModel()
        Log.d(TAG, "onCreate: viewModel = $viewModel")
    }

    private fun setupOnClickListeners() {
        binding.buttonAddUser.setOnClickListener {
            binding.editTextEnterUserName.text.toString()
                .trim()
                .takeIf { it.isNotBlank() }
                ?.let { viewModel.addUser(it) }
        }

        binding.buttonLaunchNextScreen.setOnClickListener {
            startActivity(User2Activity.newIntent(this))
        }
    }

    private fun observeViewModel() {
        viewModel.users.observe(this, {
            binding.textViewUserNames.text = it.joinToString()
        })
    }

    companion object {
        private val TAG = "UserActivity"

        fun newIntent(context: Context): Intent {
            return Intent(context, UserActivity::class.java)
        }
    }
}