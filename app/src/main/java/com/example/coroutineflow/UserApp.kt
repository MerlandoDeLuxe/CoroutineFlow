package com.example.coroutineflow

import android.app.Application
import com.example.coroutineflow.di.DaggerApplicationComponent

class UserApp : Application() {

    val component by lazy {
        DaggerApplicationComponent.factory().create()
    }
}