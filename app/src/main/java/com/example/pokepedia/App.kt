package com.example.pokepedia

import android.app.Application

class App : Application() {

    companion object {
        private lateinit var instance: App

        @JvmStatic
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        instance = this
        appComponent = DaggerAppComponent
            .builder()
            .appModule(AppModule(this, "https://pokeapi.co/api/"))
            .build()
        super.onCreate()
    }
}