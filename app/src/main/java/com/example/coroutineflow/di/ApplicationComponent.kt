package com.example.coroutineflow.di

import com.example.coroutineflow.presentation.CryptoActivity
import com.example.coroutineflow.presentation.User2Activity
import com.example.coroutineflow.presentation.MainActivity
import com.example.coroutineflow.presentation.TeamScoreActivity
import com.example.coroutineflow.presentation.UserActivity
import dagger.Component

@ApplicationScope
@Component(
    modules = [DomainModule::class,
        ViewModelModule::class
    ]
)
interface ApplicationComponent {

    fun inject(activity: MainActivity)
    fun inject(activity: UserActivity)
    fun inject(activity: User2Activity)
    fun inject(activity: CryptoActivity)
    fun inject(activity: TeamScoreActivity)

    @Component.Factory
    interface Factory {

        fun create(): ApplicationComponent
    }
}