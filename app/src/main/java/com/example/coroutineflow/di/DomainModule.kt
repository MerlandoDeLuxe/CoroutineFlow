package com.example.coroutineflow.di

import com.example.coroutineflow.data.CryptoRepositoryImpl
import com.example.coroutineflow.data.TeamScoreRepositoryImpl
import com.example.coroutineflow.data.UserRepositoryImpl
import com.example.coroutineflow.domain.CryptoRepository
import com.example.coroutineflow.domain.TeamScoreRepository
import com.example.coroutineflow.domain.UserRepository
import dagger.Binds
import dagger.Module

@Module
interface DomainModule {

    @Binds
    @ApplicationScope
    fun bindUserRepository(impl: UserRepositoryImpl): UserRepository

    @Binds
    @ApplicationScope
    fun bindsCryptoRepository(impl: CryptoRepositoryImpl): CryptoRepository

    @Binds
    @ApplicationScope
    fun bindsTeamScoreRepository(impl: TeamScoreRepositoryImpl): TeamScoreRepository
}