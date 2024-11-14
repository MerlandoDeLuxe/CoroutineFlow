package com.example.coroutineflow.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.coroutineflow.domain.Team
import com.example.coroutineflow.domain.TeamScoreRepository
import com.example.coroutineflow.domain.TeamScoreSetScoreUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class TeamScoreViewModel @Inject constructor(
    private val teamScoreSetScoreUseCase: TeamScoreSetScoreUseCase
) : ViewModel() {

    private val _stateGame = MutableStateFlow<TeamScoreState>(TeamScoreState.Game(0, 0))
    var stateGame = _stateGame.asStateFlow()

    fun increaseScore(team: Team) {
        val currentState = _stateGame.value
        if (currentState is TeamScoreState.Game) {
            if (team == Team.TEAM_1) {
                val oldValue = currentState.score1
                val newValue = oldValue + 1
                _stateGame.value = currentState.copy(score1 = newValue)
                if (newValue >= WINNER_SCORE) {
                    _stateGame.value = TeamScoreState.Winner(
                        Team.TEAM_1,
                        score1 = newValue,
                        currentState.score2
                    )
                }
            } else {
                val oldValue = currentState.score2
                val newValue = oldValue + 1
                _stateGame.value = currentState.copy(score2 = newValue)
                if (newValue >= WINNER_SCORE) {
                    _stateGame.value = TeamScoreState.Winner(
                        Team.TEAM_2,
                        score1 = currentState.score1,
                        newValue
                    )
                }
            }
        }
    }

//        val currentState = _state.value
//        if (currentState is TeamScoreState.Game) {
//            if (team == Team.TEAM_1) {
//                val oldValue = currentState.score1
//                val newValue = oldValue + 1
//                _state.value = currentState.copy(score1 = newValue)
//                if (newValue >= WINNER_SCORE) {
//                    _state.value = TeamScoreState.Winner(
//                        winnerTeam = Team.TEAM_1,
//                        newValue,
//                        currentState.score2
//                    )
//                }
//            } else {
//                val oldValue = currentState.score2
//                val newValue = oldValue + 1
//                _state.value = currentState.copy(score2 = newValue)
//                if (newValue >= WINNER_SCORE) {
//                    _state.value = TeamScoreState.Winner(
//                        winnerTeam = Team.TEAM_2,
//                        currentState.score1,
//                        newValue
//                    )
//                }
//            }
//        }


//    private val _state = MutableLiveData<TeamScoreState>(TeamScoreState.Game(0, 0))
//    val state: LiveData<TeamScoreState>
//        get() = _state


//    fun increaseScore(team: Team) {
//        val currentState = _state.value
//        if (currentState is TeamScoreState.Game) {
//            if (team == Team.TEAM_1) {
//                val oldValue = currentState.score1
//                val newValue = oldValue + 1
//                _state.value = currentState.copy(score1 = newValue)
//                if (newValue >= WINNER_SCORE) {
//                    _state.value = TeamScoreState.Winner(
//                        winnerTeam = Team.TEAM_1,
//                        newValue,
//                        currentState.score2
//                    )
//                }
//            } else {
//                val oldValue = currentState.score2
//                val newValue = oldValue + 1
//                _state.value = currentState.copy(score2 = newValue)
//                if (newValue >= WINNER_SCORE) {
//                    _state.value = TeamScoreState.Winner(
//                        winnerTeam = Team.TEAM_2,
//                        currentState.score1,
//                        newValue
//                    )
//                }
//            }
//        }
//    }

    private companion object {
        private const val TAG = "TeamScoreViewModel"
        private const val WINNER_SCORE = 10
    }
}