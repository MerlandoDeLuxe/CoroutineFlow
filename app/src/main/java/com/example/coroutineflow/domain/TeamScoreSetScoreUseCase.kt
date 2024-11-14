package com.example.coroutineflow.domain

import com.example.coroutineflow.presentation.TeamScoreState
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class TeamScoreSetScoreUseCase @Inject constructor(private val repository: TeamScoreRepository) {

    operator fun invoke(team: Team): TeamScoreState {
        return repository.setScore(team)
    }
}