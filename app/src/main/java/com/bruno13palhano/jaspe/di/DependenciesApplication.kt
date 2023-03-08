package com.bruno13palhano.jaspe.dependencies

import android.app.Application

class DependenciesApplication : Application() {
    lateinit var dependenciesContainer: DependenciesContainer

    override fun onCreate() {
        super.onCreate()
        dependenciesContainer = DependenciesContainer(this)
    }
}