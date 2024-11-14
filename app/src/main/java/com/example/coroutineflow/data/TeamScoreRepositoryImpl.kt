package com.example.coroutineflow.data

import android.util.Log
import com.example.coroutineflow.domain.Team
import com.example.coroutineflow.domain.TeamScoreRepository
import com.example.coroutineflow.presentation.TeamScoreState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class TeamScoreRepositoryImpl @Inject constructor() : TeamScoreRepository {

    private val _stateGame = MutableStateFlow<TeamScoreState>(TeamScoreState.Game(0, 0))


    override fun setScore(team: Team): TeamScoreState {
        val currentState = _stateGame.value
        if (currentState is TeamScoreState.Game) {
            if (team == Team.TEAM_1) {
                val oldScore = currentState.score1
                val newScore = oldScore + 1
                _stateGame.value = currentState.copy(score1 = newScore)
                if (newScore > WINNER_SCORE) {
                    _stateGame.value =
                        TeamScoreState.Winner(Team.TEAM_1, newScore, currentState.score2)
                }
            } else {
                val oldScore = currentState.score2
                val newScore = oldScore + 1
                _stateGame.value = currentState.copy(score2 = newScore)
                if (newScore >= WINNER_SCORE) {
                    _stateGame.value =
                        TeamScoreState.Winner(Team.TEAM_2, currentState.score1, newScore)
                }
            }
        }
        Log.d(TAG, "setScore: ${_stateGame.value}")
        return _stateGame.value
    }

    companion object {
        private val TAG = "TeamScoreRepositoryImpl"
        private const val WINNER_SCORE = 10
    }
}