package com.example.pokepedia

import android.content.Context
import com.example.pokepedia.di.AppModule
import com.example.pokepedia.pokemonInfo.PokemonDetailsFragment
import com.example.pokepedia.pokemonList.PokemonListFragment
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

    fun inject(target: PokemonListFragment)

    fun inject(target: PokemonDetailsFragment)
}