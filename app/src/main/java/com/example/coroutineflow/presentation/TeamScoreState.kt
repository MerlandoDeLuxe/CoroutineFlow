package com.example.coroutineflow.presentation

import com.example.coroutineflow.domain.Team

sealed class TeamScoreState {

    data class Game(
        val score1: Int,
        val score2: Int
    ) : TeamScoreState()

    data class Winner(
        val winnerTeam: Team,
        val score1: Int,
        val score2: Int
    ) : TeamScoreState()
}