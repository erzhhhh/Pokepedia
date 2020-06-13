package com.example.pokepedia

import androidx.multidex.MultiDexApplication
import com.example.pokepedia.di.AppModule

class App : MultiDexApplication() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        appComponent = DaggerAppComponent
            .builder()
            .appModule(
                AppModule(
                    this,
                    "https://pokeapi.co/api/"
                )
            )
            .build()
        super.onCreate()
    }
}