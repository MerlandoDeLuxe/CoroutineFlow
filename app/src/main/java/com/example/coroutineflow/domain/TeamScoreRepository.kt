package com.example.coroutineflow.domain

import com.example.coroutineflow.presentation.TeamScoreState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface TeamScoreRepository {

    fun setScore(team: Team): TeamScoreState
}