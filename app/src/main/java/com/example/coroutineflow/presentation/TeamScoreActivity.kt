package com.example.coroutineflow.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.coroutineflow.R
import com.example.coroutineflow.UserApp
import com.example.coroutineflow.databinding.ActivityTeamScoreBinding
import com.example.coroutineflow.domain.Team
import kotlinx.coroutines.launch
import javax.inject.Inject

class TeamScoreActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityTeamScoreBinding.inflate(layoutInflater)
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(TeamScoreViewModel::class.java)
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
        observeViewModel()
        setupOnClickListeners()
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                Log.d(TAG, "observeViewModel: мы тут")
                viewModel.stateGame.collect {
                    when (it) {
                        is TeamScoreState.Game -> {
                            with(binding) {
                                textViewTeam1Score.text = it.score1.toString()
                                textViewTeam2Score.text = it.score2.toString()
                            }
                        }

                        is TeamScoreState.Winner -> {
                            with(binding) {
                                textViewTeam1Score.text = it.score1.toString()
                                textViewTeam2Score.text = it.score2.toString()
                                Toast.makeText(
                                    this@TeamScoreActivity,
                                    "Победитель: ${it.winnerTeam}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }
            }
        }
    }

    private fun setupOnClickListeners() {
        with(binding) {
            textViewTeam1Logo.setOnClickListener {
                viewModel.increaseScore(Team.TEAM_1)
            }
            textViewTeam2Logo.setOnClickListener {
                viewModel.increaseScore(Team.TEAM_2)
            }
        }
    }

    companion object {
        private const val TAG = "TeamScoreActivity"

        fun newIntent(context: Context): Intent {
            return Intent(context, TeamScoreActivity::class.java)
        }
    }
}