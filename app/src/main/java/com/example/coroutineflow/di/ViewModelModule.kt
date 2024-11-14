package com.example.coroutineflow.di

import androidx.lifecycle.ViewModel
import com.example.coroutineflow.presentation.CryptoViewModel
import com.example.coroutineflow.presentation.TeamScoreViewModel
import com.example.coroutineflow.presentation.UserViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @IntoMap
    @ViewModelKey(UserViewModel::class)
    @Binds
    fun bindsUserViewModel(impl: UserViewModel): ViewModel

    @IntoMap
    @ViewModelKey(CryptoViewModel::class)
    @Binds
    fun bindsCryptoViewModel(impl: CryptoViewModel): ViewModel

    @IntoMap
    @ViewModelKey(TeamScoreViewModel::class)
    @Binds
    fun bindTeamScoreViewModel(impl: TeamScoreViewModel): ViewModel
}