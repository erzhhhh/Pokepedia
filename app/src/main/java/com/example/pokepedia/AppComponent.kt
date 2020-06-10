package com.example.pokepedia

import android.content.Context
import dagger.Component
import retrofit2.Retrofit
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class
    ]
)
interface AppComponent {

    fun exposeRetrofit(): Retrofit

    fun exposeContext(): Context

//    fun injects(target: StarWarsService)
//
//    fun injects(target: MainActivity)
}