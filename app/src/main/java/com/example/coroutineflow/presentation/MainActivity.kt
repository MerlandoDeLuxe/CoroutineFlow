package com.example.coroutineflow.presentation

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.coroutineflow.R
import com.example.coroutineflow.UserApp
import com.example.coroutineflow.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

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

        with(binding) {
            buttonUserActivity.setOnClickListener {
                startActivity(UserActivity.newIntent(this@MainActivity))
            }

            buttonCryptoActivity.setOnClickListener {
                startActivity(CryptoActivity.newIntent(this@MainActivity))
            }

            buttonTeamScore.setOnClickListener {
                startActivity(TeamScoreActivity.newIntent(this@MainActivity))
            }
        }
    }
}